package org.l2x9.l2x9core.listeners.patches;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class FallingBlock implements Runnable {
	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			for (Chunk chunk : world.getLoadedChunks()) {
				for (Entity entity : chunk.getEntities()) {
					if (entity instanceof FallingBlock) {
						entity.remove();
					}
				}
			}
		}
	}
}
