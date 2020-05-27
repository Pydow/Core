package net.lldv.pydow.core.components.data;

import cn.nukkit.level.Level;

public class Warp {

    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final double yaw;
    private final double pitch;
    private final Level level;

    public Warp(String name, double x, double y, double z, double yaw, double pitch, Level level) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public Level getLevel() {
        return level;
    }

}
