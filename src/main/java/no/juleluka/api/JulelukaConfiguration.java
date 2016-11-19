package no.juleluka.api;

import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

public class JulelukaConfiguration extends Configuration {

    @JsonProperty("mongo")
    protected MongoConfiguration mongoConfiguration;

    @JsonProperty("swagger")
    protected SwaggerBundleConfiguration swaggerBundleConfiguration;


    public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
        return swaggerBundleConfiguration;
    }

    public MongoConfiguration getMongoConfiguration() {
        return mongoConfiguration;
    }
}
