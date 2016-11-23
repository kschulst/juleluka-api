package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Door;

import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.looseMapper;

@Data
@JsonInclude(NON_NULL)
public class CalendarForParticipant {
    private String id;
    private String companyName;
    private List<Integer> doorSequence;
    private List<DoorForParticipant> doors;

    public static CalendarForParticipant from(Calendar c, String participantId) {
        CalendarForParticipant cal = looseMapper().map(c, CalendarForParticipant.class);
        boolean isDoorsAlwaysAvailable  = Optional.ofNullable(c.getDoorsAlwaysAvailable()).orElse(false);


        for (Door door : c.getDoors()) {
            if (door.isOpened(participantId)) {
                cal.updateDoor(DoorForParticipant.openRepresentationOf(door, participantId, isDoorsAlwaysAvailable));
            }
            else {
                cal.updateDoor(DoorForParticipant.closedRepresentationOf(door, participantId, isDoorsAlwaysAvailable));
            }
        }

        return cal;
    }

    public void updateDoor(DoorForParticipant door) {
        doors.set(door.getNumber()-1, door);
    }
}
