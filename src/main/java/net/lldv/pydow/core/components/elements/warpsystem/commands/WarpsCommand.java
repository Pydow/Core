package net.lldv.pydow.core.components.elements.warpsystem.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.form.Form;
import cn.nukkit.form.SimpleForm;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.data.Warp;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class WarpsCommand extends PluginCommand<Core> {

    public WarpsCommand(Core owner) {
        super(owner, Command.create("warps", "Erhalte eine Liste aller Warps"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            SimpleForm.SimpleFormBuilder form = Form.simple()
                    .title(Language.getAndReplaceNoPrefix("warps-title"))
                    .content(Language.getAndReplaceNoPrefix("warps-content"))
                    .onSubmit((formPlayer, response) -> {
                        String text = response.getButton().getButtonText();
                        Warp warp = CoreAPI.getWarpHandler().cachedWarps.get(text);
                        player.teleport(Location.from((float) warp.getX(), (float) warp.getY(), (float) warp.getZ(), (float) warp.getYaw(), (float) warp.getPitch(), warp.getLevel()));
                        player.sendMessage(Language.getAndReplace("warp-teleported", warp.getName()));
                    });
            CoreAPI.getWarpHandler().warpNames.forEach(form::button);
            SimpleForm finalForm = form.build();
            player.showFormWindow(finalForm);
        }
        return true;
    }
}
