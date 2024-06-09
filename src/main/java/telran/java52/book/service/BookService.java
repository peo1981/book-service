package telran.java52.book.service;

import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;

public interface BookService {

	Boolean addBook(BookDto bookDto);
	BookDto findBook(String isbn);
	BookDto removeBook(String isbn);
	BookDto updateTitle(String isbn,String title);
	Iterable<BookDto> findBooksByAuthor(String author);
	Iterable<BookDto> findBooksByPublisher(String publisher);
	Iterable<AuthorDto> findBooksAuthors(String isbn);
	Iterable<String> findPublishersByAuthor(String author);
	AuthorDto removeAuthor(String authorName);
}
