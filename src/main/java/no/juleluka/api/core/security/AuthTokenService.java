package no.juleluka.api.core.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.models.Participant;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

@Slf4j
public class AuthTokenService {

    // TODO: Read key from config
    private final Key KEY = MacProvider.generateKey();
    private final String CALENDAR_SUBJECT_PREFIX = "calendars/";

    public String newCalendarAuthToken(String calendarId) {
        checkArgument(!isNullOrEmpty(calendarId), "calendarId is required for creating an auth token");
        Date expirationDate = Date.from(LocalDate.now().plusWeeks(2L).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setIssuer("http://juleluka.no")
                .setSubject(CALENDAR_SUBJECT_PREFIX + calendarId)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public String parseCalendarId(String authToken) {
        String subject = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
        return subject.substring(CALENDAR_SUBJECT_PREFIX.length());
    }

    public boolean isValidCalendarAuthToken(String authToken, String calendarId) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(KEY)
                    .requireSubject(CALENDAR_SUBJECT_PREFIX + calendarId)
                    .parseClaimsJws(authToken);

            return new Date().before(jws.getBody().getExpiration());
        }
        catch (JwtException e) {
            log.info("Invalid authToken: " + e.getMessage());
            return false;
        }
    }

    public String newParticipantToken(Participant participant, String companyName) {
        return Jwts.builder()
                .claim("companyName", companyName)
                .claim("participantId", participant.getId())
                .claim("participantName", participant.getName())
                .signWith(SignatureAlgorithm.HS256, KEY)
                .compact();
    }

    public String parseParticipantId(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("participantId", String.class);
    }

    public String parseParticipantCompanyName(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("companyName", String.class);
    }

}
