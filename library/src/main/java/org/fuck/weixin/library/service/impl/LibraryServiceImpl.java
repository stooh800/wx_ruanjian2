package org.fuck.weixin.library.service.impl;

import java.util.LinkedList;
import java.util.function.Predicate;

import org.fuck.weixin.library.domain.Book;
import org.fuck.weixin.library.domain.DebitList;
import org.fuck.weixin.library.repository.BookRepository;
import org.fuck.weixin.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Page<Book> search(String keyword, int pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber, 10);

		Page<Book> page;
		if (StringUtils.isEmpty(keyword)) {
			// 没有查询的关键字
			// where disabled == false
			page = this.bookRepository.findByDisabledFalse(pageable);
		} else {
			// 有关键字，要根据关键字来查询数据
			// where name like '%' + keyword + '%' and disabled == false
			page = this.bookRepository.findByNameContainingAndDisabledFalse(keyword, pageable);
		}

		return page;
	}

	@Override
	public void add(String id, DebitList debitList) {
          if(debitList.getBooks()==null) {
        	  debitList.setBooks(new LinkedList<>());
          }
		boolean exists=false;          
            for(Book b:debitList.getBooks()) {
            	if(b.getId().equals(id)){
            	exists=true;
            	break;
            }
		
	}
	if(!exists) {
		this.bookRepository.findById(id).ifPresent(book ->{
			debitList.getBooks().add(book);
		});
		
	}
}

	@Override
	public void remove(String id, DebitList debitList) {
		 debitList.getBooks().stream().filter(book -> book.getId().equals(id)).findFirst().ifPresent(debitList.getBooks()::remove);;
		
	}
}