package net.lldv.pydow.core.components.elements.punishsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class UnbanCommand extends PluginCommand<Core> {


    public UnbanCommand(Core owner) {
        super(owner, Command.create("unban", "Hebe die Bestrafung eines Spieler auf",
                new String[]{"pydow.core.punishment.unban"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 1) {
                String player = args[0];
                if (CoreAPI.getPunishHandler().isBanned(player)) {
                    CoreAPI.getPunishHandler().unBan(player);
                    sender.sendMessage(Language.getAndReplace("player-unbanned", player));
                } else sender.sendMessage(Language.getAndReplace("no-data", player));
            } else sender.sendMessage(Language.getAndReplace("unban-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
