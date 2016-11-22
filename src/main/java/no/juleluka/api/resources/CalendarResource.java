package no.juleluka.api.resources;

import com.google.inject.Inject;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.api.CalendarForParticipant;
import no.juleluka.api.api.DoorForParticipant;
import no.juleluka.api.api.ParticipantToken;
import no.juleluka.api.core.security.AuthTokenService;
import no.juleluka.api.db.CalendarRepository;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Door;
import no.juleluka.api.models.Participant;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;
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

    @ApiOperation("Retrieve participant's calendar")
    @GET
    public CalendarForParticipant getCalendar(@HeaderParam("X-Participant") @NotEmpty String participantToken) {
        Calendar cal = calendarService.findCalendarByName(companyName(participantToken));
        String participantId = authTokenService.parseParticipantId(participantToken);
        return CalendarForParticipant.from(cal, participantId);
    }

    @ApiOperation("Open a participant's calendar door")
    @POST
    @Path("/doors/{doorNumber}/open")
    public DoorForParticipant openCalendarDoor(@HeaderParam("X-Participant") @NotEmpty String participantToken,
                                               @PathParam("doorNumber") @Min(1) @Max(24) Integer doorNumber) {
        Calendar cal = calendarService.findCalendarByName(companyName(participantToken));
        String participantId = authTokenService.parseParticipantId(participantToken);
        Door door = cal.getDoors().get(doorNumber - 1);

        if (! door.isAvailable(cal.getDoorsAlwaysAvailable())) {
            throw new WebApplicationException("Door number " + doorNumber + " is not available yet.", Response.Status.BAD_REQUEST);
        }
        door.getOpenedBy().add(participantId);
        calendarRepository.save(cal);
        DoorForParticipant doorForParticipant = DoorForParticipant.openRepresentationOf(door, participantId, cal.getDoorsAlwaysAvailable());
        return doorForParticipant;
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
/*
    private Set<String> calculateWinners(Calendar calendar) {
        Set<String> winners = new HashSet();
        for (int i=0; i<calendar.getWinnersPerDay(); i++) {
            winners.add(calendar.getParticipants().)
        }
    }
*/
}
