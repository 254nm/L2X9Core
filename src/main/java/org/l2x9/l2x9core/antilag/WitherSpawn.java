package org.l2x9.l2x9core.antilag;

import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.l2x9.l2x9core.util.Utils;

public class WitherSpawn implements Listener {
	@EventHandler
	public void onWitherSpawn(EntitySpawnEvent event) {
		try {
			if (event.getEntity() instanceof Wither) {
				if (Utils.getTps() <= 16) {
					event.setCancelled(true);

				}
			}
		} catch (Error | Exception throwable) {
			Utils.reportException(throwable);
			throwable.printStackTrace();
		}
	}
}