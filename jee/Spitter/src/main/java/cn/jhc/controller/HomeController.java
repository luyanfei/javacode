package cn.jhc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jhc.service.SpitterService;

@Controller
public class HomeController {
	
	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;

	private SpitterService spitterService;
	
	@Autowired
	public HomeController(SpitterService spitterService) {
		super();
		this.spitterService = spitterService;
	}

	@RequestMapping({"/","/home"})
	public String showHomePage(Map<String,Object> model) {
		model.put("spittles", 
				spitterService.getRecentSpittles(DEFAULT_SPITTLES_PER_PAGE));
		return "home";
	}
}
