package org.fuck.weixin.menu.controller;

import org.fuck.weixin.menu.domain.SelfMenu;
import org.fuck.weixin.menu.service.SelfMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/kemao_2/menu")
public class SelfMenuController {

	@Autowired
	private SelfMenuService menuService;

	@GetMapping
	public ModelAndView index() {
		return new ModelAndView("/WEB-INF/views/kemao_2/menu/index.jsp");
	}

	@GetMapping(produces = "application/json") // 表明对外提供JSON数据
	@ResponseBody 
	// 此时方法的返回值可以是任意类型的数据，Spring会自动转换为JSON
	public SelfMenu data() {
		return menuService.getMenu();
	}

	@PostMapping
	@ResponseBody
	// @RequestBody的作用：把整个请求体转换为Java对象
	public String save(@RequestBody SelfMenu selfMenu) {
		this.menuService.save(selfMenu);
		return "保存成功";
	}
}
