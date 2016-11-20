# Juleluka API

Building and running locally
---

1. Run `mvn clean install` to build the application
1. Start application with `java -jar target/juleluka-api-0.1.0-SNAPSHOT.jar server config.yml`


Using the API
---

### Creating a new calendar

POST /edit/calendar

##### Request
```
{
  "name": "Acme",
  "adminPassword": "secret"
}
```


### Retrieve an auth token for calendar editing

POST /edit/calendar/auth?name=Acme&adminPassword=secret

##### Response
```
{
  "calendarId": "5832179f7b09a900041b0dae",
  "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vanVsZWx1a2Eubm8iLCJzdWIiOiJjYWxlbmRhcnMvNTgzMjE3OWY3YjA5YTkwMDA0MWIwZGFlIiwiZXhwIjoxNDgwODA5NjAwfQ.n6X4UhqIzRG8o_lhwjsNZByQUutnOYqK7FyC9AaEEAs"
}
```
