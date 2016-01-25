/**
 * Nishkarma Project
 */
package org.nishkarma.common.util;

import java.util.Locale;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class NishkarmaLocaleUtil {

	static Logger logger = LoggerFactory.getLogger(NishkarmaLocaleUtil.class);
	
	public static final String DEFAULT_LANGUAGE = "en";

	public static Locale resolveLocale() {

		try {
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession();
			if (session != null) {
				Locale locale = (Locale) session
						.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

				if (locale == null) {
					locale = new Locale(DEFAULT_LANGUAGE);
				}

				return locale;

			} else {
				return new Locale(DEFAULT_LANGUAGE);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return new Locale(DEFAULT_LANGUAGE);
		}
	}
}
