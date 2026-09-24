package onboarding.smarthome.schema;

import com.blackbuild.klum.ast.layer3.DefaultValues;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@DefaultValues(valueTarget = "displayName")
public @interface DisplayName {

    String value();
}
