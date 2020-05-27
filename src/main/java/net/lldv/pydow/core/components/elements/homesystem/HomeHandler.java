package net.lldv.pydow.core.components.elements.homesystem;

import cn.nukkit.Server;
import cn.nukkit.form.CustomForm;
import cn.nukkit.form.Form;
import cn.nukkit.form.SimpleForm;
import cn.nukkit.form.util.ImageType;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.Sound;
import cn.nukkit.player.Player;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import net.lldv.pydow.core.Core;
import net.lldv.pydow.core.api.CoreAPI;
import net.lldv.pydow.core.components.data.Home;
import net.lldv.pydow.core.components.database.MongoDB;
import net.lldv.pydow.core.components.language.Language;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HomeHandler {

    public HashMap<String, Home> cachedHomes = new HashMap<>();
    public HashMap<String, List<String>> cachedPlayerHomes = new HashMap<>();

    public void createHome(Player player, String name) {
        CompletableFuture.runAsync(() -> {
            Location location = player.getLocation();
            Document document = new Document("player", player.getName())
                    .append("name", name)
                    .append("x", (int) location.getX())
                    .append("y", (int) location.getY())
                    .append("z", (int) location.getZ())
                    .append("yaw", (int) location.getYaw())
                    .append("pitch", (int) location.getPitch())
                    .append("level", location.getLevel().getName());
            MongoDB.getHomeCollection().insertOne(document);
            cachedHomes.put(player.getName() + ":" + name, new Home(location, name, player.getName()));
            if (cachedPlayerHomes.get(player.getName()) != null) {
                List<String> list = cachedPlayerHomes.get(player.getName());
                list.add(name);
                cachedPlayerHomes.put(player.getName(), list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(name);
                cachedPlayerHomes.put(player.getName(), list);
            }
        });
    }

    public void deleteHome(String player, String name) {
        CompletableFuture.runAsync(() -> {
            MongoCollection<Document> collection = MongoDB.getHomeCollection();
            collection.deleteOne(new Document("player", player).append("name", name));
        });
        cachedHomes.remove(player + ":" + name);
        if (cachedPlayerHomes.get(player) != null) {
            List<String> list = cachedPlayerHomes.get(player);
            list.remove(name);
            cachedPlayerHomes.put(player, list);
        }
    }

    public boolean homeExists(String player, String name) {
        return cachedHomes.get(player + ":" + name) != null;
    }

    public void openHomeMenu(Player player) {
        SimpleForm.SimpleFormBuilder homeForm = Form.simple()
                .title(Language.getAndReplaceNP("homeui-title"))
                .content(Language.getAndReplaceNP("homeui-content"))
                .button(Language.getAndReplaceNP("home-homes-button"), ImageType.URL, "https://image.flaticon.com/icons/png/512/149/149031.png")
                .button(Language.getAndReplaceNP("home-create-button"), ImageType.URL, "https://image.flaticon.com/icons/png/512/148/148764.png")
                .button(Language.getAndReplaceNP("home-delete-button"), ImageType.URL, "https://image.flaticon.com/icons/png/512/148/148766.png")
                .onSubmit((player1, response) -> {
                    String text = response.getButton().getButtonText();
                    if (text.equals(Language.getAndReplaceNP("home-homes-button"))) {
                        SimpleForm.SimpleFormBuilder homesForm = Form.simple()
                                .title(Language.getAndReplaceNP("homeui-homes-title"))
                                .content(Language.getAndReplaceNP("homeui-homes-content"))
                                .onSubmit(((player2, response1) -> {
                                    String text1 = response1.getButton().getButtonText();
                                    Home home = cachedHomes.get(player.getName() + ":" + text1);
                                    player.teleport(home.getLocation());
                                    player.sendMessage(Language.getAndReplace("home-teleported", home.getName()));
                                    CoreAPI.playSound(player, Sound.NOTE_PLING);
                                }));
                        cachedPlayerHomes.get(player.getName()).forEach(homesForm::button);
                        SimpleForm finalHomesForm = homesForm.build();
                        player.showFormWindow(finalHomesForm);
                    } else if (text.equals(Language.getAndReplaceNP("home-create-button"))) {
                        CustomForm createForm = Form.custom()
                                .title(Language.getAndReplaceNP("homeui-create-title"))
                                .input(Language.getAndReplaceNP("homeui-create-content"), "Name")
                                .onSubmit((player2, response1) -> {
                                    String input = response1.getInput(0);
                                    if (!homeExists(player.getName(), input)) {
                                        createHome(player, input);
                                        player.sendMessage(Language.getAndReplace("home-created", input));
                                        CoreAPI.playSound(player, Sound.RANDOM_LEVELUP);
                                    } else player.sendMessage(Language.getAndReplace("already-set", input));
                                })
                                .build();
                        player.showFormWindow(createForm);
                    } else if (text.equals(Language.getAndReplaceNP("home-delete-button"))) {
                        SimpleForm.SimpleFormBuilder homesForm = Form.simple()
                                .title(Language.getAndReplaceNP("homeui-delete-title"))
                                .content(Language.getAndReplaceNP("homeui-delete-content"))
                                .onSubmit(((player2, response1) -> {
                                    String text1 = response1.getButton().getButtonText();
                                    deleteHome(player.getName(), text1);
                                    player.sendMessage(Language.getAndReplace("home-deleted", text1));
                                    CoreAPI.playSound(player, Sound.NOTE_BASS);
                                }));
                        cachedPlayerHomes.get(player.getName()).forEach(homesForm::button);
                        SimpleForm finalHomesForm = homesForm.build();
                        player.showFormWindow(finalHomesForm);
                    }
                });
        SimpleForm finalForm = homeForm.build();
        player.showFormWindow(finalForm);
        Server.getInstance().getScheduler().scheduleDelayedTask(Core.getInstance(), () -> player.setExperience(player.getExperience()), 20);
    }

    public void openHomesMenu(Player player) {
        SimpleForm.SimpleFormBuilder homesForm = Form.simple()
                .title(Language.getAndReplaceNP("homeui-homes-title"))
                .content(Language.getAndReplaceNP("homeui-homes-content"))
                .onSubmit(((player2, response1) -> {
                    String text1 = response1.getButton().getButtonText();
                    Home home = cachedHomes.get(player.getName() + ":" + text1);
                    player.teleport(home.getLocation());
                    player.sendMessage(Language.getAndReplace("home-teleported", home.getName()));
                    CoreAPI.playSound(player, Sound.NOTE_PLING);
                }));
        cachedPlayerHomes.get(player.getName()).forEach(homesForm::button);
        SimpleForm finalHomesForm = homesForm.build();
        player.showFormWindow(finalHomesForm);
    }

    public void cachePlayerHomes(String player) {
        CompletableFuture.runAsync(() -> MongoDB.getHomeCollection().find(new Document("player", player)).forEach((Block<? super Document>) doc -> {
            Level level = Server.getInstance().getLevelByName(doc.getString("level"));
            if (level == null) return;
            Location location = Location.from((float) doc.getInteger("x"), (float) doc.getInteger("y"), (float) doc.getInteger("z"), (float) doc.getInteger("yaw"), (float) doc.getInteger("pitch"), level);
            cachedHomes.put(player + ":" + doc.getString("name"), new Home(location, doc.getString("name"), player));
            if (cachedPlayerHomes.get(player) != null) {
                List<String> list = cachedPlayerHomes.get(player);
                list.add(doc.getString("name"));
                cachedPlayerHomes.put(player, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(doc.getString("name"));
                cachedPlayerHomes.put(player, list);
            }
        }));
    }

}
