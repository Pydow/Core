package net.lldv.pydow.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import net.lldv.pydow.core.commands.IDCommand;

public class Core extends PluginBase {

    public static Core instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        registerCommands();
    }

    public void registerCommands() {
        CommandRegistry cr = getServer().getCommandRegistry();

        cr.register(this, "id", new IDCommand("id", this));
    }


    public static Core getInstance() {
        return instance;
    }
}
