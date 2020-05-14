package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;

public class BroadcastCommand extends PluginCommand<Core> implements CommandFactory {

    public BroadcastCommand(String name, Core owner) {
        super(name, owner);
        setDescription("Schreibe eine wichtige Info an alle Spieler");
        setAliases(new String[]{"bc", "b", "info"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission("pydow.core.command.broadcast")) {
            if (args.length >= 1) {
                String text = "";
                for (int i = 0; i < args.length; ++i) text = text + args[i] + " ";
                Server.getInstance().broadcastMessage(text.replace("&", "§"));
            } else sender.sendMessage(Language.getAndReplace("broadcast-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
