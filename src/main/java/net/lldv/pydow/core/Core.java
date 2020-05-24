package net.lldv.pydow.core;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.commands.*;
import net.lldv.pydow.core.components.database.MongoDB;
import net.lldv.pydow.core.components.elements.punishsystem.commands.*;
import net.lldv.pydow.core.components.elements.punishsystem.listener.PunishListener;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.TimeTool;
import net.lldv.pydow.core.listener.PlayerListener;

public class Core extends PluginBase {

    private static Core instance;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        Language.init();
        registerCommands();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new PunishListener(), this);
        new CoreAPI().loadAPI();
        new TimeTool().init();
        MongoDB.connect(this);
    }

    public void registerCommands() {
        CommandRegistry cr = getServer().getCommandRegistry();
        cr.unregister(this, "gamemode");
        cr.unregister(this, "tp");
        cr.unregister(this, "time");
        cr.unregister(this, "weather");
        cr.unregister(this, "tell");
        cr.unregister(this, "ban");
        cr.unregister(this, "banip");
        cr.unregister(this, "banlist");
        cr.unregister(this, "kick");
        cr.unregister(this, "pardon");
        cr.unregister(this, "say");
        cr.register(this, new IDCommand(this));
        cr.register(this, new GamemodeCommand(this));
        cr.register(this, new TeleportCommand(this));
        cr.register(this, new FlyCommand(this));
        cr.register(this, new TimeCommand(this));
        cr.register(this, new WeatherCommand(this));
        cr.register(this, new BroadcastCommand(this));
        cr.register(this, new TpaCommand(this));
        cr.register(this, new TpacceptCommand(this));
        cr.register(this, new TpdenyCommand(this));
        cr.register(this, new VanishCommand(this));
        cr.register(this, new MsgCommand(this));
        cr.register(this, new ReplyCommand(this));
        cr.register(this, new TeamchatCommand(this));

        cr.register(this, new BanCommand(this));
        cr.register(this, new MuteCommand(this));
        cr.register(this, new UnbanCommand(this));
        cr.register(this, new UnmuteCommand(this));
        cr.register(this, new GetbanCommand(this));
        cr.register(this, new GetmuteCommand(this));
    }


    public static Core getInstance() {
        return instance;
    }
}
