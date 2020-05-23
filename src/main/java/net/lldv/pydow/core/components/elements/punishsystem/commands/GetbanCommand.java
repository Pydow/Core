package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.elements.punishsystem.data.Punishment;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;
import net.lldv.pydow.core.components.tools.TimeTool;

public class GetbanCommand extends PluginCommand<Core> {

    public GetbanCommand(Core owner) {
        super(owner, Command.create("getban", "Erhalte Informationen über einen aktiven Bann",
                new String[]{"pydow.core.punishment.getban"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 1) {
                String id = args[0];
                if (CoreAPI.getPunishHandler().banIdExists(id)) {
                    Punishment punishment = CoreAPI.getPunishHandler().getPunishmentById(id, "ban");
                    sender.sendMessage(Language.getAndReplace("getban-info"));
                    sender.sendMessage(Language.getAndReplace("getban-player", punishment.getPlayer()));
                    sender.sendMessage(Language.getAndReplace("getban-reason", punishment.getReason()));
                    sender.sendMessage(Language.getAndReplace("getban-creator", punishment.getCreator()));
                    sender.sendMessage(Language.getAndReplace("getban-id", punishment.getId()));
                    sender.sendMessage(Language.getAndReplace("getban-date", punishment.getDate()));
                    sender.sendMessage(Language.getAndReplace("getban-duration", TimeTool.durationInString(punishment.getTime())));
                } else if (CoreAPI.getPunishHandler().isBanned(id)) {
                    Punishment punishment = CoreAPI.getPunishHandler().getPunishment(id, "ban");
                    sender.sendMessage(Language.getAndReplace("getban-info"));
                    sender.sendMessage(Language.getAndReplace("getban-player", punishment.getPlayer()));
                    sender.sendMessage(Language.getAndReplace("getban-reason", punishment.getReason()));
                    sender.sendMessage(Language.getAndReplace("getban-creator", punishment.getCreator()));
                    sender.sendMessage(Language.getAndReplace("getban-id", punishment.getId()));
                    sender.sendMessage(Language.getAndReplace("getban-date", punishment.getDate()));
                    sender.sendMessage(Language.getAndReplace("getban-duration", TimeTool.durationInString(punishment.getTime())));
                } else sender.sendMessage(Language.getAndReplace("no-data", id));
            } else sender.sendMessage(Language.getAndReplace("getban-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
