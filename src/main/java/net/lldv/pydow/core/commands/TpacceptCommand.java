package net.lldv.pydow.core.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.data.TpaData;
import net.lldv.pydow.core.components.language.Language;

public class TpacceptCommand extends PluginCommand<Core> implements CommandFactory {

    public TpacceptCommand(String name, Core owner) {
        super(name, owner);
        setDescription("Nimm eine Teleportationsanfrage an");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (TpaData.tpaRequest.containsKey(player.getName())) {
                TpaData tpa = TpaData.tpaRequest.get(player.getName());
                if (tpa.getExpiry() < System.currentTimeMillis()) {
                    TpaData.tpaRequest.remove(player.getName());
                    player.sendMessage(Language.getAndReplace("request-expired"));
                    return true;
                }
                if (tpa.getReceiver().isOnline() && tpa.getRequester().isOnline()) {
                    tpa.getRequester().teleport(player.getLocation());
                    tpa.getRequester().sendMessage(Language.getAndReplace("tpa-teleport-to", player.getName()));
                    player.sendMessage(Language.getAndReplace("tpa-teleport-me", tpa.getRequester().getName()));
                } else player.sendMessage(Language.getAndReplace("receiver-not-online"));
                TpaData.tpaRequest.remove(player.getName());
            } else player.sendMessage(Language.getAndReplace("no-requests"));
        }
        return true;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
