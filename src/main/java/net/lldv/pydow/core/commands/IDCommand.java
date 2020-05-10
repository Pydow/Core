package net.lldv.pydow.core.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemIds;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;

public class IDCommand extends PluginCommand<Core> implements CommandFactory {

    public IDCommand(String name, Core owner) {
        super(name, owner);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;

            Item item = player.getInventory().getItemInHand();

            player.sendMessage(Language.getAndReplace("id", item.getId().getName()));
        }
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
