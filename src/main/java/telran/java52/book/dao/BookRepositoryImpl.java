package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java52.book.model.Author;
import telran.java52.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;

	

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;

	}

	@Override
	public Optional<Book> findById(String isbn) {
		
		return  Optional.ofNullable(em.find(Book.class, isbn));
		
	}

	@Override
	public void delete(Book book) {
		em.remove(book);
				return;

	}

	@Override
	public boolean existsById(String isbn) {
		
		return em.find(Book.class, isbn)!=null;
	}

}
