package com.palmergames.bukkit.towny.questioner;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public abstract class TownyQuestionTask {

	protected Towny towny;
	protected TownyUniverse universe;

	public TownyUniverse getUniverse() {

		return universe;
	}

	public void setTowny(Towny towny) {

		this.towny = towny;
		this.universe = towny.getTownyUniverse();
	}

	public abstract void run();
}
