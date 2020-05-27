package net.lldv.pydow.core.components.elements.homesystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.tools.Command;

public class HomesCommand extends PluginCommand<Core> {

    public HomesCommand(Core owner) {
        super(owner, Command.create("homes", "Teleportiere dich zu deinen Homes"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CoreAPI.getHomeHandler().openHomesMenu(player);
        }
        return true;
    }
}
