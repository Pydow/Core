package net.lldv.pydow.core.components.data;

import cn.nukkit.player.Player;

import java.util.HashMap;

public class TpaData {

    public static HashMap<String, TpaData> tpaRequest = new HashMap<>();

    private final Player requester;
    private final Player receiver;
    private final Long expiry;

    public TpaData(Player requester, Player receiver, Long expiry) {
        this.requester = requester;
        this.receiver = receiver;
        this.expiry = expiry;
    }

    public Player getRequester() {
        return requester;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Long getExpiry() {
        return expiry;
    }
}
