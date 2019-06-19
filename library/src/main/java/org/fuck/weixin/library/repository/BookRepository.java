package org.fuck.weixin.library.repository;

import org.fuck.weixin.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
	Page<Book> findByDisabledFalse(Pageable pageable);

	Page<Book> findByNameContainingAndDisabledFalse(String keyword, Pageable pageable);

}
