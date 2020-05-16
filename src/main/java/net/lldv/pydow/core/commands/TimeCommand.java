package net.lldv.pydow.core.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class TimeCommand extends PluginCommand<Core> {

    public TimeCommand(Core owner) {
        super(owner, Command.create("time", "Änder die Zeit in deiner Welt",
                new String[]{"pydow.core.command.time"},
                new String[]{},
                new CommandParameter[]{new CommandParameter("time", CommandParamType.INT, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(getPermissions().get(0))) {
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
}
