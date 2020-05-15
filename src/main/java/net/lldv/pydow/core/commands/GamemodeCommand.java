package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;

public class GamemodeCommand extends PluginCommand<Core> implements CommandFactory {

    public GamemodeCommand(String name, Core owner) {
        super(name, owner);
        setAliases(new String[]{"gm", "gammelmode"});
        setDescription("Änder deinen Spielmodus");
        commandParameters.add(new CommandParameter[]{new CommandParameter("player", false, new String[]{"0", "1", "2", "3"})});
        commandParameters.add(new CommandParameter[]{new CommandParameter("player", false, new String[]{"0", "1", "2", "3"}), new CommandParameter("player", CommandParamType.TARGET, false)});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission("pydow.core.command.gamemode")) {
            if (args.length == 1 && sender instanceof Player) {
                Player player = (Player) sender;
                try {
                    int gameMode = Integer.parseInt(args[0]);
                    if (gameMode < 0 || gameMode > 3) {
                        player.sendMessage(Language.getAndReplace("invalid-gamemode"));
                        return true;
                    }
                    player.setGamemode(gameMode);
                    player.sendMessage(Language.getAndReplace("gamemode-set-own", gameMode));
                } catch (NumberFormatException exception) {
                    player.sendMessage(Language.getAndReplace("invalid-gamemode"));
                }
            } else if (args.length == 2) {
                Player player = Server.getInstance().getPlayer(args[1]);
                if (player == null) {
                    sender.sendMessage(Language.getAndReplace("player-not-online", args[1]));
                    return true;
                }
                try {
                    int gameMode = Integer.parseInt(args[0]);
                    if (gameMode < 0 || gameMode > 3) {
                        sender.sendMessage(Language.getAndReplace("invalid-gamemode"));
                        return true;
                    }
                    player.setGamemode(gameMode);
                    sender.sendMessage(Language.getAndReplace("gamemode-set-other", gameMode, player.getName()));
                } catch (NumberFormatException exception) {
                    player.sendMessage(Language.getAndReplace("invalid-gamemode"));
                }
            } else sender.sendMessage(Language.getAndReplace("gamemode-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
