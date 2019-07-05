package org.fuck.weixin.library.service.impl;

import java.util.LinkedList;

import org.fuck.weixin.library.domain.Book;
import org.fuck.weixin.library.domain.Borrow;
import org.fuck.weixin.library.domain.DebitList;
import org.fuck.weixin.library.repository.BorrowRepository;
import org.fuck.weixin.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;

public class BorrowServiceImpl implements BorrowService {
    @Autowired
	private BorrowRepository borrowRepository;
	@Override
	public void save(String id, DebitList debitList) {
		 if(debitList.getBorrow()==null) {
       	  debitList.setBorrow(new LinkedList<>());
         }
		boolean exists=false;          
           for(Borrow b:debitList.getBorrow()) {
           	if(b.getId().equals(id)){
           	exists=true;
           	break;
           }
		
	}
	if(!exists) {
		this.borrowRepository.findById(id).ifPresent(book ->{
			debitList.getBorrow().add(book);
		});
		
	}

}
	}
