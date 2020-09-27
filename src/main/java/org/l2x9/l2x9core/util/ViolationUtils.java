package org.l2x9.l2x9core.util;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ViolationUtils {

    private final HashMap<Player, Integer> vlMap = new HashMap<Player, Integer>();

    public void addVls(Player player, int vls) {
        if (!vlMap.containsKey(player)) {
            vlMap.put(player, vls);
        } else {
            vlMap.replace(player, vlMap.get(player) + 1);
        }
    }

    public void resetVls(Player player) {
        vlMap.remove(player);
    }

    public int getVls(Player player) {
        return vlMap.get(player);
    }

    public void removeVl(Player player, int vlsToRemove) {
        if (vlMap.containsKey(player)) {
            vlMap.put(player, vlsToRemove);

        }
    }

    public boolean vlMapContainsPlayer(Player player) {
        return vlMap.containsKey(player);

    }

    public void resetMap() {
        vlMap.clear();
    }
}
