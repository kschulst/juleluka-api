package no.juleluka.api.core.security;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthTokenServiceTest {

    private final AuthTokenService authTokenService = new AuthTokenService();

    @Test
    public void isValidCalendarAuthToken() {
        String calendarId = "123";
        String authToken = authTokenService.newCalendarAuthToken(calendarId);
        assertThat(authTokenService.isValidCalendarAuthToken(authToken, calendarId)).isTrue() ;
        assertThat(authTokenService.isValidCalendarAuthToken(authToken, "456")).isFalse() ;
        assertThat(authTokenService.isValidCalendarAuthToken(authToken + "a", calendarId)).isFalse();
    }
/*
    @Test
    public void parseJwt() {
        String jwt = jwtService.newCalendarAuthToken("123");
        Jws jws = jwtService.parseJws(jwt);
        assertThat(""+jws.getBody()).startsWith("{iss=http://juleluka.no, sub=calendars/123");
    }
*/
}