package org.soraworld.levelworld;

import org.bukkit.event.Listener;
import org.soraworld.violet.VioletPlugin;
import org.soraworld.violet.command.IICommand;
import org.soraworld.violet.config.IIConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LevelWorld extends VioletPlugin {

    protected IIConfig registerConfig(File file) {
        return new LevelConfig(file, this);
    }

    protected List<Listener> registerEvents(IIConfig config) {
        ArrayList<Listener> listeners = new ArrayList<>();
        if (config instanceof LevelConfig) {
            listeners.add(new EventListener((LevelConfig) config));
        }
        return listeners;
    }

    protected IICommand registerCommand(IIConfig config) {
        return new LevelCommand("lvlworld", "lvlworld.admin", (LevelConfig) this.config, this);
    }

    protected void afterEnable() {

    }

    protected void beforeDisable() {

    }
}
