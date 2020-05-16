package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.data.TpaData;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class TpaCommand extends PluginCommand<Core> {

    public TpaCommand(Core owner) {
        super(owner, Command.create("tpa", "Sende eine Teleportationsanfrage an einen Spieler",
                new String[]{},
                new String[]{},
                new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player player1 = Server.getInstance().getPlayer(args[0]);
                if (player1 == null || CoreData.vanish.contains(player1.getName())) {
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
}
