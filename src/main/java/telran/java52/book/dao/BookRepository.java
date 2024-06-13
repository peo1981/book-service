package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java52.book.model.Book;


public interface BookRepository extends JpaRepository<Book, String> {

	@Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors"
			+ " LEFT JOIN FETCH b.publisher WHERE b.isbn =?1")
	Optional<Book> findByIdWithProps(String isbn);
	
//	@Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name = ?1")
//	Stream<Book> findAllByAuthor(String author);
	
	Stream<Book> findAllByAuthorsName(String author);
	
	//@Query("SELECT b FROM Book b WHERE b.publisher.brand = ?1")
	Stream<Book> findAllByPublisherBrand(String publisher);
	
	void deleteByAuthorsName(String name);

	

}
