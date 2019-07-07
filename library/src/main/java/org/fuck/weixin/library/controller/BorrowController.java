package org.fuck.weixin.library.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.fuck.weixin.library.domain.Book;
import org.fuck.weixin.library.domain.Borrow;
import org.fuck.weixin.library.domain.DebitList;
import org.fuck.weixin.library.repository.BookRepository;
import org.fuck.weixin.library.repository.BorrowRepository;
import org.fuck.weixin.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/kemao_2/library/borrowdebit")
public class BorrowController {
    @Autowired
	private BorrowRepository borrowRepository;
    @Autowired
    private BookRepository bookRepository;
    
    @RequestMapping
	public String add(@RequestParam("id") String id, WebRequest request) {
		Date date=new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		String b_date=ft.format(date);
		Book book=bookRepository.getOne(String.valueOf(id));
		Borrow borrow=new Borrow();
		borrow.setName(book.getName());
		borrow.setImage(book.getImage());
		borrow.setBorrowtime(b_date);
		borrowRepository.save(borrow);
		
		List<Borrow> list=borrowRepository.findAll();
		request.setAttribute("borrowList", list, WebRequest.SCOPE_SESSION);
		return "/WEB-INF/views/library/debit/borrowlist.jsp";
}
}