/**
 * Nishkarma Project
 */
package org.nishkarma.common.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.config.NishkarmaCommonData;
import org.nishkarma.common.model.Internationalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 10. 29. 오후 6:23:45
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 10. 29. freelsj
 *          -------------------------------------------------------------------
 */
public class AcceptLocaleFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(AcceptLocaleFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		try {
			HttpServletRequest hreq = (HttpServletRequest) request;

			String url = hreq.getRequestURI();

			NishkarmaCommonData nishkarmaCommonData = NishkarmaCommonData.getNishkarmaCommonDataInstance();

			String localechanger = hreq.getContextPath() + "/localechanger";
			String restfulContext = hreq.getContextPath() + "/restful";

			if ((url.startsWith(restfulContext) == false) && (nishkarmaCommonData.hasInternationalPath(url) == false)
					&& (url.equals(localechanger) == false)) {
				HttpServletResponse hres = (HttpServletResponse) response;

				AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
				Locale locale = acceptHeaderLocaleResolver.resolveLocale(hreq);
				String languageTag = locale.toLanguageTag();

				if (languageTag == null || languageTag.isEmpty()) {
					languageTag = "us";
				} else {
					int i = languageTag.indexOf('-');

					if (i > -1) {

						languageTag = languageTag.substring(0, i);
					}
				}

				Internationalization internationalization = nishkarmaCommonData.getInternationalizationByLang(languageTag);

				String newURL = hreq.getContextPath() + internationalization.getInternationalPath();

				if (response.isCommitted() == false) {
					HttpSession httpSession = hreq.getSession(true);
					httpSession.setAttribute("acceptHeaderLanguageTag", languageTag);

					hres.sendRedirect(newURL);
					return;
				}
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
