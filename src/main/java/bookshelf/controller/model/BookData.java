package bookshelf.controller.model;

import java.util.HashSet;
import java.util.Set;

import bookshelf.entity.Author;
import bookshelf.entity.Book;
import bookshelf.entity.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookData {

	private Long bookId;
	private String bookName;
	private boolean readOrNot;
	private boolean ownOrNot;
	private int rating;
	private String yearPublished;

//	private BookAuthor bookAuthor;
	
	private Set<String> genres = new HashSet<>();
	
	public BookData(Book book) {
		bookId = book.getBookId();
		bookName = book.getBookName();
		readOrNot = book.isReadOrNot();
		ownOrNot = book.isOwnOrNot();
		rating = book.getRating();
		yearPublished = book.getYearPublished();
		
		for(Genre genre : book.getGenres()) {
			genres.add(genre.getGenreName());
		}
		
		
	}
	
	
	
	@Data
	@NoArgsConstructor
	public static class BookAuthor {
		private Long authorId;
		private String authorName;
		
		Set<BookData> books = new HashSet<>();
		
	public BookAuthor(Author author) {
		authorId = author.getAuthorId();
		authorName = author.getAuthorName();
		
		for(Book book : author.getBooks() ) {
			books.add(new BookData(book));
		}
	}
}

	@Data
	@NoArgsConstructor
	public static class BookGenre {
		
		private Long genreId;
		private String genreName;
		
		Set<BookData> books = new HashSet<>();
		
	public BookGenre(Genre genre) {
		genreId = genre.getGenreId();
		genreName = genre.getGenreName();
		
		for(Book book : genre.getBooks()) {
			
			books.add(new BookData(book));
			
		}
			
		}
	}
	}

	

