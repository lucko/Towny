package com.palmergames.bukkit.towny.war.flagwar.events;

import com.palmergames.bukkit.towny.war.flagwar.CellUnderAttack;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CellAttackCanceledEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

	public static HandlerList getHandlerList() {

		return handlers;
	}

	//////////////////////////////
	private CellUnderAttack cell;

	public CellAttackCanceledEvent(CellUnderAttack cell) {

		super();
		this.cell = cell;

	}

	public CellUnderAttack getCell() {

		return cell;
	}
}
