package org.l2x9.l2x9core.listeners.antilag;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.l2x9.l2x9core.util.TenSecondPassEvent;

import java.util.Map;

public class EntityPerChunkLimit implements Listener {
	@EventHandler
	public void onSecondPass(TenSecondPassEvent event) {
		try {
			if (event.getPlugin().getConfigBoolean("EntityAmounts.EntityAmounts-Enabled")) {
				for (World world : Bukkit.getWorlds()) {
					for (Chunk chunk : world.getLoadedChunks()) {
						for (Entity entity : chunk.getEntities()) {
							for (Map.Entry<String, Integer> integerEntry : event.getPlugin().getEntityAmounts().entrySet()) {
								EntityType type = EntityType.valueOf(integerEntry.getKey());
								int maxAmount = integerEntry.getValue();
								int amount = countEntityPerChunk(chunk, type);
								if (entity.getType() == type) {
									if (amount > maxAmount) {
										entity.remove();
									}
								}
							}
						}
					}
				}
			}
		} catch (Error | Exception ignored) {
		}
	}

	private int countEntityPerChunk(Chunk chunk, EntityType lookingFor) {
		int amount = 0;
		for (Entity entity : chunk.getEntities()) {
			if (entity.getType() == lookingFor) {
				amount++;
			}
		}
		return amount;
	}
}
