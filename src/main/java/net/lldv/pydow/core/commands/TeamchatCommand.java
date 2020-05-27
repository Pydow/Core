package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class TeamchatCommand extends PluginCommand<Core> {

    public TeamchatCommand(Core owner) {
        super(owner, Command.create("teamchat", "Schreibe eine Nachricht an alle Teammitglieder",
                new String[]{"pydow.core.command.teamchat"},
                new String[]{"tc"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length >= 1) {
                String text = String.join(" ", args);
                Server.getInstance().getOnlinePlayers().forEach((uuid, player) -> {
                    if (player.hasPermission("pydow.core.command.teamchat")) player.sendMessage(Language.getAndReplaceNP("teamchat", sender.getName(), text));
                });
            } else sender.sendMessage(Language.getAndReplace("teamchat-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
