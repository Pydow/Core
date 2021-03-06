package net.lldv.pydow.core.components.elements.punishsystem;

import cn.nukkit.Server;
import cn.nukkit.player.Player;
import com.mongodb.client.MongoCollection;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.database.MongoDB;
import net.lldv.pydow.core.components.elements.punishsystem.data.Punishment;
import net.lldv.pydow.core.components.event.PlayerBanEvent;
import net.lldv.pydow.core.components.event.PlayerMuteEvent;
import net.lldv.pydow.core.components.language.Language;
import net.lldv.pydow.core.components.tools.TimeTool;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PunishHandler {

    public HashMap<String, Punishment> cachedMute = new HashMap<>();

    public void createBan(String player, String creator, String reason, String timeString, double time) {
        String id = CoreAPI.getRandomIDCode();
        String date = CoreAPI.getDate();
        long end = -1;
        if ("weeks".equals(timeString)) end = TimeTool.weeksInMillis(time) + System.currentTimeMillis();
        else if ("days".equals(timeString)) end = TimeTool.daysInMillis(time) + System.currentTimeMillis();
        else if ("hours".equals(timeString)) end = TimeTool.hoursInMillis(time) + System.currentTimeMillis();
        else if ("minutes".equals(timeString)) end = TimeTool.minutesInMillis(time) + System.currentTimeMillis();
        Document document = new Document("player", player)
                .append("creator", creator)
                .append("reason", reason)
                .append("id", id)
                .append("date", date)
                .append("time", end);
        MongoDB.getBanCollection().insertOne(document);
        Server.getInstance().getPluginManager().callEvent(new PlayerBanEvent(null, player, creator, reason, id, date, timeString, time));
        Player online = Server.getInstance().getPlayer(player);
        if (online != null) online.kick(Language.getAndReplaceNP("ban-screen", reason, id, TimeTool.durationInString(end)), false);
        createBanHistory(new Punishment(player, id, reason, creator, date, end));
    }

    public void createMute(String player, String creator, String reason, String timeString, double time) {
        String id = CoreAPI.getRandomIDCode();
        String date = CoreAPI.getDate();
        long end = -1;
        if ("weeks".equals(timeString)) end = TimeTool.weeksInMillis(time) + System.currentTimeMillis();
        else if ("days".equals(timeString)) end = TimeTool.daysInMillis(time) + System.currentTimeMillis();
        else if ("hours".equals(timeString)) end = TimeTool.hoursInMillis(time) + System.currentTimeMillis();
        else if ("minutes".equals(timeString)) end = TimeTool.minutesInMillis(time) + System.currentTimeMillis();
        Document document = new Document("player", player)
                .append("creator", creator)
                .append("reason", reason)
                .append("id", id)
                .append("date", date)
                .append("time", end);
        MongoDB.getMuteCollection().insertOne(document);
        Server.getInstance().getPluginManager().callEvent(new PlayerMuteEvent(null, player, creator, reason, id, date, timeString, time));
        Player online = Server.getInstance().getPlayer(player);
        if (online != null) cachedMute.put(player, new Punishment(player, id, reason, creator, date, end));
        createMuteHistory(new Punishment(player, id, reason, creator, date, end));
    }

    public boolean isBanned(String player) {
        Document document = MongoDB.getBanCollection().find(new Document("player", player)).first();
        return document != null;
    }

    public boolean banIdExists(String id) {
        Document document = MongoDB.getBanCollection().find(new Document("id", id)).first();
        return document != null;
    }

    public boolean isMuted(String player) {
        Document document = MongoDB.getMuteCollection().find(new Document("player", player)).first();
        return document != null;
    }

    public boolean muteIdExists(String id) {
        Document document = MongoDB.getMuteCollection().find(new Document("id", id)).first();
        return document != null;
    }

    public void createBanHistory(Punishment punishment) {
        Document document = new Document("player", punishment.getPlayer())
                .append("creator", punishment.getCreator())
                .append("reason", punishment.getReason())
                .append("id", punishment.getId())
                .append("date", punishment.getDate());
        MongoDB.getBanhistoryCollection().insertOne(document);
    }

    public void createMuteHistory(Punishment punishment) {
        Document document = new Document("player", punishment.getPlayer())
                .append("creator", punishment.getCreator())
                .append("reason", punishment.getReason())
                .append("id", punishment.getId())
                .append("date", punishment.getDate());
        MongoDB.getMutehistoryCollection().insertOne(document);
    }

    public void unBan(String player) {
        CompletableFuture.runAsync(() -> {
            MongoCollection<Document> collection = MongoDB.getBanCollection();
            collection.deleteOne(new Document("player", player));
        });
    }

    public void unMute(String player) {
        CompletableFuture.runAsync(() -> {
            MongoCollection<Document> collection = MongoDB.getMuteCollection();
            collection.deleteOne(new Document("player", player));
            Player online = Server.getInstance().getPlayer(player);
            if (online != null) cachedMute.remove(player);
        });
    }

    public List<Punishment> getHistory(String player, String type) {
        List<Punishment> list = new ArrayList<>();
        if (type.equals("ban")) {
            for (Document d : MongoDB.getBanhistoryCollection().find(new Document("player", player))) {
                Punishment punishment = new Punishment(d.getString("player"), d.getString("id"), d.getString("reason"), d.getString("creator"), d.getString("date"), 0);
                list.add(punishment);
            }
        } else if (type.equals("mute")) {
            for (Document d : MongoDB.getMutehistoryCollection().find(new Document("player", player))) {
                Punishment punishment = new Punishment(d.getString("player"), d.getString("id"), d.getString("reason"), d.getString("creator"), d.getString("date"), 0);
                list.add(punishment);
            }
        }
        return list;
    }

    public Punishment getPunishment(String player, String type) {
        if (type.equals("ban")) {
            Document d = MongoDB.getBanCollection().find(new Document("player", player)).first();
            assert d != null;
            return new Punishment(player, d.getString("id"), d.getString("reason"), d.getString("creator"), d.getString("date"), d.getLong("time"));
        } else if (type.equals("mute")) {
            Document d = MongoDB.getMuteCollection().find(new Document("player", player)).first();
            assert d != null;
            return new Punishment(player, d.getString("id"), d.getString("reason"), d.getString("creator"), d.getString("date"), d.getLong("time"));
        }
        return null;
    }

    public Punishment getPunishmentById(String id, String type) {
        if (type.equals("ban")) {
            Document d = MongoDB.getBanCollection().find(new Document("id", id)).first();
            assert d != null;
            return new Punishment(d.getString("player"), id, d.getString("reason"), d.getString("creator"), d.getString("date"), d.getLong("time"));
        } else if (type.equals("mute")) {
            Document d = MongoDB.getMuteCollection().find(new Document("id", id)).first();
            assert d != null;
            return new Punishment(d.getString("player"), id, d.getString("reason"), d.getString("creator"), d.getString("date"), d.getLong("time"));
        }
        return null;
    }

}
