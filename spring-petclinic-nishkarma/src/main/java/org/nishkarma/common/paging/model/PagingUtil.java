/**
 * Nishkarma Project
 */
package org.nishkarma.common.paging.model;


public class PagingUtil {

	public static String makePageQuery(String DBMS_TYPE, int page, int size) {

		String limit = "";

		// postgres
		/*
		 * SELECT select_list FROM table_expression [LIMIT { number | ALL }]
		 * [OFFSET number]
		 */

		// mysql
		/*
		 * pagesize = 10 paging = int((gotopage - 1) * 10)
		 * query="select * from myboard order by num desc limit " & paging & ","
		 * & pagesize
		 */

		int paging = (page - 1) * size;

		if ("POSTGRES".equals(DBMS_TYPE)) {
			limit = new StringBuilder().append(size).append(" offset ").append(paging)
					.toString();
		} else {
			limit = new StringBuilder().append(paging).append(",").append(size)
					.toString();
		}

		return limit;
	}

	public static int getTotalPages(int pageSize, long totalCount) {
		if (pageSize == 0)
			return 0;

		return (int) Math.ceil((double) totalCount / pageSize);
	}
}
