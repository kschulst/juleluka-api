package no.juleluka.api.support.guice;

import java.lang.annotation.*;

/**
 * Annotated beans will be injected in guice module as singleton.
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MorphiaRepository {
}
