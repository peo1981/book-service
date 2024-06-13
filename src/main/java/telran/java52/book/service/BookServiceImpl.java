package telran.java52.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.book.dao.AuthorRepository;
import telran.java52.book.dao.BookRepository;
import telran.java52.book.dao.PublisherRepository;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.dto.exception.EntityNotFound;
import telran.java52.book.model.Author;
import telran.java52.book.model.Book;
import telran.java52.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addBook(BookDto bookDto) {

		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Author
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeBook(String isbn) {

		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public BookDto updateTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		book.setTitle(title);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByAuthor(String author) {

		return bookRepository.findAllByAuthorsName(author).map(p -> modelMapper.map(p, BookDto.class)).toList();

	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisher) {
		return bookRepository.findAllByPublisherBrand(publisher).map(p -> modelMapper.map(p, BookDto.class)).toList();
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<AuthorDto> findBooksAuthors(String isbn) {
		Book book = bookRepository.findByIdWithProps(isbn).orElseThrow(EntityNotFound::new);
		return book.getAuthors().stream().map(p -> modelMapper.map(p, AuthorDto.class)).toList();
	}


	@Override
	public Iterable<String> findPublishersByAuthor(String author) {
		return publisherRepository.findPublisherByAuthor(author);
	}

	@Transactional
	@Override
	public AuthorDto removeAuthor(String authorName) {
//		List <BookDto> authorsBooks = (List<BookDto>) findBooksByAuthor (authorName);
//		if(authorsBooks.size()!=0) {
//		System.out.println("You have to delete all books of this author before removing!!!");
//				return null;
//		} else {
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFound::new);
		//bookRepository.findAllByAuthorsName(authorName).forEach(b->bookRepository.delete(b));
		bookRepository.deleteByAuthorsName(authorName);
			authorRepository.delete(author);
		
		return modelMapper.map(author, AuthorDto.class);
//		}

	}

}
