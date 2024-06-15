package telran.java52.book.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import telran.java52.book.model.Author;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

	@PersistenceContext
	EntityManager em;

	@Transactional
	@Override
	public Author save(Author author) {
		em.persist(author);
		return author;
	}

	@Override
	public Optional<Author> findById(String authorName) {
		
		return  Optional.ofNullable(em.find(Author.class, authorName));
	}

	@Override
	public void delete(Author author) {
		
		em.remove(author);
		return;

	}

}
