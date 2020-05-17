package net.lldv.pydow.core.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class ReplyCommand extends PluginCommand<Core> {

    public ReplyCommand(Core owner) {
        super(owner, Command.create("reply", "Antworte einem Spieler schnell",
                new String[]{},
                new String[]{"r"},
                new CommandParameter[]{new CommandParameter("message", CommandParamType.TEXT, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                Player player = (Player) sender;
                Player target = CoreData.reply.get(player);
                if (CoreData.reply.containsValue(target)) {
                    if (target == null || CoreData.vanish.contains(target.getName())) {
                        player.sendMessage(Language.getAndReplace("player-not-online", args[0]));
                        return true;
                    }
                    String message = String.join(" ", args);
                    player.sendMessage(Language.getAndReplaceNoPrefix("msg-to", message, player.getDisplayName(), target.getDisplayName()));
                    target.sendMessage(Language.getAndReplaceNoPrefix("msg-from", message, player.getDisplayName(), target.getDisplayName()));
                    if (CoreData.reply.containsKey(player) || CoreData.reply.containsKey(target)) {
                        CoreData.reply.remove(player);
                        CoreData.reply.remove(target);
                    }
                    CoreData.reply.put(player, target);
                    CoreData.reply.put(target, player);
                } else sender.sendMessage(Language.getAndReplace("nobody-to-reply"));
            } else sender.sendMessage(Language.getAndReplace("reply-usage"));
        }
        return true;
    }
}
