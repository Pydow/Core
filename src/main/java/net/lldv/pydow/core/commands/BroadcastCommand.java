package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class BroadcastCommand extends PluginCommand<Core> {

    public BroadcastCommand(Core owner) {
        super(owner, Command.create("broadcast", "Schreibe eine wichtige Info an alle Spieler",
                new String[]{"pydow.core.command.broadcast"},
                new String[]{"bc", "b", "say"},
                new CommandParameter[]{new CommandParameter("message", CommandParamType.TEXT, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length >= 1) {
                String text = String.join(" ", args);
                Server.getInstance().broadcastMessage(text.replace("&", "§"));
            } else sender.sendMessage(Language.getAndReplace("broadcast-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
