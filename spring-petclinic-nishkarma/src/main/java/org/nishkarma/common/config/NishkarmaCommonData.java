/**
 * Nishkarma Project
 */
package org.nishkarma.common.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.model.Internationalization;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 10. 28. 오후 3:01:27
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 10. 28. freelsj
 *          -------------------------------------------------------------------
 */
public class NishkarmaCommonData {

	Logger logger = LoggerFactory.getLogger(NishkarmaCommonData.class);

	private static NishkarmaCommonData ref;

	private ConcurrentHashMap<String, Internationalization> pathNishkarmaLocales;
	private ConcurrentHashMap<String, Internationalization> langNishkarmaLocales;
	
	private static String DEFAULT_INTERNATION_URL = "/en-us";
	private static String DEFAULT_INTERNATION_LANG = "en";
	private static Pattern internationalURLPattern;
	private static Pattern urlDelimeterPattern;

	private NishkarmaCommonData() {
		try {
			pathNishkarmaLocales = new ConcurrentHashMap<String, Internationalization>();

			pathNishkarmaLocales.put("/en-us", new Internationalization("/en-us",
					"en", "usa", "English"));
			pathNishkarmaLocales.put("/ko-kr", new Internationalization("/ko-kr",
					"ko", "kor", "한국어"));
			pathNishkarmaLocales.put("/zh-cn", new Internationalization("/zh-cn",
					"zh", "chn", "简体中文"));
			pathNishkarmaLocales.put("/ru-ru", new Internationalization("/ru-ru",
					"ru", "rus", "Русский"));
			pathNishkarmaLocales.put("/ja-jp", new Internationalization("/ja-jp",
					"ja", "jpn", "日本語"));
			
			langNishkarmaLocales = new ConcurrentHashMap<String, Internationalization>();
			langNishkarmaLocales.put("en", new Internationalization("/en-us",
					"en", "usa", "English"));
			langNishkarmaLocales.put("ko", new Internationalization("/ko-kr",
					"ko", "kor", "한국어"));
			langNishkarmaLocales.put("zh", new Internationalization("/zh-cn",
					"zh", "chn", "简体中文"));
			langNishkarmaLocales.put("ru", new Internationalization("/ru-ru",
					"ru", "rus", "Русский"));
			langNishkarmaLocales.put("jp", new Internationalization("/ja-jp",
					"ja", "jpn", "日本語"));
			
			urlDelimeterPattern = Pattern.compile("/");
			internationalURLPattern = Pattern.compile("[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}");
			
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));
		}
	}

	public static NishkarmaCommonData getNishkarmaCommonDataInstance() {
		if (ref == null) {
			ref = new NishkarmaCommonData();
		}
		return ref;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public Internationalization getInternationalizationByLang(String lang) {

		Internationalization internationalization = langNishkarmaLocales
				.get(lang);
		if (internationalization == null) {
			internationalization = langNishkarmaLocales.get(DEFAULT_INTERNATION_LANG);
		}

		return internationalization;
	}
	
	public Internationalization getInternationalizationPath(String internationalPath) {

		Internationalization internationalization = pathNishkarmaLocales
				.get(internationalPath);
		
		if (internationalization == null) {
			internationalization = pathNishkarmaLocales.get(DEFAULT_INTERNATION_URL);
		}

		return internationalization;
	}

	public String getInternationalPath(String url) {
		String internationalPath = "";
		try {
			String[] result = urlDelimeterPattern.split(url);

			if (result.length < 3) {
				internationalPath = DEFAULT_INTERNATION_URL;
			} else {
				internationalPath = result[2];
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			internationalPath = DEFAULT_INTERNATION_URL;
		}

		return "/" + internationalPath;
	}

	public boolean hasInternationalPath(String url) {
		boolean rtn = false;
		try {
			rtn = internationalURLPattern.matcher(url).find();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			rtn = false;
		}
		
		
		return rtn;
	}
	
}
