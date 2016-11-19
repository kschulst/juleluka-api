package no.juleluka.api.db;

import no.juleluka.api.models.AuthToken;
import no.juleluka.api.support.guice.MorphiaInstance;
import no.juleluka.api.support.guice.MorphiaRepository;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

@MorphiaRepository
public class AuthTokenRepository extends BasicDAO<AuthToken, ObjectId> {

    public AuthTokenRepository() {
        super(MorphiaInstance.get().getMorphiaDatastore());
    }

}
