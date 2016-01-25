package org.nishkarma.common.util;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class BindingResultDebug {

	static Logger logger = LoggerFactory.getLogger(BindingResultDebug.class);

	public static void print(BindingResult result) {
		if (result.hasErrors()) {

			logger.debug("[Nishkarma]-BindingResult Total error count : "
					+ result.getErrorCount());
			logger.debug("-------------------------------------------------");

			List<ObjectError> allObjectErrors = result.getAllErrors();
			for (ObjectError objectError : allObjectErrors) {

				if (objectError instanceof FieldError) {

					FieldError fieldError = (FieldError) objectError;
					logger.debug("Field name : " + fieldError.getField());
				}

				logger.debug("Codes "
						+ Arrays.asList(objectError.getCodes()).toString());
				logger.debug("Error Code : " + objectError.getCode());
				logger.debug("Default message : "
						+ objectError.getDefaultMessage());
				logger.debug("");
			}
			System.out
					.println("-------------------------------------------------");
		} else {
			logger.debug("[Nishkarma]-BindingResult - no errors");
		}
	}
}
