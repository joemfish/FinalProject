package bookshelf.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import bookshelf.entity.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {

}
