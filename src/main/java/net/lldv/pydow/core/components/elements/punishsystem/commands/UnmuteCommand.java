package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class UnmuteCommand extends PluginCommand<Core> {

    public UnmuteCommand(Core owner) {
        super(owner, Command.create("unmute", "Hebe die Bestrafung eines Spielers auf",
                new String[]{"pydow.core.punishment.unmute"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 1) {
                String player = args[0];
                if (CoreAPI.getPunishHandler().isMuted(player)) {
                    CoreAPI.getPunishHandler().unMute(player);
                    sender.sendMessage(Language.getAndReplace("player-unmuted", player));
                } else sender.sendMessage(Language.getAndReplace("no-data", player));
            } else sender.sendMessage(Language.getAndReplace("unmute-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
