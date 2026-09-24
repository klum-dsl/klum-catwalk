package onboarding.smarthome.schema;

import com.blackbuild.klum.ast.layer3.DefaultValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Supplies a human-readable default for the inherited {@code displayName}
 * property.
 *
 * <p>This small annotation is Java deliberately. KlumAST 4.0.1 validates a
 * {@link DefaultValues} meta-annotation only after the annotation type has
 * already been compiled. Gradle compiles this Java source before the Groovy
 * Schema classes that consume it, keeping the released public-coordinate
 * example in one ordinary project without an extra source set or build step.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@DefaultValues(valueTarget = "displayName")
public @interface DisplayName {

    String value();
}
