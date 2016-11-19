package no.juleluka.api.api;

import lombok.Data;
import no.juleluka.api.models.Participant;

import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
public class ParticipantPublic {
    private String id;
    private String name;

    public static ParticipantPublic from(Participant participant) {
        return LOOSE_MAPPER.map(participant, ParticipantPublic.class);
    }

}
