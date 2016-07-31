package com.palmergames.bukkit.towny.event;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class NewTownEvent extends Event  {

    private static final HandlerList handlers = new HandlerList();
    
    private Town town;

    @Override
    public HandlerList getHandlers() {
    	
        return handlers;
    }
    
    public static HandlerList getHandlerList() {

		return handlers;
	}

    public NewTownEvent(Town town) {
        this.town = town;
    }

    /**
     *
     * @return the new town.
     */
    public Town getTown() {
        return town;
    }
    
}