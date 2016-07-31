package com.palmergames.bukkit.towny.listeners;

import com.palmergames.bukkit.towny.Towny;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class TownyWeatherListener implements Listener {

	private final Towny plugin;

	public TownyWeatherListener(Towny instance) {

		plugin = instance;
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onLightningStrike(LightningStrikeEvent event) {

	}

	/**
	 * @return the plugin
	 */
	public Towny getPlugin() {

		return plugin;
	}

}