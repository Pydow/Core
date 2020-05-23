package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class MuteCommand extends PluginCommand<Core> {

    public MuteCommand(Core owner) {
        super(owner, Command.create("mute", "Nimm einem bösen Spieler das Mitspracherecht weg",
                new String[]{"pydow.core.punishment.mute"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length >= 4) {
                String player = args[0];
                String timeString = args[1];
                try {
                    double time = Double.parseDouble(args[2]);
                    String reason = "";
                    for (int i = 3; i < args.length; ++i) reason = reason + args[i] + " ";
                    reason = reason.substring(0, reason.length() - 1);
                    if (!CoreAPI.getPunishHandler().isMuted(player)) {
                        CoreAPI.getPunishHandler().createMute(player, sender.getName(), reason, timeString, time);
                        sender.sendMessage(Language.getAndReplace("player-muted", player, reason));
                    } else sender.sendMessage(Language.getAndReplace("already-set", player));
                } catch (Exception e) {
                    sender.sendMessage(Language.getAndReplace("invalid-time"));
                }
            } else sender.sendMessage(Language.getAndReplace("mute-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
