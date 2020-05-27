package net.lldv.pydow.core.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class SizeCommand extends PluginCommand<Core> {

    public SizeCommand(Core owner) {
        super(owner, Command.create("size", "Mache dich groß oder klein",
                new String[]{"pydow.core.commands.size"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getPermissions().get(0)) && sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                try {
                    double size = Double.parseDouble(args[0]);
                    if (size > 0 || size < 5) {
                        player.setScale((float) size);
                        player.sendMessage(Language.getAndReplace("size-changed", size));
                    } else player.sendMessage(Language.getAndReplace("invalid-size"));
                } catch (Exception e) {
                    player.sendMessage(Language.getAndReplace("invalid-size"));
                }
            } else sender.sendMessage(Language.getAndReplace("size-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
