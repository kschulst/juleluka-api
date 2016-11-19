package no.juleluka.api.api;

import lombok.Data;
import no.juleluka.api.models.Participant;

import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
public class ParticipantToken {
    private String id;
    private String name;
    private String token;
}
