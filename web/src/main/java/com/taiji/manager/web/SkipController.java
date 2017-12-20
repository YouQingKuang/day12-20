package com.taiji.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taiji.manager.dto.MenuDto;
import com.taiji.manager.dto.TreeDto;
import com.taiji.manager.service.MenuService;

@Controller
public class SkipController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("login")
	public String test(Model medol) {
		return "login";
	}
	
	@GetMapping("/")
	public String toIndex(HttpServletRequest request) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		String password = userDetails.getPassword();
		System.out.println(username+" "+ password);
		return "list";
	}
	
	@GetMapping("/toTree")
	public String toShow() {
		return "showtree";
	} 
	
	@RequestMapping("/getTree")
	@ResponseBody
	public List<TreeDto> getTree() {
		//System.out.println("getTree++++");
		List<TreeDto> findAllList = menuService.findAllList("0");
		//System.out.println("---"+findAllList);
		return findAllList;
	}
}
