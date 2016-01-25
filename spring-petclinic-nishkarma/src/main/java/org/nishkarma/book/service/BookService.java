package org.nishkarma.book.service;

import java.util.Collection;

import org.nishkarma.book.model.Book;
import org.nishkarma.book.repository.BookMapper;
import org.nishkarma.common.paging.model.PagingParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

	Logger logger = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookMapper bookMapper;

	@Transactional(readOnly = true)
	public Book findBookById(int id) throws DataAccessException {
		Book book = bookMapper.selectByPrimaryKey(id);
		logger.debug("[Nishkarma]-" + book.getTitle());

		return book;
	}

	@Transactional(readOnly = true)
	public Collection<Book> findBooks(PagingParam pagingParam)
			throws DataAccessException {
		return bookMapper.findAll(pagingParam);
	}

	@Transactional(readOnly = true)
	public int findBooksCount() throws DataAccessException {
		return bookMapper.findAllCount();
	}

	@Transactional
	public int saveBook(Book book) throws DataAccessException {
		int cnt = 0;
		logger.debug("[Nishkarma]-save-getId=" + book.getId());

		if (book.isNew()) {
			logger.debug("[Nishkarma]-save-isNew-insert");
			cnt = bookMapper.insert(book);
		} else {
			logger.debug("[Nishkarma]-save-updateByPrimaryKey");
			cnt = bookMapper.updateByPrimaryKey(book);
		}

		return cnt;
	}

	@Transactional
	public int deleteBook(int id) throws DataAccessException {
		return bookMapper.deleteByPrimaryKey(id);
	}

}
