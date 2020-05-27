package net.lldv.pydow.core.components.database;

import cn.nukkit.utils.Config;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.lldv.pydow.core.Core;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;

public class MongoDB {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> banCollection, muteCollection, banhistoryCollection, mutehistoryCollection, homeCollection;

    public static void connect(Core server) {
        CompletableFuture.runAsync(() -> {
            Config config = Core.getInstance().getConfig();
            MongoClientURI uri = new MongoClientURI(config.getString("MongoDB.Uri"));
            mongoClient = new MongoClient(uri);
            mongoDatabase = mongoClient.getDatabase(config.getString("MongoDB.Database"));
            banCollection = mongoDatabase.getCollection("bans");
            muteCollection = mongoDatabase.getCollection("mutes");
            banhistoryCollection = mongoDatabase.getCollection("banhistory");
            mutehistoryCollection = mongoDatabase.getCollection("mutehistory");
            homeCollection = mongoDatabase.getCollection("homes");
            server.getLogger().info("[MongoClient] Connection opened.");
        });
    }

    public static MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static MongoCollection<Document> getBanCollection() {
        return banCollection;
    }

    public static MongoCollection<Document> getMuteCollection() {
        return muteCollection;
    }

    public static MongoCollection<Document> getBanhistoryCollection() {
        return banhistoryCollection;
    }

    public static MongoCollection<Document> getMutehistoryCollection() {
        return mutehistoryCollection;
    }

    public static MongoCollection<Document> getHomeCollection() {
        return homeCollection;
    }
}
