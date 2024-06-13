package telran.java52.book.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java52.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {

	@Query("SELECT DISTINCT p.brand FROM Book b JOIN b.publisher p JOIN b.authors a WHERE a.name = ?1")
	List<String> findPublisherByAuthor(String author);
}
