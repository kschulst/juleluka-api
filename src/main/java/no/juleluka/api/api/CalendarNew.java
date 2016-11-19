package no.juleluka.api.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.juleluka.api.models.Calendar;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static no.juleluka.api.models.mappers.ModelMappers.LOOSE_MAPPER;

@Data
@AllArgsConstructor
public class CalendarNew {

    @NotNull @NotEmpty @Size(min=3, max=20)
    private final String companyName;

    @NotNull @NotEmpty @Size(min=6, max=256)
    private final String adminPassword;

    public Calendar toCalendar() {
        Calendar cal = LOOSE_MAPPER.map(this, Calendar.class);
        return cal.init();
    }
}
