package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Door;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
@JsonInclude(NON_NULL)
public class CalendarForParticipant {
    private String id;
    private String companyName;
    private List<Integer> doorSequence;
    private List<DoorForParticipant> doors;

    public static CalendarForParticipant from(Calendar c, String participantId) {
        CalendarForParticipant cal = LOOSE_MAPPER.map(c, CalendarForParticipant.class);

        for (Door door : c.getDoors()) {
            if (door.isOpened(participantId)) {
                cal.updateDoor(DoorForParticipant.openRepresentationOf(door, participantId, c.getDoorsAlwaysAvailable()));
            }
            else {
                cal.updateDoor(DoorForParticipant.closedRepresentationOf(door, participantId, c.getDoorsAlwaysAvailable()));
            }
        }

        return cal;
    }

    public void updateDoor(DoorForParticipant door) {
        doors.set(door.getNumber()-1, door);
    }
}
