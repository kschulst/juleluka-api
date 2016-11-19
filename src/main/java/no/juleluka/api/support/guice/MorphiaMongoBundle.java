package no.juleluka.api.support.guice;

import com.meltmedia.dropwizard.mongo.MongoBundle;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.module.installer.bundle.GuiceyBootstrap;
import ru.vyarus.dropwizard.guice.module.installer.bundle.GuiceyBundle;

public class MorphiaMongoBundle<C extends Configuration> extends MongoBundle<C> implements GuiceyBundle {
    public MorphiaMongoBundle(ConfigurationAccessor<C> configurationAccessor, String healthCheckName) {
        super(configurationAccessor, healthCheckName);
    }

    @Override
    public void run(C configuration, Environment environment) throws Exception {
        super.run(configuration, environment);
    }

    @Override
    public void initialize(GuiceyBootstrap bootstrap) {
        MorphiaInstance.init(getConfiguration(), getClient(), getDB());
// TODO: How can this possibly work when the installer is not registered?
//        bootstrap.installers(MorphiaRepositoryInstaller.class);
    }

    public static class Builder<C extends Configuration> {
        protected ConfigurationAccessor<C> configurationAccessor;
        protected String healthCheckName = "mongo";

        public Builder<C> withConfiguration( ConfigurationAccessor<C> configurationAccessor ) {
            this.configurationAccessor = configurationAccessor;
            return this;
        }

        public Builder<C> withHealthCheckName( String healthCheckName ) {
            this.healthCheckName = healthCheckName;
            return this;
        }

        public MorphiaMongoBundle<C> build() {
            if( configurationAccessor == null ) {
                throw new IllegalArgumentException("configuration accessor is required.");
            }
            return new MorphiaMongoBundle<C>(configurationAccessor, healthCheckName);
        }
    }

    public static <C extends Configuration> Builder<C> create() {
        return new Builder<C>();
    }


}
