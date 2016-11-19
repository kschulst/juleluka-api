package no.juleluka.api.api;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ParticipantNew {
    @NotNull @NotEmpty @Size(min=2, max=20)
    private String name;
}
