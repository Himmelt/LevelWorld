package org.soraworld.lvlworld;

import org.soraworld.violet.Violet;
import org.soraworld.violet.command.SpongeBaseSubs;
import org.soraworld.violet.command.SpongeCommand;
import org.soraworld.violet.manager.SpongeManager;
import org.soraworld.violet.plugin.SpongePlugin;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Plugin(
        id = "lvlworld",
        name = "LevelWorld",
        version = "1.1.3",
        description = "LevelWorld Plugin.",
        url = "https://github.com/Himmelt/LevelWorld",
        authors = {"Himmelt"},
        dependencies = {@Dependency(
                id = Violet.PLUGIN_ID,
                version = Violet.PLUGIN_VERSION)
        }
)
public class LevelWorld extends SpongePlugin {

    @Nonnull
    public String assetsId() {
        return "lvlworld";
    }

    @Nonnull
    public SpongeManager registerManager(Path path) {
        return new LevelManager(this, path);
    }

    public void registerCommands() {
        SpongeCommand command = new SpongeCommand(getId(), manager.defAdminPerm(), false, manager);
        command.extractSub(SpongeBaseSubs.class, "lang");
        command.extractSub(SpongeBaseSubs.class, "debug");
        command.extractSub(SpongeBaseSubs.class, "save");
        command.extractSub(SpongeBaseSubs.class, "reload");
        command.extractSub(LevelCommand.class);
        command.setUsage("/lvlworld level|default|force");
        register(this, command);
    }

    @Nullable
    public List<Object> registerListeners() {
        ArrayList<Object> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }
}
