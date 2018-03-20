package edu.nju.ticket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.ticket.model.User;
import edu.nju.ticket.service.UserService;
import edu.nju.ticket.utils.Email;
import edu.nju.ticket.utils.Member;

@Controller
public class MainController {

	@Autowired
	private UserService userService;
	

	@Autowired
	private UserService yardService;
	
	
	@RequestMapping(value = "/home1")
    protected String test1(ModelMap model) {
		for(int i=-500;i<10000;i+=1000)
		{
			System.out.println(Member.getInstance().getMemberLevel(i));
		}
		return "/user/test.jsp";
    }
	@RequestMapping(value = "/home2")
    protected String test2(ModelMap model) {
    	model.addAttribute("text",userService.signIn("ab","cde").name()+";"+userService.signIn("abc","bcd").name()+";"+userService.signIn("abc","cd").name());
        //System.out.println(userService.signUp(new User("cde","bcd")));
		return "/user/test.jsp";
    }
	
    @RequestMapping(value = "/signup")
    @ResponseBody
    protected Map<String,String> doSignup(
    		@RequestParam(value="email", required=true, defaultValue="adf") String email,
    		@RequestParam(value="password", required=true, defaultValue="bcd") String password,
    		ModelMap model, 
    		HttpServletRequest request) {
		// TODO Auto-generated method stub
    	HashMap<String,String> map=new HashMap<String,String>();
        map.put("name", userService.signUp(new User(email,password))?"true":"false");
		return map;
	}
	
}
