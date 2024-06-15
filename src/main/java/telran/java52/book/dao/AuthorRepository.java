package telran.java52.book.dao;

import java.util.Optional;

import telran.java52.book.model.Author;

public interface AuthorRepository {

	Author save(Author author);

	Optional<Author> findById(String authorName);

	void delete(Author author);
	

	
	

}
