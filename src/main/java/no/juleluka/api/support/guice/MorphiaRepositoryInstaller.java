package no.juleluka.api.support.guice;

import com.google.inject.Binder;
import com.google.inject.Singleton;
import ru.vyarus.dropwizard.guice.module.installer.FeatureInstaller;
import ru.vyarus.dropwizard.guice.module.installer.install.binding.BindingInstaller;
import ru.vyarus.dropwizard.guice.module.installer.util.FeatureUtils;
import ru.vyarus.dropwizard.guice.module.installer.util.Reporter;

public class MorphiaRepositoryInstaller implements FeatureInstaller<MorphiaRepository>, BindingInstaller {
    private final Reporter reporter = new Reporter(MorphiaRepositoryInstaller.class, "Morphia repositories =");

    @Override
    public <T> void install(Binder binder, Class<? extends T> type, boolean lazy) {
        binder.bind(type).in(Singleton.class);
    }

    @Override
    public boolean matches(Class<?> type) {
        return FeatureUtils.hasAnnotation(type, MorphiaRepository.class);
    }

    @Override
    public void report() {
        reporter.report();
    }

}
