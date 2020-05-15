package net.lldv.pydow.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import net.lldv.pydow.core.commands.*;
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
        cr.unregister(this, "gamemode");
        cr.unregister(this, "tp");
        cr.unregister(this, "time");
        cr.unregister(this, "weather");
        cr.unregister(this, "tell");
        cr.register(this, "id", new IDCommand("id", this));
        cr.register(this, "gamemode", new GamemodeCommand("gamemode", this));
        cr.register(this, "teleport", new TeleportCommand("teleport", this));
        cr.register(this, "fly", new FlyCommand("fly", this));
        cr.register(this, "time", new TimeCommand("time", this));
        cr.register(this, "weather", new WeatherCommand("weather", this));
        cr.register(this, "broadcast", new BroadcastCommand("broadcast", this));
        cr.register(this, "tpa", new TpaCommand("tpa", this));
        cr.register(this, "tpaccept", new TpacceptCommand("tpaccept", this));
        cr.register(this, "tpdeny", new TpdenyCommand("tpdeny", this));
        cr.register(this, "vanish", new VanishCommand("vanish", this));
        cr.register(this, "msg", new MsgCommand("msg", this));
        cr.register(this, "reply", new ReplyCommand("reply", this));
    }


    public static Core getInstance() {
        return instance;
    }
}
