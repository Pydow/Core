package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class HistoryCommand extends PluginCommand<Core> {

    public HistoryCommand(Core owner) {
        super(owner, Command.create("history", "",
                new String[]{"pydow.core.punishment.history"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 2) {
                String player = args[0];
                String type = args[1];
                if (type.equals("ban") || type.equals("mute")) {
                    int i = CoreAPI.getPunishHandler().getHistory(player, type).size();
                    if (i == 0) {
                        sender.sendMessage(Language.getAndReplace("no-data", player));
                        return true;
                    }
                    sender.sendMessage(Language.getAndReplace("history-info", player, i, type));
                    CoreAPI.getPunishHandler().getHistory(player, type).forEach(punishment -> {
                        sender.sendMessage(Language.getAndReplace("history-reason", punishment.getReason()));
                        sender.sendMessage(Language.getAndReplace("history-creator", punishment.getCreator()));
                        sender.sendMessage(Language.getAndReplace("history-id", punishment.getId()));
                        sender.sendMessage(Language.getAndReplace("history-date", punishment.getDate()));
                        sender.sendMessage("\n");
                    });
                } else sender.sendMessage(Language.getAndReplace("history-usage"));
            } else sender.sendMessage(Language.getAndReplace("history-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
