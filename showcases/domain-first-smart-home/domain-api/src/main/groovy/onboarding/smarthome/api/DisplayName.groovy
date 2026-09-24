package onboarding.smarthome.api

import com.blackbuild.klum.ast.layer3.DefaultValues

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Domain-level vocabulary for assigning the inherited {@code displayName}
 * property from a concrete Schema type or field.
 *
 * The annotation belongs to the Domain API because {@code displayName} is part
 * of that contract. A Schema owns the actual human-readable values it assigns.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.FIELD])
@DefaultValues(valueTarget = 'displayName')
@interface DisplayName {

    String value()
}
