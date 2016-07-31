package com.palmergames.bukkit.towny.event;

import com.palmergames.bukkit.towny.object.Nation;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class RenameNationEvent extends Event  {

    private static final HandlerList handlers = new HandlerList();
    
    private String oldName;
    private Nation nation;

    @Override
    public HandlerList getHandlers() {
    	
        return handlers;
    }
    
    public static HandlerList getHandlerList() {

		return handlers;
	}

    public RenameNationEvent(String oldName, Nation nation) {
        this.oldName = oldName;
        this.nation = nation;
    }

    /**
     *
     * @return the old nation name.
     */
    public String getOldName() {
        return oldName;
    }
    
    /**
    *
    * @return the nation with it's changed name
    */
   public Nation getNation() {
       return this.nation;
   }
    
}