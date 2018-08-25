package org.soraworld.lvlworld;

import org.soraworld.hocon.node.Node;
import org.soraworld.hocon.node.NodeBase;
import org.soraworld.hocon.node.NodeMap;
import org.soraworld.hocon.node.Options;
import org.soraworld.hocon.serializer.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.HashMap;

import static org.spongepowered.api.util.Coerce.toDouble;

public class Location {

    private final String name;
    private final double x, y, z;

    public Location(org.spongepowered.api.world.Location<World> loc) {
        this.name = loc.getExtent().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
    }

    public Location(String name, double x, double y, double z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public org.spongepowered.api.world.Location<World> toLocation() {
        World world = Sponge.getServer().getWorld(name).orElse(null);
        if (world != null) {
            return new org.spongepowered.api.world.Location<>(world, x, y, z);
        }
        return null;
    }

    public World getWorld() {
        return Sponge.getServer().getWorld(name).orElse(null);
    }

    public static class Serializer implements TypeSerializer<Location> {

        public Location deserialize(@Nonnull Type type, @Nonnull Node node) {
            if (node instanceof NodeMap) {
                HashMap<String, String> map = ((NodeMap) node).asStringMap();
                if (map.containsKey("world") && map.containsKey("x") && map.containsKey("y") && map.containsKey("z")) {
                    return new Location(map.get("world"), toDouble(map.get("x")), toDouble(map.get("y")), toDouble(map.get("z")));
                }
            }
            return null;
        }

        public Node serialize(@Nonnull Type type, Location loc, @Nonnull Options options) {
            if (loc != null) {
                NodeMap map = new NodeMap(options);
                map.set("world", loc.name);
                map.set("x", loc.x);
                map.set("y", loc.y);
                map.set("z", loc.z);
                return map;
            } else return new NodeBase(options, null, false);
        }

        @Nonnull
        public Type getRegType() {
            return Location.class;
        }
    }
}
