package net.lldv.pydow.core.components.elements.warpsystem;

import cn.nukkit.Server;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.Warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WarpHandler {

    public HashMap<String, Warp> cachedWarps = new HashMap<>();
    public ArrayList<String> warpNames = new ArrayList<>();
    public Config config = new Config(Core.getInstance().getDataFolder() + "/data/warps.yml", Config.YAML);

    public void createWarp(String name, Location location) {
        config.set("Warps." + name + ".X", location.getX());
        config.set("Warps." + name + ".Y", location.getY());
        config.set("Warps." + name + ".Z", location.getZ());
        config.set("Warps." + name + ".Yaw", location.getYaw());
        config.set("Warps." + name + ".Pitch", location.getPitch());
        config.set("Warps." + name + ".Level", location.getLevel().getName());
        List<String> list = config.getStringList("Names");
        list.add(name);
        config.set("Names", list);
        config.save();
        cachedWarps.put(name, new Warp(name, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getLevel()));
        warpNames.add(name);
    }

    public void deleteWarp(String name) {
        Map<String, Object> map = config.getSection("Warps").getAllMap();
        map.remove(name);
        config.set("Warps", map);
        List<String> list = config.getStringList("Names");
        list.remove(name);
        config.set("Names", list);
        config.save();
        cachedWarps.remove(name);
        warpNames.remove(name);
    }

    public boolean warpExists(String name) {
        return cachedWarps.get(name) != null;
    }

    public void cacheAllWarps() {
        Core.getInstance().saveResource("/data/warps.yml");
        CompletableFuture.runAsync(() -> config.getStringList("Names").forEach(s -> {
            double x = config.getDouble("Warps." + s + ".X");
            double y = config.getDouble("Warps." + s + ".Y");
            double z = config.getDouble("Warps." + s + ".Z");
            double yaw = config.getDouble("Warps." + s + ".Yaw");
            double pitch = config.getDouble("Warps." + s + ".Pitch");
            String world = config.getString("Warps." + s + ".Level");
            //if (!Server.getInstance().isLevelLoaded(world)) Server.getInstance().loadLevel(world);
            Level level = Server.getInstance().getLevelByName(world);
            cachedWarps.put(s, new Warp(s, x, y, z, yaw, pitch, level));
            warpNames.add(s);
        }));
    }

}
