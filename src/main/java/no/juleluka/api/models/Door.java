package no.juleluka.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    // TODO: Winners
}
