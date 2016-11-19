package no.juleluka.api.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.models.Calendar;

import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
@JsonInclude(NON_NULL)
public class CalendarAdmin {
    private String id;
    private String companyName;
    private List<Integer> doorSequence;
    private List<DoorAdmin> doors;
    private Set<ParticipantAdmin> participants;

    public static CalendarAdmin from(Calendar calendar) {
        return LOOSE_MAPPER.map(calendar, CalendarAdmin.class);
    }

}
