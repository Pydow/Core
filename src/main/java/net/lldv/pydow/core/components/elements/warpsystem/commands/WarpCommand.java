package net.lldv.pydow.core.components.elements.warpsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.data.Warp;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class WarpCommand extends PluginCommand<Core> {

    public WarpCommand(Core owner) {
        super(owner, Command.create("warp", "Teleportiere dich zu einem Warp"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (CoreAPI.getWarpHandler().warpExists(args[0])) {
                    Warp warp = CoreAPI.getWarpHandler().cachedWarps.get(args[0]);
                    player.teleport(Location.from((float) warp.getX(), (float) warp.getY(), (float) warp.getZ(), (float) warp.getYaw(), (float) warp.getPitch(), warp.getLevel()));
                    player.sendMessage(Language.getAndReplace("warp-teleported", warp.getName()));
                } else player.sendMessage(Language.getAndReplace("no-data", args[0]));
            } else player.sendMessage(Language.getAndReplace("warp-usage"));
        }
        return true;
    }
}
