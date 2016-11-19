package no.juleluka.api.api.calendar;

import no.juleluka.api.api.CalendarNew;
import no.juleluka.api.models.Calendar;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class NewCalendarTest {
    @Test
    public void toCalendar() {
        CalendarNew newCalendar = new CalendarNew("someName", "secret");

        Calendar calendar = new Calendar();
        calendar.setCompanyName("someName");
        calendar.setAdminPassword("secret");

        assertThat(newCalendar.toCalendar()).hasFieldOrPropertyWithValue("companyName", "someName");
        assertThat(newCalendar.toCalendar()).hasFieldOrPropertyWithValue("adminPassword", "secret");
    }

}