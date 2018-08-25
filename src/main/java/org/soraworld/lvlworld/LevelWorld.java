package org.soraworld.lvlworld;

import org.soraworld.violet.Violet;
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
        version = "1.1.2",
        description = "Level World Plugin.",
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

    @Nonnull
    public SpongeCommand registerCommand() {
        return new LevelCommand("lvlworld.admin", false, (LevelManager) this.manager, "lvlworld");
    }

    @Nullable
    public List<Object> registerListeners() {
        ArrayList<Object> listeners = new ArrayList<>();
        listeners.add(new EventListener((LevelManager) manager));
        return listeners;
    }
}
