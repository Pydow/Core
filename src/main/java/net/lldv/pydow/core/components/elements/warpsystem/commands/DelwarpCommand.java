package net.lldv.pydow.core.components.elements.warpsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandData;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class DelwarpCommand extends PluginCommand<Core> {

    public DelwarpCommand(Core owner) {
        super(owner, Command.create("delwarp", "",
                new String[]{"pydow.core.warp.delwarp"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0)) && sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (CoreAPI.getWarpHandler().warpExists(args[0])) {
                    CoreAPI.getWarpHandler().deleteWarp(args[0]);
                    player.sendMessage(Language.getAndReplace("warp-deleted"));
                } else player.sendMessage(Language.getAndReplace("no-data", args[0]));
            } else player.sendMessage(Language.getAndReplace("delwarp-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
