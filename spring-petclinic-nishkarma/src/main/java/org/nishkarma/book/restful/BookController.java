package org.nishkarma.book.restful;

import static java.util.Collections.singletonList;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.book.model.Book;
import org.nishkarma.book.model.BookPaginator;
import org.nishkarma.book.service.BookService;
import org.nishkarma.common.paging.model.PagingParam;
import org.nishkarma.common.restful.exception.JsonErrorUtil;
import org.nishkarma.common.restful.exception.RESTfulException;
import org.nishkarma.common.restful.exception.RESTfulValidateException;
import org.nishkarma.common.restful.util.ValidationUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.UriTemplate;

@Path("/book")
@Controller
public class BookController {

	Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Book read(@PathParam("id") int id) {

		Book book = null;

		try {
			book = bookService.findBookById(id);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new RESTfulException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}

		return book;
	}

	@SuppressWarnings("rawtypes")
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public BookPaginator listBooks(@Context HttpServletRequest req) {
		BookPaginator<Book> bookPaginator = new BookPaginator<Book>();

		try {
			PagingParam pagingParam = new PagingParam(req);
			new ValidationUtil<PagingParam>().validate(pagingParam);

			bookPaginator.setPage(pagingParam.getPage_page());
			bookPaginator.setPageSize(pagingParam.getPage_size());

			bookPaginator.setRows((List<Book>) bookService
					.findBooks(pagingParam));
			bookPaginator.setTotal(bookService.findBooksCount());

		} catch (RESTfulValidateException validateException) {
			throw validateException;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new RESTfulException(JsonErrorUtil.getErrorResponse(
					Response.Status.INTERNAL_SERVER_ERROR, "ERROR",
					NishkarmaMessageSource.getMessage("exception_message")));
		}

		return bookPaginator;
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response createBook(@Context HttpServletRequest req, Book book) {

		URI uri = null;

		try {
			new ValidationUtil<Book>().validate(book);

			logger.debug("[Nishkarma]-createBook");
			logger.debug("		book.getAuthor()=" + book.getAuthor());
			logger.debug("		book.getComments()=" + book.getComments());
			logger.debug("		book.getCover()=" + book.getCover());
			logger.debug("		book.getTitle()=" + book.getTitle());
			logger.debug("		book.getPublishedyear()=" + book.getPublishedyear());
			logger.debug("		book.getAvailable()=" + book.getAvailable());

			bookService.saveBook(book);

			uri = new UriTemplate("{requestUrl}/{id}").expand(req
					.getRequestURL().toString(), book.getId());
			HttpHeaders headers = new HttpHeaders();
			headers.put("Location", singletonList(uri.toASCIIString()));

			logger.debug("[Nishkarma]-new uri =" + uri.toASCIIString());

		} catch (RESTfulValidateException validateException) {
			throw validateException;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new RESTfulException(JsonErrorUtil.getErrorResponse(
					Response.Status.INTERNAL_SERVER_ERROR, "ERROR",
					NishkarmaMessageSource.getMessage("exception_message")));
		}

		return Response.created(uri).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response save(Book book) {

		logger.debug("[Nishkarma]--save");

		try {
			new ValidationUtil<Book>().validate(book);

			logger.debug("		book.getAuthor()=" + book.getAuthor());
			logger.debug("		book.getComments()=" + book.getComments());
			logger.debug("		book.getCover()=" + book.getCover());
			logger.debug("		book.getTitle()=" + book.getTitle());
			logger.debug("		book.getPublishedyear()=" + book.getPublishedyear());
			logger.debug("		book.getAvailable()=" + book.getAvailable());

			int cnt = bookService.saveBook(book);

			if (cnt == 0) {
				return Response.noContent().build();
			}
		} catch (RESTfulValidateException validateException) {
			throw validateException;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new RESTfulException(JsonErrorUtil.getErrorResponse(
					Response.Status.INTERNAL_SERVER_ERROR, "ERROR",
					NishkarmaMessageSource.getMessage("exception_message")));
		}

		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response deleteBook(@PathParam("id") int id) {
		try {
			int cnt = bookService.deleteBook(id);

			if (cnt == 0) {
				return Response.noContent().build();
			}

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new RESTfulException(JsonErrorUtil.getErrorResponse(
					Response.Status.INTERNAL_SERVER_ERROR, "ERROR",
					NishkarmaMessageSource.getMessage("exception_message")));

		}
		return Response.ok().build();
	}

}
