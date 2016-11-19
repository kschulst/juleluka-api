package no.juleluka.api;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import no.juleluka.api.support.guice.MorphiaMongoBundle;
import no.juleluka.api.support.guice.MorphiaRepositoryInstaller;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class JulelukaApplication extends Application<JulelukaConfiguration> {

    public static void main(final String[] args) throws Exception {
        new JulelukaApplication().run(args);
    }

    @Override
    public String getName() {
        return "Juleluka";
    }

    @Override
    public void initialize(final Bootstrap<JulelukaConfiguration> bootstrap) {

        // Ref: https://github.com/smoketurner/dropwizard-swagger
        bootstrap.addBundle(new SwaggerBundle<JulelukaConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(JulelukaConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

        // Ref: https://github.com/meltmedia/dropwizard-mongo
        bootstrap.addBundle(MorphiaMongoBundle.<JulelukaConfiguration>create()
                .withConfiguration(JulelukaConfiguration::getMongoConfiguration)
                .build());

        // Ref: https://github.com/xvik/dropwizard-guicey
        bootstrap.addBundle(GuiceBundle.<JulelukaConfiguration>builder()
                        .enableAutoConfig("no.juleluka.api")
                        .printDiagnosticInfo()
//                        .bindConfigurationInterfaces()
                        .configureFromDropwizardBundles() // tODO: Detectivism on how this works
                        .build()
        );
    }

    @Override
    public void run(final JulelukaConfiguration configuration,
                    final Environment environment) {
    }

}
