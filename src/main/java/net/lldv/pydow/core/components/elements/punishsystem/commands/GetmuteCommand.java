package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.elements.punishsystem.data.Punishment;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;
import net.lldv.pydow.core.components.tools.TimeTool;

public class GetmuteCommand extends PluginCommand<Core> {

    public GetmuteCommand(Core owner) {
        super(owner, Command.create("getmute", "Erhalte Informationen über einen aktiven Mute",
                new String[]{"pydow.core.punishment.getmute"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 1) {
                String id = args[0];
                if (CoreAPI.getPunishHandler().muteIdExists(id)) {
                    Punishment punishment = CoreAPI.getPunishHandler().getPunishmentById(id, "mute");
                    sender.sendMessage(Language.getAndReplace("getmute-info"));
                    sender.sendMessage(Language.getAndReplace("getmute-player", punishment.getPlayer()));
                    sender.sendMessage(Language.getAndReplace("getmute-reason", punishment.getReason()));
                    sender.sendMessage(Language.getAndReplace("getmute-creator", punishment.getCreator()));
                    sender.sendMessage(Language.getAndReplace("getmute-id", punishment.getId()));
                    sender.sendMessage(Language.getAndReplace("getmute-date", punishment.getDate()));
                    sender.sendMessage(Language.getAndReplace("getmute-duration", TimeTool.durationInString(punishment.getTime())));
                } else if (CoreAPI.getPunishHandler().isMuted(id)) {
                    Punishment punishment = CoreAPI.getPunishHandler().getPunishment(id, "mute");
                    sender.sendMessage(Language.getAndReplace("getmute-info"));
                    sender.sendMessage(Language.getAndReplace("getmute-player", punishment.getPlayer()));
                    sender.sendMessage(Language.getAndReplace("getmute-reason", punishment.getReason()));
                    sender.sendMessage(Language.getAndReplace("getmute-creator", punishment.getCreator()));
                    sender.sendMessage(Language.getAndReplace("getmute-id", punishment.getId()));
                    sender.sendMessage(Language.getAndReplace("getmute-date", punishment.getDate()));
                    sender.sendMessage(Language.getAndReplace("getmute-duration", TimeTool.durationInString(punishment.getTime())));
                } else sender.sendMessage(Language.getAndReplace("no-data", id));
            } else sender.sendMessage(Language.getAndReplace("getmute-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
