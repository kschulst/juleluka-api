package no.juleluka.api.resources;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.api.*;
import no.juleluka.api.core.security.AuthTokenService;
import no.juleluka.api.db.AuthTokenRepository;
import no.juleluka.api.db.CalendarRepository;
import no.juleluka.api.models.AuthToken;
import no.juleluka.api.models.Calendar;
import no.juleluka.api.models.Door;
import no.juleluka.api.models.Participant;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Api("calendar - edit")
@Path("/edit/calendar")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class CalendarEditResource {

    private final CalendarRepository calendarRepository;
    private final CalendarService calendarService;
    private final AuthTokenRepository authTokenRepository;
    private final AuthTokenService authTokenService;

    @Inject
    public CalendarEditResource(CalendarRepository calendarRepository,
                                CalendarService calendarService,
                                AuthTokenRepository authTokenRepository,
                                AuthTokenService authTokenService) {
        this.calendarRepository = requireNonNull(calendarRepository);
        this.calendarService = requireNonNull(calendarService);
        this.authTokenRepository = requireNonNull(authTokenRepository);
        this.authTokenService = requireNonNull(authTokenService);
    }

    @ApiOperation("Create a new calendar")
    @POST
    public CalendarAdmin createCalendar(@Valid CalendarNew newCalendar) {
        // Don't create the calendar if a calendar for that companyName is already registered
        if (calendarRepository.findOne("companyName", newCalendar) != null) {
            throw new WebApplicationException("Calendar for '" + newCalendar.getCompanyName() + "' already exists.", Response.Status.CONFLICT);
        };

        Calendar cal = newCalendar.toCalendar();
        calendarRepository.save(cal);
        return CalendarAdmin.from(cal);
    }

    @ApiOperation("Authorize for calendar editing")
    @POST
    @Path("/auth")
    public CalendarAuth auth(@QueryParam("companyName") @NotEmpty String companyName,
                             @QueryParam("password") @NotEmpty String adminPassword) {
        Calendar cal = calendarService.findCalendarByName(companyName);
        if (! cal.getAdminPassword().equals(adminPassword)) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String authToken = authTokenService.newCalendarAuthToken(cal.getId().toString());
        authTokenRepository.save(new AuthToken(authToken));

        CalendarAuth calAuth = new CalendarAuth();
        calAuth.setCalendarId(cal.getId().toString());
        calAuth.setAuthToken(authToken);

        return calAuth;
    }

    @ApiOperation("Retrieve calendar for administration by id")
    @GET
    public CalendarAdmin getCalendarById(@HeaderParam("Authorization") @NotEmpty String authToken) {
        Calendar cal = calendarService.findCalendarById(calendarId(authToken));
        return CalendarAdmin.from(cal);
    }

    @ApiOperation("Update a calendar door")
    @PUT
    @Path("/doors/{doorNumber}")
    public void updateDoor(@HeaderParam("Authorization") @NotEmpty String authToken,
                           @PathParam("doorNumber") @Min(1) @Max(24) Integer doorNumber,
                           @Valid DoorAdmin doorToUpdate) {
        Calendar cal = calendarService.findCalendarById(calendarId(authToken));
        Door door = doorToUpdate.toDoor();
        door.setNumber(doorNumber); // TODO: Two separate models for Doors (request vs response)
        cal.getDoors().set(doorNumber-1, doorToUpdate.toDoor());

        calendarRepository.save(cal);
    }

    @ApiOperation("Add a calendar participant")
    @POST
    @Path("/participants")
    public void addParticipant(@HeaderParam("Authorization") @NotEmpty String authToken,
                           final @Valid ParticipantNew newParticipant) {
        Calendar cal = calendarService.findCalendarById(calendarId(authToken));

        // Don't add the participant if the calendar already has a participant with that name
        if (Iterables.any(cal.getParticipants(), new Predicate<Participant>() {
            @Override
            public boolean apply(Participant participant) {
                return participant.getName().equalsIgnoreCase(newParticipant.getName());
            }
        })) {
            throw new WebApplicationException("Participant with name '" + newParticipant.getName() + "' already exists.", Response.Status.CONFLICT);
        }

        cal.getParticipants().add(new Participant(newParticipant.getName()));
        calendarRepository.save(cal);
    }

    @ApiOperation("Delete a calendar participant")
    @DELETE
    @Path("/participants/{participantId}")
    public void deleteParticipant(@HeaderParam("Authorization") @NotEmpty String authToken,
                                  @PathParam("participantId") String participantId) {
        Calendar cal = calendarService.findCalendarById(calendarId(authToken));

        boolean removed = cal.getParticipants().removeIf(new java.util.function.Predicate<Participant>() {
            @Override
            public boolean test(Participant participant) {
                return participant.getId().equals(participantId);
            }
        });

        if (removed) {
            calendarRepository.save(cal);
        }
        else {
            throw new WebApplicationException("No participants with id " + participantId + " in the calendar", Response.Status.BAD_REQUEST);
        }
    }

    private String calendarId(String authToken) {
        return calendarService.calendarId(authToken);
    }

}
