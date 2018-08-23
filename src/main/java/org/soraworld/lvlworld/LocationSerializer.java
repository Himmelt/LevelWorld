package org.soraworld.lvlworld;

import org.soraworld.hocon.node.Node;
import org.soraworld.hocon.node.NodeBase;
import org.soraworld.hocon.node.NodeMap;
import org.soraworld.hocon.node.Options;
import org.soraworld.hocon.reflect.TypeToken;
import org.soraworld.hocon.serializer.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.HashMap;

import static org.spongepowered.api.util.Coerce.toDouble;

public class LocationSerializer implements TypeSerializer<Location<World>> {

    public Location<World> deserialize(@Nonnull Type type, @Nonnull Node node) {
        if (node instanceof NodeMap) {
            HashMap<String, String> map = ((NodeMap) node).asStringMap();
            String name = map.get("world");
            if (name != null && !name.isEmpty()) {
                World world = Sponge.getServer().getWorld(name).orElse(null);
                if (world != null) {
                    return new Location<>(world, toDouble(map.get("x")), toDouble(map.get("y")), toDouble(map.get("z")));
                }
            }
        }
        return null;
    }

    public Node serialize(@Nonnull Type type, Location<World> loc, @Nonnull Options options) {
        if (loc != null) {
            NodeMap map = new NodeMap(options);
            World world = null;
            try {
                world = loc.getExtent();
            } catch (Throwable ignored) {
            }
            map.set("world", world == null ? null : world.getName());
            map.set("x", loc.getX());
            map.set("y", loc.getY());
            map.set("z", loc.getZ());
            return map;
        } else return new NodeBase(options, null, false);
    }

    @Nonnull
    public Type getRegType() {
        return new TypeToken<Location<World>>() {
        }.getType();
    }
}
