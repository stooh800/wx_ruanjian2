package org.fuck.weixin.library.controller;

import org.fuck.weixin.library.domain.DebitList;
import org.fuck.weixin.library.service.BorrowService;
import org.fuck.weixin.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/kemao_2/library/debit")
// @SessionAttributes表示哪些属性是放入Session里面的
@SessionAttributes({ "debitList" })
public class DebitController {

	@Autowired
	private LibraryService libraryService;

	@RequestMapping
	public String add(@RequestParam("id") String id, WebRequest request) {
		// 获取Session里面名为debitList的对象，并且强制转换为DebitList
		DebitList debitList = (DebitList) request.getAttribute("debitList", WebRequest.SCOPE_SESSION);
		if (debitList == null) {
			debitList = new DebitList();
			// 把新创建的DebitList放入Session里面
			request.setAttribute("debitList", debitList, WebRequest.SCOPE_SESSION);
		}

		libraryService.add(id, debitList);

		return "redirect:/kemao_2/library/debit/list";
	}

	@RequestMapping("list")
	public String list() {
		return "/WEB-INF/views/library/debit/list.jsp";
	}

	// 获取路径参数需要使用@PathVariable注解
	@RequestMapping("remove/{id}")
	public String remove(@PathVariable("id") String id,
			// 从Session里面获取名为debitList的对象
			@SessionAttribute(name = "debitList") DebitList debitList) {
		this.libraryService.remove(id, debitList);
		return "redirect:/kemao_2/library/debit/list";
	}
}
