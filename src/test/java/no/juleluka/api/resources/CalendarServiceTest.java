package no.juleluka.api.resources;

import no.juleluka.api.core.security.AuthTokenService;
import no.juleluka.api.db.CalendarRepository;
import no.juleluka.api.models.AuthToken;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Participant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class CalendarServiceTest {

    @Mock
    private AuthTokenService authTokenService;

    @Mock
    private CalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;

    @Test
    public void calculateWinners_noWinnersPerDaySpecified() {
        Calendar c = new Calendar();
        c.getParticipants().add(new Participant("Bolla"));
        c.getParticipants().add(new Participant("Bulle"));
        c.getParticipants().add(new Participant("Bella"));
        c.getParticipants().add(new Participant("Balla"));
        c.getParticipants().add(new Participant("Ballo"));
        assertThat(calendarService.calculateWinners(c)).hasSize(1);
    }

    @Test
    public void calculateWinners_moreWinnersPerDayThanParticipants() {
        Calendar c = new Calendar();
        c.setWinnersPerDay(10);
        c.getParticipants().add(new Participant("Bolla"));
        assertThat(calendarService.calculateWinners(c)).hasSize(1);
    }

    @Test
    public void calculateWinners_zeroWinnersPerDay() {
        Calendar c = new Calendar();
        c.setWinnersPerDay(0);
        assertThat(calendarService.calculateWinners(c)).hasSize(0);

        c.getParticipants().add(new Participant("Bolla"));
        assertThat(calendarService.calculateWinners(c)).hasSize(0);
    }

}