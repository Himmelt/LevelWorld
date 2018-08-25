package org.soraworld.lvlworld;

import org.bukkit.event.Listener;
import org.soraworld.violet.command.SpigotCommand;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.violet.plugin.SpigotPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LevelWorld extends SpigotPlugin {

    @Nonnull
    public String assetsId() {
        return "lvlworld";
    }

    @Nonnull
    public SpigotManager registerManager(Path path) {
        return new LevelManager(this, path);
    }

    @Nonnull
    public SpigotCommand registerCommand() {
        return new LevelCommand("lvlworld.admin", false, (LevelManager) this.manager, "lvlworld");
    }

    @Nullable
    public List<Listener> registerListeners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }
}
