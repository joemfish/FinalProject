package bookshelf.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bookshelf.controller.model.BookData;
import bookshelf.controller.model.BookData.BookAuthor;
import bookshelf.controller.model.BookData.BookGenre;
import bookshelf.dao.AuthorDao;
import bookshelf.dao.BookDao;
import bookshelf.entity.Author;
import bookshelf.entity.Book;
import bookshelf.entity.Genre;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	
	@Autowired
	AuthorDao authorDao;
	
	@Autowired
	GenreDao genreDao;

	@Transactional(readOnly = false)
	public BookData saveBook(Long authorId, BookData bookData) {
		Author author = findAuthorById(authorId);
		
		Set<Genre> genres = genreDao.findAllByGenreNameIn(bookData.getGenres());
		
		Long bookId = bookData.getBookId();
		Book book = findOrCreateBook(authorId, bookId);
		
		copyBookFields(book, bookData);
		
		book.setAuthor(author);
		author.getBooks().add(book);
		
		for(Genre genre : genres) {
			genre.getBooks().add(book);
			book.getGenres().add(genre);
		}
		
		return new BookData(bookDao.save(book));
	}

	private void copyBookFields(Book book, BookData bookData) {
		book.setBookId(bookData.getBookId());
		book.setBookName(bookData.getBookName());
		book.setReadOrNot(bookData.isReadOrNot());
		book.setOwnOrNot(bookData.isOwnOrNot());
		book.setRating(bookData.getRating());
		book.setYearPublished(bookData.getYearPublished());
		
		
	}

	private Book findOrCreateBook(Long authorId, Long bookId) {
		Book book;
		
		if(Objects.isNull(bookId)) {
			book = new Book();
		} else {
			book = findBookById(authorId, bookId);
		}
		
		return book;
		
	}

	private Book findBookById(Long authorId, Long bookId) {
		Book book = bookDao.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book with ID=" + bookId + " does not exist"));
		
		if (book.getAuthor().getAuthorId() != authorId) {
			throw new IllegalArgumentException("Book with ID=" + bookId + " is not written by that author with ID=" + authorId + " .");
		}
		
		return book;
	}

	@Transactional(readOnly = true)
	public List<BookData> retrieveAllBooksFromAuthor() {
		List<Book> books = bookDao.findAll();
		List<BookData> result = new LinkedList<BookData>();
		
		for(Book book : books) {
			BookData bd = new BookData(book);
			
			result.add(bd);
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public BookData retrieveBookById(Long authorId, Long bookId) {
		Book book = findBookById(authorId, bookId);
		
		return new BookData(book);
		
	}

	@Transactional(readOnly = false)
	public void deleteBookById(Long authorId, Long bookId) {
		Book book = findBookById(authorId, bookId);
		
		bookDao.delete(book);
	}

	@Transactional(readOnly = false)
	public BookAuthor saveAuthor(BookAuthor bookAuthor) {
			Long authorId = bookAuthor.getAuthorId();
			Author author = findOrCreateAuthor(authorId);
			
			copyAuthorFields(author, bookAuthor);
			
			return new BookAuthor(authorDao.save(author));
		
	}

	private void copyAuthorFields(Author author, BookAuthor bookAuthor) {
		author.setAuthorId(bookAuthor.getAuthorId());
		author.setAuthorName(bookAuthor.getAuthorName());
		
}

	private Author findOrCreateAuthor(Long authorId) {
		if(Objects.isNull(authorId)) {
			return new Author();
		} else {
			return findAuthorById(authorId);
		}
	}

	private Author findAuthorById(Long authorId) {
		return authorDao.findById(authorId).orElseThrow(() -> new NoSuchElementException("Author with ID= " + authorId + " does not exist"));
		
	}

	@Transactional(readOnly = true)
	public List<BookAuthor> getAllAuthors() {
		
		List<Author> authors = authorDao.findAll();
		List<BookAuthor> bookAuthors = new LinkedList<BookAuthor>();
		
		for(Author author : authors) {
			BookAuthor ba = new BookAuthor(author);
			
			bookAuthors.add(ba);
		}
		
		return bookAuthors;
	}

	@Transactional(readOnly = true)
	public BookAuthor retrieveAuthorById(Long authorId) {
		Author author = findAuthorById(authorId);
		
		return new BookAuthor(author);
	}

//	@Transactional(readOnly = false)
//	public BookGenre saveGenre(Long authorId, Long bookId, BookGenre bookGenre) {
//		Book book = findBookById(authorId, bookId);
//		Long genreId = bookGenre.getGenreId();
//		Genre genre = findOrCreateGenre(bookId, genreId);
//		copyGenreFields(genre, bookGenre);
//		
//		book.getGenres().add(genre);
//		genre.getBooks().add(book);
//		
//		return new BookGenre(genreDao.save(genre));
//		
//	}
//
//	private void copyGenreFields(Genre genre, BookGenre bookGenre) {
//			genre.setGenreId(bookGenre.getGenreId());
//			genre.setGenreName(bookGenre.getGenreName());
//	}
//
//	private Genre findOrCreateGenre(Long bookId, Long genreId) {
//		Genre genre;
//		
//		if(Objects.isNull(genreId)) {
//			genre = new Genre();
//		} else {
//			genre = findGenreById(bookId, genreId);
//		}
//		
//		return genre;
//	}
//
//	private Genre findGenreById(Long bookId, Long genreId) {
//		Genre genre = genreDao.findById(genreId).orElseThrow(() -> new NoSuchElementException("Genre with ID=" + genreId + " does not exist."));
//		
//		for (Book book : genre.getBooks()) {
//			if (book.getBookId().equals(bookId)) {
//				genre = new Genre();
//			} else {
//				throw new IllegalArgumentException("Genre with ID=" + genreId + " is not a genre for book with ID=" + bookId + " .");
//			}
//		}
//		
//		return genre;
//	}
//
	public List<BookGenre> retrieveAllGenres() {
		List<Genre> genres = genreDao.findAll();
		List<BookGenre> bookGenres = new LinkedList<BookGenre>();
		
		for (Genre genre : genres) {
			BookGenre bg = new BookGenre(genre);
			
			bookGenres.add(bg);
		}
		
		return bookGenres;
	}





}
