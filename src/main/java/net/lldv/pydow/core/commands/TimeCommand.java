package net.lldv.pydow.core.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;

public class TimeCommand extends PluginCommand<Core> implements CommandFactory {

    public TimeCommand(String name, Core owner) {
        super(name, owner);
        setDescription("Änder die Zeit in deiner Welt");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("pydow.core.command.time")) {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    try {
                        int time = Integer.parseInt(args[0]);
                        player.getLevel().setTime(time);
                        player.sendMessage(Language.getAndReplace("time-set"));
                    } catch (NumberFormatException exception) {
                        player.sendMessage(Language.getAndReplace("invalid-time"));
                    }
                } else sender.sendMessage(Language.getAndReplace("time-usage"));
            } else sender.sendMessage(Language.getAndReplace("no-permission"));
        }
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
