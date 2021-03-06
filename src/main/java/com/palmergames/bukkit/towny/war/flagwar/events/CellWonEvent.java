package com.palmergames.bukkit.towny.war.flagwar.events;

import com.palmergames.bukkit.towny.war.flagwar.CellUnderAttack;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CellWonEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {

		return handlers;
	}

	public static HandlerList getHandlerList() {

		return handlers;
	}

	//////////////////////////////

	private CellUnderAttack cellAttackData;

	public CellWonEvent(CellUnderAttack cellAttackData) {

		super();
		this.cellAttackData = cellAttackData;
	}

	public CellUnderAttack getCellAttackData() {

		return cellAttackData;
	}
}
