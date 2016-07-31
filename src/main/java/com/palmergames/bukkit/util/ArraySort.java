package com.palmergames.bukkit.util;

import org.bukkit.block.Block;

import java.util.Comparator;

/**
 * @author ElgarL
 * 
 */
public class ArraySort implements Comparator<Block> {

	@Override
	public int compare(Block blockA, Block blockB) {

		return blockA.getY() - blockB.getY();
	}

	private static ArraySort instance;

	public static ArraySort getInstance() {

		if (instance == null) {
			instance = new ArraySort();
		}
		return instance;
	}
}
