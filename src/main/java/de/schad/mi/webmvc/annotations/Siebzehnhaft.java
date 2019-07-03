package de.schad.mi.webmvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import de.schad.mi.webmvc.validators.SiebzehnhaftValidator;

/**
 * Siebzehnhaft is an annotation to validate a variable to contain 17 or siebzehn
 *
 * For implementation see {@link SiebzehnhaftValidator}
 * @author Dennis Schad
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SiebzehnhaftValidator.class)
public @interface Siebzehnhaft {
	String message() default "Siebzehn muss irgendwo vorkommen";

	Class<? extends Payload>[] payload() default { };

	Class<?>[] groups() default { };

}