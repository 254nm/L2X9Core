package org.l2x9.l2x9core.mute;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DataHandler {
    private final File mutedYml = new File("plugins/L2X9Core/Muted.yml");
    private final FileConfiguration mutedConfig = new YamlConfiguration();
    public String path = "MutedPlayers.List";
    List<String> mutedPlayers = getMutedConfig().getStringList(path);

    public FileConfiguration getMutedConfig() {
        return mutedConfig;
    }

    public String getPath() {
        return path;
    }

    public void saveMutedYml() {
        try {
            mutedConfig.save(mutedYml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMutedYml() {
        if (!mutedYml.exists()) {
            try {
                mutedYml.createNewFile();
                mutedConfig.load(mutedYml);
                mutedConfig.set(path, Collections.emptyList());
                mutedConfig.save(mutedYml);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mutedConfig.load(mutedYml);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }
}
