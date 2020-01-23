package org.soraworld.lvlworld;

import org.bukkit.event.Listener;
import org.soraworld.violet.command.SpigotBaseSubs;
import org.soraworld.violet.command.SpigotCommand;
import org.soraworld.violet.manager.SpigotManager;
import org.soraworld.violet.plugin.SpigotPlugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Himmelt
 */
public final class LevelWorld extends SpigotPlugin {

    @Override
    public String assetsId() {
        return "lvlworld";
    }

    @Override
    public String getId() {
        return "lvlworld";
    }

    @Override
    public SpigotManager registerManager(Path path) {
        return new LevelManager(this, path);
    }

    @Override
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

    @Override
    public List<Listener> registerListeners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }
}
