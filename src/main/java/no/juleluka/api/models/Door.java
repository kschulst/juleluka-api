package no.juleluka.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class Door {
    public Door(int number) {
        this.number = number;
    }

    private int number;
    private String prize;
    private String quote;
    private String instructions;
    private String imageUrl;
    private Set<String> openedBy = new HashSet();
    private Set<String> winners = new HashSet();

    public boolean isOpened(String participantId) {
        return openedBy.contains(participantId);
    }

    public boolean isClosed(String participantId) {
        return ! isOpened(participantId);
    }

    /**
     * The door is considered available if:
     * 1) The date is after 2016-12-24
     * OR
     * 2) The date is after 2016-12-01 AND day of month is GTE the door number
     */
    public boolean isAvailable() {
        return LocalDate.now().isAfter(LocalDate.of(2016, 12, 24)) ||
              (LocalDate.now().isAfter(LocalDate.of(2016, 12, 1)) && LocalDate.now().getDayOfMonth() >= number);
    }

}
