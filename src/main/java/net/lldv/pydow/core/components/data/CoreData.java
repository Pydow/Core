package net.lldv.pydow.core.components.data;

import cn.nukkit.Server;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.components.language.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoreData {

    public static List<Player> vanish = new ArrayList<>();
    public static HashMap<Player, Player> reply = new HashMap<>();

    public static void updatePlayer(Player player) {
        for (Player player1 : Server.getInstance().getOnlinePlayers().values()) {
            if (player1 == player) continue;
            if (CoreData.vanish.contains(player)) player1.hidePlayer(player);
            else player1.showPlayer(player);
            if (CoreData.vanish.contains(player1)) player.hidePlayer(player1);
        }
    }

    public static void toggleVanish(Player player) {
        if (CoreData.vanish.contains(player)) {
            CoreData.vanish.remove(player);
            player.sendMessage(Language.getAndReplace("vanish-disabled"));
        } else {
            CoreData.vanish.add(player);
            player.sendMessage(Language.getAndReplace("vanish-enabled"));
        }
    }
}
