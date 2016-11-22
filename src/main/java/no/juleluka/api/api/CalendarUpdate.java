package no.juleluka.api.api;

import lombok.Data;
import no.juleluka.api.models.Calendar;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static no.juleluka.api.models.mappers.ModelMappers.nonNullMapper;

@Data
public class CalendarUpdate {

    @Size(min=3, max=20)
    private final String companyName;

    @Size(min=6, max=256)
    private final String adminPassword;

    @Min(0)
    private Integer winnersPerDay;

    public void populateCalendar(Calendar calendar) {
        nonNullMapper().map(this, calendar);
    }

}
