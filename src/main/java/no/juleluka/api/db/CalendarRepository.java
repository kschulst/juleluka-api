package no.juleluka.api.db;

import no.juleluka.api.models.Calendar;
import no.juleluka.api.support.guice.MorphiaInstance;
import no.juleluka.api.support.guice.MorphiaRepository;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

@MorphiaRepository
public class CalendarRepository extends BasicDAO<Calendar, ObjectId> {

    public CalendarRepository() {
        super(MorphiaInstance.get().getMorphiaDatastore());
    }

}
