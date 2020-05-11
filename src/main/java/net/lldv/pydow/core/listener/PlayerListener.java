package net.lldv.pydow.core.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setCheckMovement(false);
    }

}
