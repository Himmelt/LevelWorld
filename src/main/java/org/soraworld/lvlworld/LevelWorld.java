package org.soraworld.lvlworld;

import org.bukkit.event.Listener;
import org.soraworld.violet.command.SpigotBaseSubs;
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
    public String getId() {
        return "lvlworld";
    }

    @Nonnull
    public SpigotManager registerManager(Path path) {
        return new LevelManager(this, path);
    }

    public void registerCommands() {
        SpigotCommand command = new SpigotCommand(this.getId(), this.manager.defAdminPerm(), false, this.manager);
        command.extractSub(SpigotBaseSubs.class, "lang");
        command.extractSub(SpigotBaseSubs.class, "debug");
        command.extractSub(SpigotBaseSubs.class, "save");
        command.extractSub(SpigotBaseSubs.class, "reload");
        command.extractSub(LevelCommand.class);
        command.setUsage("/lvlworld level|default|force");
        register(this, command);
    }

    @Nullable
    public List<Listener> registerListeners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }
}
