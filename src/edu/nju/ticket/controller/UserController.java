package edu.nju.ticket.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.ticket.model.User;
import edu.nju.ticket.service.UserService;

@Controller //@Controller用于标注控制层组件(如struts中的action)
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
    @RequestMapping(value = "/signin")
    @ResponseBody
    protected Map<String,String> doSignin(
    		@RequestParam(value="email", required=true, defaultValue="151250100@smail.nju.edu.cn") String email,
    		@RequestParam(value="password", required=true, defaultValue="a") String password,
    		ModelMap model, 
    		HttpServletRequest request
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
        map.put("name", userService.signIn(email,password).name());
		return map;
	}
    
    @RequestMapping(value = "/signup")
    @ResponseBody
    protected Map<String,String> doSignup(
    		@RequestParam(value="email", required=true, defaultValue="151250100@smail.nju.edu.cn") String email,
    		@RequestParam(value="password", required=true, defaultValue="a") String password,
    		ModelMap model, 
    		HttpServletRequest request) {
		// TODO Auto-generated method stub
    	HashMap<String,String> map=new HashMap<String,String>();
        map.put("name", userService.signUp(new User(email,password))?"true":"false");
		return map;
	}
    
    @RequestMapping(value="/checksignup/{userid}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,String> checkSignup(@PathVariable String userid) {
            
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("name", userService.checkSignUp(userid)?"true":"false");
		return map;
    
    }
    
    @RequestMapping(value = "/reviseinfo")
    @ResponseBody
    protected Map<String,String> reviseinfo(
    		@RequestParam(value="password") String password,
    		ModelMap model, 
    		HttpServletRequest request) {
		// TODO Auto-generated method stub
    	HttpSession session=request.getSession();
    	User temp=new User((User) session.getAttribute("object"));
    	temp.setPassword(password);
    	userService.reviseInfo(temp);
    	HashMap<String,String> map=new HashMap<String,String>();
		return map;
	}
}
