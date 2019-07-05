package org.fuck.weixin.library.repository;

import org.fuck.weixin.library.domain.Borrow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, String> {

	Page<Borrow> findByDisabledFalse(Pageable pageable);
	
	

	  
}
