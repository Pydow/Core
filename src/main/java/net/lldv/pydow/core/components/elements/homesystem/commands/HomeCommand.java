package net.lldv.pydow.core.components.elements.homesystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.tools.Command;

public class HomeCommand extends PluginCommand<Core> {

    public HomeCommand(Core owner) {
        super(owner, Command.create("home", "Verwalte deine Homes",
                new String[]{},
                new String[]{"sethome", "delhome"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CoreAPI.getHomeHandler().openHomeMenu(player);
        }
        return true;
    }
}
