package no.juleluka.api.models;

import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

@Entity(value = "Calendar", noClassnameStored = true)
@Data
public class Calendar {
    @Id private ObjectId id;
    private String companyName; // TODO: Rename to calendarName
    private String adminPassword;
    private String logoUrl;
    private String contactEmail;
    private Integer winnersPerDay;
    private List<Integer> doorSequence;
    private List<Door> doors = new ArrayList();
    private Set<Participant> participants = new HashSet<>();

    public Calendar init() {
        this.setId(new ObjectId());
        for (int i=1; i<=24; i++) {
            this.getDoors().add(new Door(i));
        }
        this.doorSequence = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        Collections.shuffle(this.doorSequence);
        return this;
    }

    public Door getDoor(int doorNumber) {
        return getDoors().get(doorNumber-1);
    }

    public void setDoor(int doorNumber, Door door) {
        getDoors().set(doorNumber-1, door);
    }
}
