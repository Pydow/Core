package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;

public class TeleportCommand extends PluginCommand<Core> implements CommandFactory {

    public TeleportCommand(String name, Core owner) {
        super(name, owner);
        setAliases(new String[]{"tp"});
        setDescription("Teleportiere dich zu einem Spieler oder einer Position");
        commandParameters.add(new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false)});
        commandParameters.add(new CommandParameter[]{new CommandParameter("player", CommandParamType.TARGET, false), new CommandParameter("target", CommandParamType.TARGET, false)});
        commandParameters.add(new CommandParameter[]{new CommandParameter("x", CommandParamType.INT, false), new CommandParameter("y", CommandParamType.INT, false), new CommandParameter("z", CommandParamType.INT, false)});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission("pydow.core.command.teleport")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    Player target = Server.getInstance().getPlayer(args[0]);
                    if (target != null) {
                        player.teleport(target.getLocation());
                        player.sendMessage(Language.getAndReplace("teleport-to-1", target.getName()));
                    } else player.sendMessage(Language.getAndReplace("player-not-online", args[0]));
                } else if (args.length == 2) {
                    Player target = Server.getInstance().getPlayer(args[0]);
                    Player targetTo = Server.getInstance().getPlayer(args[1]);
                    if (target != null) {
                        if (targetTo != null) {
                            target.teleport(targetTo.getLocation());
                            player.sendMessage(Language.getAndReplace("teleport-to-2", target.getName(), targetTo.getName()));
                        } player.sendMessage(Language.getAndReplace("player-not-online", args[1]));
                    } else player.sendMessage(Language.getAndReplace("player-not-online", args[0]));
                } else if (args.length == 3) {
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);
                        player.teleport(Location.from(x, y, z, player.getLevel()));
                        player.sendMessage(Language.getAndReplace("teleported"));
                    } catch (NumberFormatException exception) {
                        player.sendMessage("invalid-coordinates");
                    }
                } else player.sendMessage(Language.getAndReplace("teleport-usage"));
            }
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
