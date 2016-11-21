package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Door;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;
import static no.juleluka.api.models.mappers.ModelMappers.STANDARD_MAPPER;

@Data
@JsonInclude(NON_NULL)
public class DoorForParticipant {
    private int number;
    private Boolean open;
    private Boolean win;
    private String prize;
    private String quote;
    private String instructions;

    public static DoorForParticipant fullRepresentationOf(Door door, String participantId) {
        DoorForParticipant doorForParticipant = LOOSE_MAPPER.map(door, DoorForParticipant.class);
        doorForParticipant.setOpen(door.getOpenedBy().contains(participantId));
        if (door.getWinners().size() > 0) { // Only set win if winners have been announced
            doorForParticipant.setWin(door.getWinners().contains(participantId));
        }
        return doorForParticipant;
    }

    public static DoorForParticipant minimalRepresentationOf(Door door, String participantId) {
        DoorForParticipant doorForParticipant = fullRepresentationOf(door, participantId);
        doorForParticipant.setInstructions(null);
        doorForParticipant.setPrize(null);
        doorForParticipant.setQuote(null);

        return doorForParticipant;
    }

}
