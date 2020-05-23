package net.lldv.pydow.core.components.elements.punishsystem.data;

public class Punishment {

    private final String player;
    private final String id;
    private final String reason;
    private final String creator;
    private final String date;
    private final long time;

    public Punishment(String player, String id, String reason, String creator, String date, long time) {
        this.player = player;
        this.id = id;
        this.reason = reason;
        this.creator = creator;
        this.date = date;
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public String getCreator() {
        return creator;
    }

    public String getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }
}
