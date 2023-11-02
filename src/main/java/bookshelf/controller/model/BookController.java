package bookshelf.controller.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bookshelf.controller.model.BookData.BookAuthor;
import bookshelf.controller.model.BookData.BookGenre;
import bookshelf.service.BookService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bookshelf")
@Slf4j
public class BookController {
	
	@Autowired
	public BookService bookService;
	
	@PostMapping("/author/{authorId}/book")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookData newBook(@PathVariable Long authorId, @RequestBody BookData bookData) {
		log.info("Creating a new book");
		return bookService.saveBook(authorId, bookData);
		
		
	}
	
	@PutMapping("/author/{authorId}/book/{bookId}")
	public BookData modifyBook(@PathVariable Long authorId, @PathVariable Long bookId, @RequestBody BookData bookData) {
		bookData.setBookId(bookId);
		log.info("Modifying book details");
		return bookService.saveBook(authorId, bookData);
		
	}
	
	@GetMapping("/author/{authorId}/book")
	public List<BookData> retrieveAllBooksFromAuthor () {
		log.info("Retrieving list of all books");
		return bookService.retrieveAllBooksFromAuthor();
		
	}
	
	@GetMapping("author/{authorId}/book/{bookId}")
	public BookData retrieveBookById(@PathVariable Long authorId, @PathVariable Long bookId) {
		log.info("Retrieving book with ID={}", bookId);
		return bookService.retrieveBookById(authorId, bookId);
	}

	@DeleteMapping("author/{authorId}/book/{bookId}")
	public Map<String, String> deleteBookById(@PathVariable Long authorId, @PathVariable Long bookId) {
		log.info("Deleting book with ID={} from author with ID= {}", bookId, authorId);
		bookService.deleteBookById(authorId, bookId);
		
		return Map.of("message", "Book with ID =" + bookId + " has been deleted");
	}
		
	
	@PostMapping("/author")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookAuthor newAuthor (@RequestBody BookAuthor bookAuthor) {
		log.info("Creating new author");
		
		return bookService.saveAuthor(bookAuthor);
				
	}
	
	@PutMapping("/author/{authorId}")
	public BookAuthor modifyAuthor(@PathVariable Long authorId, @RequestBody BookAuthor bookAuthor) {
		bookAuthor.setAuthorId(authorId);
		log.info("Updating author with ID={}", authorId);
		return bookService.saveAuthor(bookAuthor);
	}
	
	@GetMapping("/author")
	public List<BookAuthor> retrieveAllAuthors() {
		log.info("Retrieving all author information");
		return bookService.getAllAuthors();
	}
	
	@GetMapping("/author/{authorId}")
	public BookAuthor retrieveAuthorById(@PathVariable Long authorId) {
		log.info("Retrieving author with ID={}", authorId);
		return bookService.retrieveAuthorById(authorId);
	}
	
	@PostMapping("/author/{authorId}/book/{bookId}/genre")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BookGenre addGenre(@PathVariable Long authorId, @PathVariable Long bookId, @RequestBody BookGenre bookGenre) {
		log.info("Creating new genre");
		return bookService.saveGenre(authorId, bookId, bookGenre);
		
	}
	
	@PutMapping("/author/{authorId}/book/{bookId}/genre/{genreId}")
	public BookGenre modifyGenre(@PathVariable Long authorId, @PathVariable Long bookId, @PathVariable Long genreId, @RequestBody BookGenre bookGenre) {
		log.info("Modifying genre with ID+{}", genreId );
		return bookService.saveGenre(authorId, bookId, bookGenre);
	}
	
	@GetMapping("author/{authorId}/book/{bookId}/genre")
	public List<BookGenre> retrieveAllGenres(@PathVariable Long authorId, @PathVariable Long bookId) {
		log.info("Retrieving all genres");
		return bookService.retrieveAllGenres();
	}
}
