package net.lldv.pydow.core.components.data;

import cn.nukkit.level.Location;

public class Home {

    private final Location location;
    private final String name;
    private final String player;

    public Home(Location location, String name, String player) {
        this.location = location;
        this.name = name;
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPlayer() {
        return player;
    }

}
