package org.nishkarma.common.paging.model;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PagingParam {

	Logger logger = LoggerFactory.getLogger(PagingParam.class);

	//private static final String DBMS_TYPE = "MYSQL";
	private static final String DBMS_TYPE = "POSTGRES";
	
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@Digits(fraction = 0, integer = 10000)
	private int page_page = DEFAULT_PAGE;

	@Digits(fraction = 0, integer = 10000)
	private int page_size = DEFAULT_PAGE_SIZE;

	@Digits(fraction = 0, integer = 10000)
	private int totalPages = 0;
	
	@Pattern(regexp = "[0-9a-zA-Z$_]{1,50}", message = "invalid page_sort characters!")
	private String page_sort;

	@Pattern(regexp = "(asc|desc)", message = "invalid page_sort_dir")
	private String page_sort_dir;

	private String orderByClause = null;
	private String limit = null;

	public PagingParam() {
	}
	
	public PagingParam(HttpServletRequest req) {

		String pageParam = req.getParameter("page_page");
		this.page_page = StringUtils.isEmpty(pageParam) ? DEFAULT_PAGE
				: Integer.parseInt(pageParam);

		String pageSizeParam = req.getParameter("page_size");
		this.page_size = StringUtils.isEmpty(pageSizeParam) ? DEFAULT_PAGE_SIZE
				: Integer.parseInt(pageSizeParam);

		this.page_sort = req.getParameter("page_sort");
		this.page_sort_dir = req.getParameter("page_sort_dir");

		logger.debug("[Nishkarma]-pagingParam.page_page=[" + page_page + "]");
		logger.debug("[Nishkarma]-pagingParam.page_size=[" + page_size + "]");
		logger.debug("[Nishkarma]-pagingParam.getPage_sort=[" + page_sort + "]");
		logger.debug("[Nishkarma]-pagingParam.page_sort_dir=[" + page_sort_dir
				+ "]");

		makePageQuery();
	}

	public void makePageQuery() {

		if (StringUtils.isNotEmpty(page_sort)
				&& StringUtils.isNotEmpty(page_sort_dir)) {
			orderByClause = new StringBuilder().append(page_sort).append(" ")
					.append(page_sort_dir).toString();
		}

		limit = PagingUtil.makePageQuery(DBMS_TYPE, page_page, page_size);

		// disabling for sql injection
		/*
		 * if (_search) { getSearchOper= eq - equal ne - not equal bw - begins
		 * with bn - does not begins with ew - ends with cn - contains nc - does
		 * tno contain in - is in ni - is not in }
		 */

	}

	public int getPage_page() {
		return page_page;
	}

	public void setPage_page(int page_page) {
		this.page_page = page_page;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public String getPage_sort() {
		return page_sort;
	}

	public void setPage_sort(String page_sort) {
		this.page_sort = page_sort;
	}

	public String getPage_sort_dir() {
		return page_sort_dir;
	}

	public void setPage_sort_dir(String page_sort_dir) {
		this.page_sort_dir = page_sort_dir;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

}
