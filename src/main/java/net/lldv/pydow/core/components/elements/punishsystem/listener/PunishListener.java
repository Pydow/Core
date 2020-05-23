package net.lldv.pydow.core.components.elements.punishsystem.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.elements.punishsystem.PunishHandler;
import net.lldv.pydow.core.components.elements.punishsystem.data.Punishment;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.TimeTool;

import java.util.concurrent.CompletableFuture;

public class PunishListener implements Listener {

    @EventHandler
    public void on(PlayerPreLoginEvent event) {
        PunishHandler punishment = CoreAPI.getPunishHandler();
        CompletableFuture.runAsync(() -> {
            try {
                if (punishment.isBanned(event.getLoginData().getName())) {
                    Punishment ban = punishment.getPunishment(event.getLoginData().getName(), "ban");
                    if (ban.getTime() < System.currentTimeMillis()) {
                        punishment.unBan(event.getLoginData().getName());
                        return;
                    }
                    event.setKickMessage(Language.getAndReplaceNoPrefix("ban-screen", ban.getReason(), ban.getId(), TimeTool.durationInString(ban.getTime())));
                    event.getLoginData().getSession().disconnect(event.getKickMessage());
                } else if (punishment.isMuted(event.getLoginData().getName())) {
                    Punishment mute = punishment.getPunishment(event.getLoginData().getName(), "mute");
                    if (mute.getTime() < System.currentTimeMillis()) {
                        punishment.unMute(event.getLoginData().getName());
                        return;
                    }
                    punishment.cachedMute.put(event.getLoginData().getName(), mute);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @EventHandler
    public void on(PlayerChatEvent event) {
        PunishHandler punishment = CoreAPI.getPunishHandler();
        if (punishment.cachedMute.containsKey(event.getPlayer().getName())) {
            Punishment mute = punishment.cachedMute.get(event.getPlayer().getName());
            if (mute.getTime() < System.currentTimeMillis()) {
                punishment.unMute(event.getPlayer().getName());
                return;
            }
            event.getPlayer().sendMessage(Language.getAndReplace("chat-muted-info"));
            event.getPlayer().sendMessage(Language.getAndReplace("chat-muted-reason", mute.getReason()));
            event.getPlayer().sendMessage(Language.getAndReplace("chat-muted-time", TimeTool.durationInString(mute.getTime())));
            event.setCancelled(true);
        }
    }

}
