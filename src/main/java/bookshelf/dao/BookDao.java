package bookshelf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookshelf.entity.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}
