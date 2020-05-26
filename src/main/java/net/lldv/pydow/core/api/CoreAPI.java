package net.lldv.pydow.core.api;

import cn.nukkit.level.Sound;
import cn.nukkit.player.Player;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import net.lldv.pydow.core.components.elements.punishsystem.PunishHandler;
import net.lldv.pydow.core.components.elements.holograms.HologramHandler;
import net.lldv.pydow.core.components.elements.homesystem.HomeHandler;
import net.lldv.pydow.core.components.elements.warpsystem.WarpHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CoreAPI {

    private static PunishHandler punishHandler;
    private static HologramHandler hologramHandler;
    private static HomeHandler homeHandler;
    private static WarpHandler warpHandler;

    public void loadAPI() {
        punishHandler = new PunishHandler();
        hologramHandler = new HologramHandler();
        homeHandler = new HomeHandler();
        warpHandler = new WarpHandler();
    }

    public static PunishHandler getPunishHandler() {
        return punishHandler;
    }

    public static HologramHandler getHologramHandler() {
        return hologramHandler;
    }

    public static HomeHandler getHomeHandler() {
        return homeHandler;
    }

    public static WarpHandler getWarpHandler() {
        return warpHandler;
    }

    public static String getRandomIDCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        Random rnd = new Random();
        while (stringBuilder.length() < 6) {
            int index = (int) (rnd.nextFloat() * chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }

    public static String getDate() {
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        return dateFormat.format(now);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.setSound(sound.getSound());
        packet.setPosition(Vector3f.from(new Double(player.getLocation().getX()).intValue(), new Double(player.getLocation().getY()).intValue(), new Double(player.getLocation().getZ()).intValue()));
        packet.setVolume(volume);
        packet.setPitch(pitch);
        player.sendPacket(packet);
    }
}
