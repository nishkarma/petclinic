/**
 * Nishkarma Project
 */
package org.nishkarma.common.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.config.NishkarmaCommonData;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.model.Internationalization;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 10. 2. 오후 5:04:48
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 10. 2. freelsj
 *          -------------------------------------------------------------------
 */
public class NishkarmaInterceptor implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(NishkarmaInterceptor.class);

	private String staticUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			String url = request.getRequestURI();

			NishkarmaCommonData nishkarmaCommonData = NishkarmaCommonData
					.getNishkarmaCommonDataInstance();

			String internationalPath = nishkarmaCommonData
					.getInternationalPath(url);

			Internationalization internationalization = nishkarmaCommonData
					.getInternationalizationPath(internationalPath);

			String acceptHeaderLanguageTag = "";
			HttpSession httpSession = request.getSession();

			if (httpSession != null) {
				acceptHeaderLanguageTag = (String) httpSession
						.getAttribute("acceptHeaderLanguageTag");
			}

			if (acceptHeaderLanguageTag != null
					&& acceptHeaderLanguageTag.isEmpty() == false) {
				LocaleResolver localeResolver = RequestContextUtils
						.getLocaleResolver(request);
				if (localeResolver == null) {
					throw new IllegalStateException(
							"No LocaleResolver found: not in a DispatcherServlet request?");
				}

				localeResolver.setLocale(request, response, new Locale(
						internationalization.getLanguageTag()));
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		try {

			if (modelAndView == null)
				return;

			String url = request.getRequestURI();
			NishkarmaCommonData nishkarmaCommonData = NishkarmaCommonData
					.getNishkarmaCommonDataInstance();

			String internationalPath = nishkarmaCommonData
					.getInternationalPath(url);

			Internationalization internationalization = nishkarmaCommonData
					.getInternationalizationPath(internationalPath);
			
			if (internationalization.getLanguageTag() != null
					&& NishkarmaLocaleUtil.resolveLocale().toLanguageTag() != null
					&& (internationalization.getLanguageTag().equals(
							NishkarmaLocaleUtil.resolveLocale().toLanguageTag()) == false)) {

				LocaleResolver localeResolver = RequestContextUtils
						.getLocaleResolver(request);
				if (localeResolver != null) {
					localeResolver.setLocale(request, response, new Locale(
							internationalization.getLanguageTag()));
				}

			}

			modelAndView
					.addObject("internationalization", internationalization);

			url = getLocaleURL(request, internationalization);
			modelAndView.addObject("url", url);

			String scheme = request.getScheme();
			modelAndView.addObject("scheme", scheme);
			modelAndView.addObject("staticUrl", staticUrl);

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			HttpSession httpSession = request.getSession();

			if (httpSession != null) {
				httpSession.removeAttribute("acceptHeaderLanguageTag");
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

		}
	}

	private String getLocaleURL(HttpServletRequest request,
			Internationalization internationalization) {
		String url = request.getRequestURI();
		String queryString = request.getQueryString();

		if (queryString != null && !queryString.isEmpty()) {
			String contextPath = request.getContextPath().replace("/", "%2F");

			int i = queryString.indexOf("url=" + contextPath
					+ "%2Flocalechanger");

			if (i > -1) {
				queryString = queryString.substring(0, i);
			}

			i = queryString.indexOf("internationalPath=");

			if (i > -1) {
				queryString = queryString.substring(0, i);
			}

			if (queryString.isEmpty() == false) {
				url += "?" + queryString;
			}
		}
		url = url.replaceFirst(
				request.getContextPath()
						+ internationalization.getInternationalPath(), "");

		return url;
	}

	public String getStaticUrl() {
		return staticUrl;
	}

	public void setStaticUrl(String staticUrl) {
		this.staticUrl = staticUrl;
	}
}
