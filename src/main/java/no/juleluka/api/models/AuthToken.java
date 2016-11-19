package no.juleluka.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "AuthToken", noClassnameStored = true)
@Data
@NoArgsConstructor
public class AuthToken {
    @Id private ObjectId id = new ObjectId();
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }
}
