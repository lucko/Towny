package com.palmergames.bukkit.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.TimerTask;

public class ServerBroadCastTimerTask extends TimerTask {

	private JavaPlugin plugin;
	private String msg;

	public ServerBroadCastTimerTask(JavaPlugin plugin, String msg) {

		this.plugin = plugin;
		this.msg = msg;
	}

	@Override
	public void run() {

		for (Player player : plugin.getServer().getOnlinePlayers())
			player.sendMessage(msg);
	}

}
