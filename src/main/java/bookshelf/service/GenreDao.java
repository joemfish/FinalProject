package bookshelf.service;

import org.springframework.data.jpa.repository.JpaRepository;

import bookshelf.entity.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
