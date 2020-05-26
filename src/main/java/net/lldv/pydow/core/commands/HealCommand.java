package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.item.Item;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class HealCommand extends PluginCommand<Core> {

    public HealCommand(Core owner) {
        super(owner, Command.create("heal", "Mache dich im Handumdrehen wieder gesund.",
                new String[]{"pydow.core.command.heal"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0))) {
            if (args.length == 0 && sender instanceof Player) {
                Player player = (Player) sender;
                player.setHealth(20);
                player.getFoodData().setLevel(20);
                sender.sendMessage(Language.getAndReplace("healed"));
            } else if (args.length == 1) {
                Player player = Server.getInstance().getPlayer(args[0]);
                if (player != null) {
                    player.setHealth(20);
                    player.getFoodData().setLevel(20);
                    sender.sendMessage(Language.getAndReplace("player-healed", player.getDisplayName()));
                } else sender.sendMessage(Language.getAndReplace("player-not-online", args[0]));
            } else sender.sendMessage(Language.getAndReplace("heal-usage"));
        }
        return true;
    }
}
