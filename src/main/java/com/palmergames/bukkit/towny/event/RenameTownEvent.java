package com.palmergames.bukkit.towny.event;

import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class RenameTownEvent extends Event  {

    private static final HandlerList handlers = new HandlerList();
    
    private String oldName;
    private Town town;

    @Override
    public HandlerList getHandlers() {
    	
        return handlers;
    }
    
    public static HandlerList getHandlerList() {

		return handlers;
	}

    public RenameTownEvent(String oldName, Town town) {
        this.oldName = oldName;
        this.town = town;
    }

    /**
     *
     * @return the old town name.
     */
    public String getOldName() {
        return oldName;
    }
    
    /**
    *
    * @return the town with it's changed name
    */
   public Town getTown() {
       return this.town;
   }
    
}