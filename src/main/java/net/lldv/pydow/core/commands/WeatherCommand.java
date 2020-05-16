package net.lldv.pydow.core.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.player.Player;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.Command;

public class WeatherCommand extends PluginCommand<Core> {

    public WeatherCommand(Core owner) {
        super(owner, Command.create("weather", "Änder das Wetter in deiner Welt",
                new String[]{"pydow.core.command.weather"},
                new String[]{},
                new CommandParameter[]{new CommandParameter("weather", false, new String[]{"clear", "rain", "thunder"})}));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("pydow.core.command.weather")) {
                if (args.length == 1) {
                    Player player = (Player) sender;
                    if (args[0].equals("clear")) {
                        player.getLevel().setRaining(false);
                        player.getLevel().setThundering(false);
                        player.sendMessage(Language.getAndReplace("weather-set"));
                    } else if (args[0].equals("rain")) {
                        player.getLevel().setRaining(true);
                        player.getLevel().setThundering(false);
                        player.sendMessage(Language.getAndReplace("weather-set"));
                    } else if (args[0].equals("thunder")) {
                        player.getLevel().setRaining(true);
                        player.getLevel().setThundering(true);
                        player.sendMessage(Language.getAndReplace("weather-set"));
                    } else sender.sendMessage(Language.getAndReplace("weather-usage"));
                } else sender.sendMessage(Language.getAndReplace("weather-usage"));
            } else sender.sendMessage(Language.getAndReplace("no-permission"));
        }
        return true;
    }
}
