package de.schad.mi.webmvc.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.schad.mi.webmvc.annotations.Siebzehnhaft;

/**
 * SiebzehnhaftValidator
 */
public class SiebzehnhaftValidator implements ConstraintValidator<Siebzehnhaft, String> {

	@Override
	public void initialize(Siebzehnhaft annotationSiebzehnhaft) {
	}

	@Override
	public boolean isValid(String message, ConstraintValidatorContext ctx) {

		boolean containsSiebzehn = message.toLowerCase().contains("siebzehn") || message.contains("17");

		return containsSiebzehn;
	}

}