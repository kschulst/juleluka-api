package no.juleluka.api.resources;

import com.google.inject.Inject;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.api.CalendarForParticipant;
import no.juleluka.api.api.CalendarPublic;
import no.juleluka.api.api.ParticipantPublic;
import no.juleluka.api.api.ParticipantToken;
import no.juleluka.api.core.security.AuthTokenService;
import no.juleluka.api.db.CalendarRepository;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Participant;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@Api("calendar - participants")
@Path("/calendar")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CalendarResource {

    private final CalendarRepository calendarRepository;
    private final CalendarService calendarService;
    private final AuthTokenService authTokenService;

    @Inject
    public CalendarResource(CalendarRepository calendarRepository,
                            CalendarService calendarService,
                            AuthTokenService authTokenService) {
        this.calendarRepository = requireNonNull(calendarRepository);
        this.calendarService = requireNonNull(calendarService);
        this.authTokenService = requireNonNull(authTokenService);
    }

    @ApiOperation("Participant lookup")
    @GET
    @Path("/participant/lookup")
    public ParticipantToken lookupParticipant(@QueryParam("companyName") @NotEmpty String companyName,
                                              @QueryParam("participantName") @NotEmpty String participantName) {
        Calendar cal = calendarService.findCalendarByName(companyName);

        Participant participant = cal.getParticipants().stream()
            .filter(new Predicate<Participant>() {
                @Override
                public boolean test(Participant participant) {
                    return participant.getName().equalsIgnoreCase(participantName);
                }
            })
            .findFirst()
            .orElseThrow(
                    new Supplier<WebApplicationException>() {
                        @Override
                        public WebApplicationException get() {
                            return new WebApplicationException("Unknown participant", Response.Status.BAD_REQUEST);
                        }
                    }
            );

        ParticipantToken token = new ParticipantToken();
        token.setId(participant.getId().toString());
        token.setName(participant.getName());
        token.setToken(authTokenService.newParticipantToken(participant, companyName));
        return token;
    }

    @ApiOperation("Rertrieve participant calendar")
    @GET
    public CalendarForParticipant getCalendar(@HeaderParam("X-Participant") String participantToken) {
        Calendar cal = calendarService.findCalendarByName(companyName(participantToken));
        return CalendarForParticipant.from(cal);
    }





    private String participantId(String token) {
        try {
            return authTokenService.parseParticipantId(token);
        }
        catch (JwtException e) {
            throw new WebApplicationException("Unable to determine participantId from token", Response.Status.BAD_REQUEST);
        }
    }

    private String companyName(String token) {
        try {
            return authTokenService.parseParticipantCompanyName(token);
        }
        catch (JwtException e) {
            throw new WebApplicationException("Unable to determine companyName from token", Response.Status.BAD_REQUEST);
        }
    }

}
