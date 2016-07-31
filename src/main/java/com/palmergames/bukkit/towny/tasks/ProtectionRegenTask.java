package com.palmergames.bukkit.towny.tasks;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.regen.NeedsPlaceholder;
import com.palmergames.bukkit.towny.regen.TownyRegenAPI;
import com.palmergames.bukkit.towny.regen.block.BlockLocation;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;
import org.bukkit.material.Door;
import org.bukkit.material.PistonExtensionMaterial;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class ProtectionRegenTask extends TownyTimerTask {

	private BlockState state;
	private BlockState altState;
	private BlockLocation blockLocation;
	private int TaskId;
	private List<ItemStack> contents = new ArrayList<ItemStack>();
	
	//Tekkit - InventoryView

	private static final Material placeholder = Material.DIRT;

	public ProtectionRegenTask(Towny plugin, Block block, boolean update) {

		super(plugin);
		this.state = block.getState();
		this.altState = null;
		this.setBlockLocation(new BlockLocation(block.getLocation()));
		
		if (state instanceof InventoryHolder) {

			// Contents we are respawning.
			Inventory inven = ((InventoryHolder) state).getInventory();

			for (ItemStack item : inven.getContents()) {
				contents.add((item != null) ? item.clone() : null);
			}
			
			inven.clear();
		}
		
		if (update)
			if (state.getData() instanceof Door) {
				Door door = (Door) state.getData();
				Block topHalf;
				Block bottomHalf;
				if (door.isTopHalf()) {
					topHalf = block;
					bottomHalf = block.getRelative(BlockFace.DOWN);
				} else {
					bottomHalf = block;
					topHalf = block.getRelative(BlockFace.UP);
				}
				bottomHalf.setTypeId(0);
				topHalf.setTypeId(0);
			} else if (state.getData() instanceof PistonExtensionMaterial) {
				PistonExtensionMaterial extension = (PistonExtensionMaterial) state.getData();
				Block piston = block.getRelative(extension.getAttachedFace());
				if (piston.getTypeId() != 0) {
					this.altState = piston.getState();
					piston.setTypeId(0, false);
				}
				block.setTypeId(0, false);
			} else {
				block.setTypeId(0, false);
			}
	}

	@Override
	public void run() {

		replaceProtections();
		TownyRegenAPI.removeProtectionRegenTask(this);
	}

	public void replaceProtections() {

		try {

			Block block = state.getBlock();

			if (state.getData() instanceof Door) {

				Door door = (Door) state.getData();
				Block topHalf;
				Block bottomHalf;
				if (door.isTopHalf()) {
					topHalf = block;
					bottomHalf = block.getRelative(BlockFace.DOWN);
				} else {
					bottomHalf = block;
					topHalf = block.getRelative(BlockFace.UP);
				}
				door.setTopHalf(true);
				topHalf.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);
				door.setTopHalf(false);
				bottomHalf.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);

			} else if (state instanceof Sign) {

				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);
				Sign sign = (Sign) block.getState();
				int i = 0;
				for (String line : ((Sign) state).getLines())
					sign.setLine(i++, line);

				sign.update(true);

			} else if (state instanceof CreatureSpawner) {

				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);
				((CreatureSpawner) block.getState()).setSpawnedType(((CreatureSpawner) state).getSpawnedType());

			} else if (state instanceof InventoryHolder) {

				block.setTypeId(state.getTypeId(), false);

				// Container to receive the inventory
				Inventory container = ((InventoryHolder) block.getState()).getInventory();
				container.setContents(contents.toArray(new ItemStack[0]));
				
				block.setData(state.getData().getData(), false);

			} else if (state.getData() instanceof PistonExtensionMaterial) {

				PistonExtensionMaterial extension = (PistonExtensionMaterial) state.getData();
				Block piston = block.getRelative(extension.getAttachedFace());
				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);
				if (altState != null) {
					piston.setTypeIdAndData(altState.getTypeId(), altState.getData().getData(), false);
				}
			} else if (state.getData() instanceof Attachable) {

				Block attachedBlock = block.getRelative(((Attachable) state.getData()).getAttachedFace());
				if (attachedBlock.getTypeId() == 0) {
					attachedBlock.setTypeId(placeholder.getId(), false);
					TownyRegenAPI.addPlaceholder(attachedBlock);
				}
				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), false);

			} else {

				if (NeedsPlaceholder.contains(state.getType())) {
					Block blockBelow = block.getRelative(BlockFace.DOWN);
					if (blockBelow.getTypeId() == 0) {
						if (state.getType().equals(Material.CROPS)) {
							blockBelow.setTypeId(Material.SOIL.getId(), true);
						} else {
							blockBelow.setTypeId(placeholder.getId(), true);
						}
						TownyRegenAPI.addPlaceholder(blockBelow);
					}
				}
				block.setTypeIdAndData(state.getTypeId(), state.getData().getData(), !NeedsPlaceholder.contains(state.getType()));
			}
			TownyRegenAPI.removePlaceholder(block);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return the blockLocation
	 */
	public BlockLocation getBlockLocation() {

		return blockLocation;
	}

	/**
	 * @param blockLocation the blockLocation to set
	 */
	private void setBlockLocation(BlockLocation blockLocation) {

		this.blockLocation = blockLocation;
	}

	public BlockState getState() {

		return state;
	}

	/**
	 * @return the taskId
	 */
	public int getTaskId() {

		return TaskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(int taskId) {

		TaskId = taskId;
	}
}
