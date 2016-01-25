/**
 * Nishkarma Project
 */
package org.nishkarma.common.model;

/** <pre>
 * 
 * </pre>
 * @Company freelsj
 * @Author freelsj
 * @Date 2013. 10. 28. 오후 3:21:19
 * @since 0.3
 * @history -------------------------------------------------------------------
 *          Date________Auther____Desc.________________________________________
 *          2013. 10. 28.     freelsj
 *          -------------------------------------------------------------------
 */
public class Internationalization {
	
	private String internationalPath;
	private String languageTag;
	private String langFlag;
	private String langauage;
	
	
	public Internationalization(String internationalPath, String languageTag, String langFlag, String langauage) {
		this.setInternationalPath(internationalPath);
		this.languageTag = languageTag;
		this.langFlag = langFlag;
		this.langauage = langauage;
	}
	
	public String getInternationalPath() {
		return internationalPath;
	}
	public void setInternationalPath(String internationalPath) {
		this.internationalPath = internationalPath;
	}	
	public String getLanguageTag() {
		return languageTag;
	}
	public void setLanguageTag(String languageTag) {
		this.languageTag = languageTag;
	}
	public String getLangFlag() {
		return langFlag;
	}
	public void setLangFlag(String langFlag) {
		this.langFlag = langFlag;
	}
	public String getLangauage() {
		return langauage;
	}
	public void setLangauage(String langauage) {
		this.langauage = langauage;
	}

	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("internationalPath=").append(internationalPath)
		.append(", languageTag=").append(languageTag)
		.append(", langFlag=").append(langFlag)
		.append(", langauage=").append(langauage);
		
		return sbf.toString();
	}	
}
