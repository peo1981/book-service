package telran.java52.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.service.BookService;


@RestController
@RequiredArgsConstructor
public class BookController {
final BookService bookService;

@PostMapping("/book")
public Boolean addBook(@RequestBody BookDto bookDto) {
	
	return bookService.addBook(bookDto);
}

@GetMapping("/book/{isbn}")
public BookDto findBook(@PathVariable String isbn) {
	
	return bookService.findBook(isbn);
}

@DeleteMapping("/book/{isbn}")
public BookDto removeBook(@PathVariable String isbn) {
	
	return bookService.removeBook(isbn);
}

@PutMapping("/book/{isbn}/title/{title}")
public BookDto updateTitle(@PathVariable String isbn,@PathVariable String title) {
	
	return bookService.updateTitle(isbn, title);
}

@GetMapping("/books/author/{author}")
public Iterable<BookDto> findBooksByAuthor(@PathVariable String author) {
	
	return bookService.findBooksByAuthor(author);
}

@GetMapping("/books/publisher/{publisher}")
public Iterable<BookDto> findBooksByPublisher(@PathVariable String publisher) {
	
	return bookService.findBooksByPublisher(publisher);
}

@GetMapping("/authors/book/{isbn}")
public Iterable<AuthorDto> findBooksAuthors(@PathVariable String isbn) {
	
	return bookService.findBooksAuthors(isbn);
}

@GetMapping("/publishers/author/{author}")
public Iterable<String> findPublishersByAuthor(@PathVariable String author) {
	
	return bookService.findPublishersByAuthor(author);
}

@DeleteMapping ("/author/{authorName}")
public AuthorDto removeAuthor(@PathVariable String authorName) {
	
	return bookService.removeAuthor(authorName);
}





}
