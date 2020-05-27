package net.lldv.pydow.core.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;

public class PlayerListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CoreData.updatePlayer(player);
        player.setCheckMovement(false);
        event.setJoinMessage(Language.getAndReplaceNP("player-joined", player.getName()));
        CoreAPI.getHomeHandler().cachePlayerHomes(player.getName());
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Language.getAndReplaceNP("player-quit", player.getName()));
    }

}
