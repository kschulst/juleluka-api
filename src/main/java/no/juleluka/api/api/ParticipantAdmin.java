package no.juleluka.api.api;

import lombok.Data;
import no.juleluka.api.models.Participant;

import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
public class ParticipantAdmin {
    private String id;
    private String name;

    public static ParticipantAdmin from(Participant participant) {
        return LOOSE_MAPPER.map(participant, ParticipantAdmin.class);
    }

}
