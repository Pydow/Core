package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class FlyCommand extends PluginCommand<Core> {

    public FlyCommand(Core owner) {
        super(owner, Command.create("fly", "Fliege wie ein Schmetterling durch die Luft",
                new String[]{"pydow.core.command.fly"},
                new String[]{},
                new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 0 && sender instanceof Player) {
                Player player = (Player) sender;
                if (player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    player.sendMessage(Language.getAndReplace("fly-disabled"));
                } else if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    player.sendMessage(Language.getAndReplace("fly-enabled"));
                }
            } else if (args.length == 1 && sender.hasPermission("pydow.core.command.fly.others")) {
                Player player = Server.getInstance().getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(Language.getAndReplace("player-not-online", args[0]));
                    return true;
                }
                if (player.getAllowFlight()) {
                    player.setAllowFlight(false);
                    player.sendMessage(Language.getAndReplace("fly-disabled"));
                    sender.sendMessage(Language.getAndReplace("fly-disabled-for", args[0]));
                } else if (!player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    player.sendMessage(Language.getAndReplace("fly-enabled"));
                    sender.sendMessage(Language.getAndReplace("fly-enabled-for", args[0]));
                }
            } else sender.sendMessage(Language.getAndReplace("fly-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}