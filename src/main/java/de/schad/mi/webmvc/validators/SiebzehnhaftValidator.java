package de.schad.mi.webmvc.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.schad.mi.webmvc.annotations.Siebzehnhaft;

/**
 * SiebzehnhaftValidator contains the validation implementation of Siebzehnhaft Annotation
 * see {@link Siebzehnhaft}
 *
 * @author Dennis Schad
 *
 */
public class SiebzehnhaftValidator implements ConstraintValidator<Siebzehnhaft, String> {

	@Override
	public void initialize(Siebzehnhaft annotationSiebzehnhaft) {
	}

	/**
	 * isValid returns true if given message contains the substring 'siebzehn' or the number 17
	 * @param message message to be validated
	 * @return boolean
	 */
	@Override
	public boolean isValid(String message, ConstraintValidatorContext ctx) {
		return message.toLowerCase().contains("siebzehn") || message.contains("17");
	}

}