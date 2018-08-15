package org.soraworld.lvlworld;

import org.bukkit.event.Listener;
import org.soraworld.violet.VioletPlugin;
import org.soraworld.violet.api.IManager;
import org.soraworld.violet.command.ICommand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LevelWorld extends VioletPlugin {

    @Nonnull
    public String getId() {
        return "lvlworld";
    }

    @Nonnull
    public IManager registerManager(Path path) {
        return new LevelManager(this, path);
    }

    @Nonnull
    public List<Listener> registerEvents() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }

    @Nullable
    public ICommand registerCommand() {
        return new LevelCommand("lvlworld.admin", false, (LevelManager) this.manager, "lvlworld");
    }
}
