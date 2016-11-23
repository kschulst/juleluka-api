package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Door;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;
import static no.juleluka.api.models.mappers.ModelMappers.looseMapper;

@Data
@JsonInclude(NON_NULL) // TODO: Add this as a setting in the ObjectMapper instead
public class DoorAdmin {
    private int number;
    private String prize;
    private String quote;
    private String instructions;
    private String imageUrl;
    private Set<String> openedBy = new HashSet();
    private Set<String> winners = new HashSet();

    public Door toDoor() {
        return looseMapper().map(this, Door.class);
    }

    public static DoorAdmin from(Door door) {
        return looseMapper().map(door, DoorAdmin.class);
    }
}
