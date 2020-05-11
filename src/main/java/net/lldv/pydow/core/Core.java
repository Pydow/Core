package net.lldv.pydow.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import net.lldv.pydow.core.commands.IDCommand;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.listener.PlayerListener;

public class Core extends PluginBase {

    public static Core instance;

    @Override
    public void onLoad() {
        instance = this;
        Language.init();
        registerCommands();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerCommands() {
        CommandRegistry cr = getServer().getCommandRegistry();

        cr.register(this, "id", new IDCommand("id", this));
    }


    public static Core getInstance() {
        return instance;
    }
}
