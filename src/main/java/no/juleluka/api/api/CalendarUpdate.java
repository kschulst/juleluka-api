package no.juleluka.api.api;

import lombok.Data;
import no.juleluka.api.models.Calendar;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import static no.juleluka.api.models.mappers.ModelMappers.nonNullMapper;

@Data
public class CalendarUpdate {

    @Size(min=3, max=20)
    private String companyName;

    @Size(min=6, max=256)
    private String adminPassword;

    @Size(min=7, max=256)
    private String logoUrl;

    @Email
    private String contactEmail;

    @Min(0)
    private Integer winnersPerDay;

    private Boolean doorsAlwaysAvailable;

    public void populateCalendar(Calendar calendar) {
        nonNullMapper().map(this, calendar);
    }

}
