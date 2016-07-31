package com.palmergames.bukkit.towny.event;

import com.palmergames.bukkit.towny.object.Nation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class NewNationEvent extends Event  {

    private static final HandlerList handlers = new HandlerList();
    
    private Nation nation;

    @Override
    public HandlerList getHandlers() {
    	
        return handlers;
    }
    
    public static HandlerList getHandlerList() {

		return handlers;
	}

    public NewNationEvent(Nation nation) {
        this.nation = nation;
    }

    /**
     *
     * @return the new nation.
     */
    public Nation getNation() {
        return nation;
    }
    
}
