package com.taiji.manager.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.dom.Macro;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.manager.dto.UserDto;
import com.taiji.manager.entity.User;
import com.taiji.manager.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	//method=RequestMethod.POST
	
	@GetMapping("toShow")
	public String getUser() {
		return "userList";
	}
	
	@PostMapping("/getPage")
	@ResponseBody
	//public Map getPage(String models) {	
	public Map getPage(@RequestParam(value="limit",defaultValue="0")int limit, @RequestParam(value="offset",defaultValue="10")int offset, String departmentname, String statu,HttpServletRequest request) {
		//String json = "{\"page\":0,\"pageSize\":10,\"filter\":{\"filters\":[{\"field\":\"loginName\",\"value\":\"sue\"},{\"field\":\"userName\",\"value\":\"sd\"}],\"logic\":\"and\"},\"sort\":[{\"field\":\"loginName\",\"dir\":\"asc\"},{\"field\":\"email\",\"dir\":\"asc\"}]}";
//		System.out.println(models);
//		Map searchParameters = new HashMap<String,String>();
		Map userPage = new HashMap<>();
//		HashMap<String,String> filter = new HashMap<String,String>();
//		try {
//			searchParameters = objectMapper.readValue(models,new TypeReference<Map>() {});
//			int page = (int) searchParameters.get("page");
//			System.out.println(page);
//			int pageSize = (int) searchParameters.get("pageSize");
//			System.out.println(pageSize);
//			
//			//filter = (HashMap<String, String>) searchParameters.get("filter");
			
			userPage = userService.getPage(limit-1, offset, null,null);
//			userPage = userService.getPage(searchParameters, null);
//			System.out.println(userPage);
//			
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
		
		return userPage;
	}
	
	@PostMapping("/saveUser")
	public String saveUser(UserDto userDto) {
		System.out.println(userDto);
		
		userService.save(userDto);
		
		return "userList";
	}
	
	@GetMapping("/getUser")
	@ResponseBody
	public UserDto updateUser(String id) {
		System.out.println("getUser+++++++");
		UserDto updateUser = userService.findById(id);
		
		return updateUser;
	}
	
}
