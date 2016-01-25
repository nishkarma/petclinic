package org.nishkarma.common.paging.model;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.Collection;

@XmlRootElement
public class Paginator<T> {

	private int page;
	private int pageSize;

	private long total;
	private Collection<T> rows;

	public Paginator() {
	}

	public Collection<T> getRows() {
		return rows;
	}

	public void setRows(Collection<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
