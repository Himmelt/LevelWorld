package org.soraworld.lvlworld;

import org.soraworld.violet.Violet;
import org.soraworld.violet.command.CommandArgs;
import org.soraworld.violet.command.SpongeCommand;
import org.spongepowered.api.entity.living.player.Player;

public class LevelCommand extends SpongeCommand.CommandViolet {
    public LevelCommand(String perm, boolean onlyPlayer, LevelManager level, String... aliases) {
        super(perm, onlyPlayer, level, aliases);
        addSub(new SpongeCommand(perm, true, level, "level") {
            public void execute(Player player, CommandArgs args) {
                if (args.notEmpty()) {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        level.setLevel(player.getWorld(), lvl < 0 ? 0 : lvl);
                        manager.sendKey(player, "setLevel", level.getLevel(player.getWorld()));
                    } catch (Throwable ignored) {
                        manager.sendKey(player, Violet.KEY_INVALID_INT);
                    }
                } else manager.sendKey(player, "getLevel", level.getLevel(player.getWorld()));
            }
        });
        addSub(new SpongeCommand(perm, true, level, "default") {
            public void execute(Player player, CommandArgs args) {
                if (args.notEmpty()) {
                    try {
                        int lvl = Integer.valueOf(args.get(0));
                        level.setDefaultLevel(lvl < 0 ? 0 : lvl);
                        manager.sendKey(player, "setDefault", level.getDefaultLevel());
                    } catch (Throwable ignored) {
                        manager.sendKey(player, Violet.KEY_INVALID_INT);
                    }
                } else manager.sendKey(player, "getDefault", level.getDefaultLevel());
            }
        });
        addSub(new SpongeCommand(perm, true, level, "force") {
            public void execute(Player player, CommandArgs args) {
                level.setForceRespawn(new Location(player.getLocation()));
                manager.sendKey(player, "setForceRespawn");
            }
        });
    }

    public String getUsage() {
        return "/lvlworld level|default|force";
    }
}
