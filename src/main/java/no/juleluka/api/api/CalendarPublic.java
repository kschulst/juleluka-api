package no.juleluka.api.api;

import lombok.AllArgsConstructor;
import lombok.Value;
import no.juleluka.api.models.Calendar;

import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Value
@AllArgsConstructor
public class CalendarPublic {
    private String id;
    private String companyName;

    public static CalendarPublic from(Calendar calendar) {
        return LOOSE_MAPPER.map(calendar, CalendarPublic.class);
    }
}
