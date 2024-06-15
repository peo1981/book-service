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
public class BookServiceImpl implements BookService{
	
	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addBook(BookDto bookDto) {
	
	if(	bookRepository.existsById(bookDto.getIsbn())) {
		return false;
	}
	//Publisher
	Publisher publisher =publisherRepository.findById(bookDto.getPublisher())
			.orElseGet(()->publisherRepository.save(new Publisher(bookDto.getPublisher())));
	//Author 
	Set<Author> authors =bookDto.getAuthors().stream().map(a->authorRepository.findById(a.getName())
			.orElseGet(()->authorRepository.save(new Author(a.getName(),a.getBirthDate())))).collect(Collectors.toSet());
	Book book=new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBook(String isbn) {
		Book book =bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		return modelMapper.map(book, BookDto.class);
	}
	@Transactional
	@Override
	public BookDto removeBook(String isbn) {
		
		Book book =bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		bookRepository.delete(book); 
		return modelMapper.map(book, BookDto.class);
	}
	@Transactional(readOnly =true)
	@Override
	public BookDto updateTitle(String isbn, String title) {
		Book book =bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		book.setTitle(title);
		bookRepository.save(book);
		return modelMapper.map(book, BookDto.class);
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<BookDto> findBooksByAuthor(String author) {
		Author author1 =authorRepository.findById(author).orElseThrow(EntityNotFound::new);
		return author1.getBooks().stream()
				.map(p->modelMapper.map(p, BookDto.class)).toList();
	
	}
	
	@Transactional (readOnly =true)
	@Override
	public Iterable<BookDto> findBooksByPublisher(String publisher) {
		Publisher publisher1 =publisherRepository.findById(publisher).orElseThrow(EntityNotFound::new);
		return publisher1.getBooks().stream()
				.map(p->modelMapper.map(p, BookDto.class)).toList();
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<AuthorDto> findBooksAuthors(String isbn) {
		Book book =bookRepository.findById(isbn).orElseThrow(EntityNotFound::new);
		return book.getAuthors().stream().map(p->modelMapper.map(p, AuthorDto.class)).toList();
	}
	@Transactional (readOnly =true)
	@Override
	public Iterable<String> findPublishersByAuthor(String author) {
				return publisherRepository.findDistinctByBooksAuthorsName(author)
				.map(Publisher::getBrand).toList();
	}
	@Transactional
	@Override
	public AuthorDto removeAuthor(String authorName) {
		  
		    Author author =authorRepository.findById(authorName).orElseThrow(EntityNotFound::new);
		    authorRepository.delete(author);
			
			return modelMapper.map(author, AuthorDto.class);
		
	}

}
