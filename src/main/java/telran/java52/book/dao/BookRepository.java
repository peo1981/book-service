package telran.java52.book.dao;

import java.util.Optional;

import telran.java52.book.model.Book;


public interface BookRepository  {


	
	Book save(Book book);

	Optional<Book> findById(String isbn);

	void delete(Book book); 

	boolean existsById(String isbn);

}
