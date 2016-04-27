package br.com.floodeer.ultragadgets.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class Blocks {

	private Block block;
	private Material type;
	private byte data;

	@SuppressWarnings("deprecation")
	public Blocks(Block block) {
		UtilBlock.blockstorestore.add(block);
		this.block = block;
		this.type = block.getType();
		this.data = block.getData();
	}

	@SuppressWarnings("deprecation")
	public void restore() {
		UtilBlock.blocktorestore.remove(this.block);
		this.block.setType(this.type);
		this.block.setData(this.data);
	}

	public Block getBlock() {
		return this.block;
	}
}
