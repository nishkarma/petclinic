/**
 * Nishkarma Project
 */
package org.nishkarma.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.model.Internationalization;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.common.config.NishkarmaCommonData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 10. 29. 오후 3:25:50
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 10. 29. freelsj
 *          -------------------------------------------------------------------
 */
@Controller
public class LocaleChangeController {

	Logger logger = LoggerFactory.getLogger(LocaleChangeController.class);

	@RequestMapping(value = "/localechanger", method = RequestMethod.GET)
	public String localeChange(HttpServletRequest request, HttpServletResponse response, String internationalPath,
			String url) {

		Internationalization internationalization = null;

		try {
			
			if (url.equals(request.getContextPath())) {
				url = "/";
			}

			NishkarmaCommonData nishkarmaCommonData = NishkarmaCommonData.getNishkarmaCommonDataInstance();

			internationalization = nishkarmaCommonData.getInternationalizationPath(internationalPath);

			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}

			localeResolver.setLocale(request, response,
					StringUtils.parseLocaleString(internationalization.getLanguageTag()));

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}

		return "redirect:" + internationalization.getInternationalPath() + url;
	}
}
