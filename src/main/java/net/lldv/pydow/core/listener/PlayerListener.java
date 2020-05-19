package net.lldv.pydow.core.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CoreData.updatePlayer(player);
        player.setCheckMovement(false);
        event.setJoinMessage(Language.getAndReplaceNoPrefix("player-joined", player.getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Language.getAndReplaceNoPrefix("player-quit", player.getName()));
    }

    /*@EventHandler
    public void onQuery(QueryRegenerateEvent event) {
        Player[] playerList = event.getPlayerList();
        List<Player> players = Arrays.asList(playerList);
        players.removeIf(player -> CoreData.vanish.contains(player.getName()));
        Player[] playerNew = players.toArray(new Player[]{});
        event.setPlayerList(playerNew);
    }*/

}
