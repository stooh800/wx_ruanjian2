package org.fuck.weixin.library.service;

import org.fuck.weixin.library.domain.Book;
import org.fuck.weixin.library.domain.DebitList;
import org.springframework.data.domain.Page;

public interface LibraryService {

	Page<Book> search(String keyword, int pageNumber);

	void add(String id, DebitList debitList);

	void remove(String id, DebitList debitList);
	

}
