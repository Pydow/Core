package net.lldv.pydow.core.components.event;

import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import cn.nukkit.player.Player;

public class PlayerBanEvent extends PlayerEvent {

    private final String target;
    private final String creator;
    private final String reason;
    private final String id;
    private final String date;
    private final String timeUnit;
    private final double time;
    private static final HandlerList handlers = new HandlerList();

    public PlayerBanEvent(Player player, String target, String creator, String reason, String id, String date, String timeUnit, double time) {
        super(player);
        this.target = target;
        this.creator = creator;
        this.reason = reason;
        this.id = id;
        this.date = date;
        this.timeUnit = timeUnit;
        this.time = time;
    }

    public String getTarget() {
        return target;
    }

    public String getCreator() {
        return creator;
    }

    public String getReason() {
        return reason;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public double getTime() {
        return time;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
