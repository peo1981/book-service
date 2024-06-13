package telran.java52.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java52.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {

	Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);
}
