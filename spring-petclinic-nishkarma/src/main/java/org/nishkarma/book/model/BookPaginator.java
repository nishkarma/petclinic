package org.nishkarma.book.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.nishkarma.book.model.Book;
import org.nishkarma.common.paging.model.Paginator;

@XmlRootElement
@XmlSeeAlso({ Book.class })
public class BookPaginator<T> extends Paginator<T> {

}
