package net.lldv.pydow.core.commands;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.item.Item;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class SignCommand extends PluginCommand<Core> {

    public SignCommand(Core owner) {
        super(owner, Command.create("sign", "Signiere ein Item und mache es einzigartig",
                new String[]{"pydow.core.command.sign"}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player && sender.hasPermission(getPermissions().get(0))) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                String text = String.join(" ", args);
                if (player.getInventory().getItemInHand().getId().getName().equals("air")) {
                    player.sendMessage(Language.getAndReplace("sign-invalid-item"));
                    return true;
                }
                player.getInventory().setItemInHand(player.getInventory().getItemInHand().setLore("§r§7" + text.replace("&", "§"), "§r§8--------------------", "§r§7Signiert von §e" + player.getName() + " §7am §e" + CoreAPI.getDate() + "§7."));
                player.sendMessage(Language.getAndReplace("item-signed"));
            } else player.sendMessage(Language.getAndReplace("sign-usage"));
        } else sender.sendMessage(Language.getAndReplace("no-permission"));
        return true;
    }
}
