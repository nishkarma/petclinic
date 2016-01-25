package org.nishkarma.common.restful.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;

import org.nishkarma.common.restful.exception.JsonErrorUtil;
import org.nishkarma.common.restful.exception.RESTfulValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * RESTFul Service Controller Validation Utility
 * In Spring Controller use @valid annotation
 * , but in jersey restful service use like this
 * 
 * public Response createBook(@Context HttpServletRequest req, Book book) {
 * 
 * 	new ValidationUtil<Book>().validate(book);
 * 
 * </pre>
 * 
 */
public class ValidationUtil<T> {

	Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

	private static Validator validator;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * <pre>
	 * RESTFul Service Controller Validation
	 * </pre>
	 * 
	 * @param t
	 *            bean to validate
	 * @throws RESTfulValidateException
	 */
	public void validate(T t) throws RESTfulValidateException {
		Map<String, String> errorMap = new HashMap<String, String>();

		Set<ConstraintViolation<T>> violations = validator.validate(t);
		for (ConstraintViolation<T> violation : violations) {
			if (logger.isDebugEnabled()) {
				logger.debug("[Nishkarma]-" + violation.getPropertyPath() + ":"
						+ violation.getMessage() + "," + violation.toString());
			}
			errorMap.put(violation.getPropertyPath().toString(),
					violation.getMessage());
		}

		if (!errorMap.isEmpty()) {
			throw new RESTfulValidateException(JsonErrorUtil.getErrorResponse(
					Response.Status.BAD_REQUEST, errorMap));
		}
	}
}
