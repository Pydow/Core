package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.CoreData;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class VanishCommand extends PluginCommand<Core> {

    public VanishCommand(Core owner) {
        super(owner, Command.create("vanish", "Mache dich unsichtbar",
                new String[]{"pydow.core.command.vanish"},
                new String[]{"v"},
                new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false)}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission(getPermissions().get(0))) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    CoreData.toggleVanish(player);
                    CoreData.updatePlayer(player);
                } else if (args.length == 1) {
                    Player player1 = Server.getInstance().getPlayer(args[0]);
                    if (player1 != null) {
                        CoreData.toggleVanish(player1);
                        CoreData.updatePlayer(player1);
                        player.sendMessage(Language.getAndReplace("vanished-other", player1.getName()));
                    }
                } else if (args.length == 2) {
                    if (args[0].equals("show")) {
                        Player player1 = Server.getInstance().getPlayer(args[1]);
                        if (CoreData.vanish.contains(player1.getName())) {
                            player.showPlayer(player1);
                            player1.showPlayer(player);
                            player.sendMessage(Language.getAndReplace("player-show", player1.getName()));
                        } else player.sendMessage(Language.getAndReplace("player-not-vanished"));
                    } else if (args[0].equals("spy")) {
                        Player player1 = Server.getInstance().getPlayer(args[1]);
                        if (CoreData.vanish.contains(player1.getName())) {
                            player.showPlayer(player1);
                            player.sendMessage(Language.getAndReplace("player-spy", player1.getName()));
                        } else player.sendMessage(Language.getAndReplace("player-not-vanished"));
                    } else player.sendMessage(Language.getAndReplace("vanish-usage"));
                } else player.sendMessage(Language.getAndReplace("vanish-usage"));
            } else sender.sendMessage(Language.getAndReplace("no-permission"));
        }
        return true;
    }
}
