/**
 * Nishkarma Project
 */
package org.nishkarma.petclinic.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/** <pre>
 * 
 * </pre>
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 11. 18. 오후 3:19:45
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 11. 18.     freelsj
 *          -------------------------------------------------------------------
 */
@Controller
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}", method = RequestMethod.GET)
	public ModelAndView WelcomeMain() {		
		ModelAndView mav = null;
		try {
			
			mav = new ModelAndView("welcome");
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));
		}		
		return mav;		
	}
	
}
