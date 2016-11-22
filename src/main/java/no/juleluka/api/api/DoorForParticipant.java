package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Door;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.looseMapper;

@Data
@JsonInclude(NON_NULL)
public class DoorForParticipant {
    private int number;
    private Boolean open;
    private Boolean win;
    private Boolean available;
    private String prize;
    private String quote;
    private String instructions;
    private String imageUrl;

    public static DoorForParticipant openRepresentationOf(Door door, String participantId) {
        DoorForParticipant doorForParticipant = looseMapper().map(door, DoorForParticipant.class);
        doorForParticipant.setOpen(door.getOpenedBy().contains(participantId));
        doorForParticipant.setAvailable(door.isAvailable());
        if (door.getWinners().size() > 0) { // Only set win if winners have been announced
            doorForParticipant.setWin(door.getWinners().contains(participantId));
        }
        return doorForParticipant;
    }

    public static DoorForParticipant closedRepresentationOf(Door door, String participantId) {
        DoorForParticipant doorForParticipant = openRepresentationOf(door, participantId);
        doorForParticipant.setInstructions(null);
        doorForParticipant.setPrize(null);
        doorForParticipant.setQuote(null);
        doorForParticipant.setImageUrl(null);

        return doorForParticipant;
    }

}
