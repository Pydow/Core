package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.TpaData;
import net.lldv.pydow.core.components.language.Language;

public class TpaCommand extends PluginCommand<Core> implements CommandFactory {

    public TpaCommand(String name, Core owner) {
        super(name, owner);
        setDescription("Sende eine Teleportationsanfrage an einen Spieler");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player player1 = Server.getInstance().getPlayer(args[0]);
                if (player1 == null) {
                    player.sendMessage(Language.getAndReplace("player-not-online"));
                    return true;
                }
                TpaData.tpaRequest.put(player1.getName(), new TpaData(player, player1, System.currentTimeMillis() + 30000));
                player.sendMessage(Language.getAndReplace("request-sent", player1.getName()));
                player1.sendMessage(Language.getAndReplace("request-received", player.getName()));
                player1.sendMessage(Language.getAndReplace("request-received-info"));
            } else player.sendMessage(Language.getAndReplace("tpa-usage"));
        }
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
