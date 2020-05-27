package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class MsgCommand extends PluginCommand<Core> {

    public MsgCommand(Core owner) {
        super(owner, Command.create("msg", "Schreibe eine private Nachricht an einen Spieler",
                new String[]{},
                new String[]{"message", "m", "dm", "pn"},
                new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false), new CommandParameter("message", CommandParamType.TEXT, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 2) {
                Player target = Server.getInstance().getPlayer(args[0]);
                if (target == null || CoreData.vanish.contains(target.getName())) {
                    player.sendMessage(Language.getAndReplace("player-not-online", args[0]));
                    return true;
                }
                String message = "";
                for (int i = 1; i < args.length; i++) message = message + args[i] + " ";
                player.sendMessage(Language.getAndReplaceNP("msg-to", message, player.getDisplayName(), target.getDisplayName()));
                target.sendMessage(Language.getAndReplaceNP("msg-from", message, player.getDisplayName(), target.getDisplayName()));
                if (CoreData.reply.containsKey(player) || CoreData.reply.containsKey(target)) {
                    CoreData.reply.remove(player);
                    CoreData.reply.remove(target);
                }
                CoreData.reply.put(player, target);
                CoreData.reply.put(target, player);
            } else player.sendMessage(Language.getAndReplace("msg-usage"));
        }
        return true;
    }
}
