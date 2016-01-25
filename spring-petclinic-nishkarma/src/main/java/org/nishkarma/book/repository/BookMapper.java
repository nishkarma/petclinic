package org.nishkarma.book.repository;

import java.util.Collection;

import org.nishkarma.book.model.Book;
import org.nishkarma.common.paging.model.PagingParam;

public interface BookMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Book record);

	Book selectByPrimaryKey(Integer id);

	int findAllCount();

	Collection<Book> findAll(PagingParam pagingParam);

	int updateByPrimaryKey(Book record);
}