package net.lldv.pydow.core.listener;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;

import java.util.ArrayList;

public class EventListener implements Listener {

    private ArrayList<String> chatPlayer = new ArrayList<>();

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

    @EventHandler
    public void on(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!message.startsWith("/")) {
            if (!chatPlayer.contains(player.getName())) {
                chatPlayer.add(player.getName());
                Server.getInstance().getScheduler().scheduleDelayedTask(Core.getInstance(), () -> chatPlayer.remove(player.getName()), 15);
            } else {
                event.setCancelled(true);
                player.sendMessage(Language.getAndReplace("no-spam"));
            }
        }
    }

}
