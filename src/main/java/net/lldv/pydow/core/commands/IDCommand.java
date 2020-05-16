package net.lldv.pydow.core.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.item.Item;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class IDCommand extends PluginCommand<Core> {

    public IDCommand(Core owner) {
        super(owner, Command.create("id", "Zeige die ID des Items in deiner Hand an."));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            Item item = player.getInventory().getItemInHand();
            if (item.getId().getName().equalsIgnoreCase("air")) {
                player.sendMessage(Language.get("id-secret"));
                return false;
            }
            player.sendMessage(Language.getAndReplace("id", item.getId().getNamespace(), item.getId().getName()));
        }
        return true;
    }
}
