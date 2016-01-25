/**
 * Nishkarma Project
 */
package org.nishkarma.common.util;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class NishkarmaMessageSource {

	private static String[] locs = new String[] {
			"spring/business-config.xml", "spring/mvc-core-config.xml",
			"spring/mvc-view-config.xml" };

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			locs);

	/**
	 * Try to resolve the message. Return default message if no message was
	 * found.
	 * 
	 * @param code
	 *            the code to lookup up, such as 'calculator.noRateSet'. Users
	 *            of this class are encouraged to base message names on the
	 *            relevant fully qualified class name, thus avoiding conflict
	 *            and ensuring maximum clarity.
	 * @param args
	 *            array of arguments that will be filled in for params within
	 *            the message (params look like "{0}", "{1,date}", "{2,time}"
	 *            within a message), or {@code null} if none.
	 * @param defaultMessage
	 *            String to return if the lookup fails
	 * @param locale
	 *            the Locale in which to do the lookup
	 * @return the resolved message if the lookup was successful; otherwise the
	 *         default message passed as a parameter
	 * @see java.text.MessageFormat
	 */
	public static String getMessage(String code, Object[] args,
			String defaultMessage, Locale locale) {
		return context.getMessage(code, args, defaultMessage, locale);
	}
	
	public static String getMessage(String code) {
		return context.getMessage(code, null, null);
	}

	public static String getMessage(String code, Locale locale) {
		return context.getMessage(code, null, locale);
	}	
	
	/**
	 * Try to resolve the message. Treat as an error if the message can't be
	 * found.
	 * 
	 * @param code
	 *            the code to lookup up, such as 'calculator.noRateSet'
	 * @param args
	 *            Array of arguments that will be filled in for params within
	 *            the message (params look like "{0}", "{1,date}", "{2,time}"
	 *            within a message), or {@code null} if none.
	 * @param locale
	 *            the Locale in which to do the lookup
	 * @return the resolved message
	 * @throws NoSuchMessageException
	 *             if the message wasn't found
	 * @see java.text.MessageFormat
	 */
	public static String getMessage(String code, Object[] args, Locale locale)
			throws NoSuchMessageException {
		return context.getMessage(code, args, locale);
	}

	/**
	 * Try to resolve the message using all the attributes contained within the
	 * {@code MessageSourceResolvable} argument that was passed in.
	 * <p>
	 * NOTE: We must throw a {@code NoSuchMessageException} on this method since
	 * at the time of calling this method we aren't able to determine if the
	 * {@code defaultMessage} property of the resolvable is null or not.
	 * 
	 * @param resolvable
	 *            value object storing attributes required to properly resolve a
	 *            message
	 * @param locale
	 *            the Locale in which to do the lookup
	 * @return the resolved message
	 * @throws NoSuchMessageException
	 *             if the message wasn't found
	 * @see java.text.MessageFormat
	 */
	public static String getMessage(MessageSourceResolvable resolvable,
			Locale locale) throws NoSuchMessageException {
		return context.getMessage(resolvable, locale);
	}

}
