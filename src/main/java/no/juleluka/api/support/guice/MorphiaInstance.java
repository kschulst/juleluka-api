package no.juleluka.api.support.guice;

import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import static java.util.Objects.requireNonNull;

public class MorphiaInstance {
    private final MongoConfiguration mongoConfiguration;
    private final MongoClient mongoClient;
    private final DB db;
    private final Morphia morphia;
    private final Datastore morphiaDatastore;

    private static MorphiaInstance instance;

    private MorphiaInstance(MongoConfiguration mongoConfiguration, MongoClient mongoClient, DB db) {
        this.mongoConfiguration = requireNonNull(mongoConfiguration);
        this.mongoClient = requireNonNull(mongoClient);
        this.db = requireNonNull(db);
        this.morphia = new Morphia();
        this.morphiaDatastore = this.morphia.createDatastore(mongoClient, mongoConfiguration.getDatabase());
        morphia.mapPackage("no.juleluka.api"); // TODO: Move this elsewhere
    }

    public static MorphiaInstance init(MongoConfiguration mongoConfiguration, MongoClient mongoClient, DB db) {
        instance = new MorphiaInstance(mongoConfiguration, mongoClient, db);
        return instance;
    }

    public static MorphiaInstance get() {
        if (instance == null) {
            throw new IllegalStateException("MorphiaInstance has not yet been initialized. Make sure MorphiaMongoBundle.init has been executed.");
        }

        return instance;
    }

    public MongoConfiguration getMongoConfiguration() {
        return mongoConfiguration;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public DB getDb() {
        return db;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public Datastore getMorphiaDatastore() {
        return morphiaDatastore;
    }
}
