package no.juleluka.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<String> openedBy = new HashSet();
    private Set<String> winners = new HashSet();
}
