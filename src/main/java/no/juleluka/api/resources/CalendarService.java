package no.juleluka.api.resources;

import com.google.inject.Inject;
import io.jsonwebtoken.JwtException;
import no.juleluka.api.core.security.AuthTokenService;
import no.juleluka.api.db.AuthTokenRepository;
import no.juleluka.api.db.CalendarRepository;
import no.juleluka.api.models.Calendar;
import org.bson.types.ObjectId;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final AuthTokenService authTokenService;

    @Inject
    public CalendarService(CalendarRepository calendarRepository,
                                 AuthTokenService authTokenService) {
        this.calendarRepository = requireNonNull(calendarRepository);
        this.authTokenService = requireNonNull(authTokenService);
    }

    // TODO: Don't expose the mongo ObjectId here...
    // TODO: Rewrite this, support common scenarios in a util or base class
    public Calendar findCalendarById(String calendarId) {
        Calendar cal = Optional.ofNullable(calendarRepository.get(new ObjectId(calendarId))).orElseThrow(
                new Supplier<WebApplicationException>() {
                    public WebApplicationException get() {
                        return new WebApplicationException(Response.Status.BAD_REQUEST);
                    }
                }
        );

        return cal;
    }

    public Calendar findCalendarByName(String companyName) {
        Calendar cal = Optional.ofNullable(calendarRepository.findOne("companyName", companyName)).orElseThrow(
                new Supplier<WebApplicationException>() {
                    public WebApplicationException get() {
                        return new WebApplicationException(Response.Status.BAD_REQUEST);
                    }
                }
        );

        return cal;
    }

    public String calendarId(String authToken) {
        final String calendarId;
        try {
            calendarId = authTokenService.parseCalendarId(authToken);
        }
        catch (JwtException e) {
            throw new WebApplicationException("Unable to determine calendar from auth token", Response.Status.UNAUTHORIZED);
        }

        if (!authTokenService.isValidCalendarAuthToken(authToken, calendarId)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        return calendarId;
    }
}
