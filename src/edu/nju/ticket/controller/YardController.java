package edu.nju.ticket.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.ticket.model.Yard;
import edu.nju.ticket.service.YardService;


@Controller //@Controller用于标注控制层组件(如struts中的action)
@RequestMapping(value="/yard")
public class YardController {
	@Autowired
	private YardService yardService; 
	
	
    @RequestMapping(value = "/signin")
    @ResponseBody
    protected Map<String,String> doSignin(
    		@RequestParam(value="email", required=true, defaultValue="151250100@smail.nju.edu.cn") String email,
    		@RequestParam(value="password", required=true, defaultValue="a") String password,
    		ModelMap model, 
    		HttpServletRequest request,
    		HttpServletResponse response
    		) 
    {
    	yardService.signIn(email,password, request.getSession(false), request, response);
    	HashMap<String,String> map=new HashMap<String,String>();
        map.put("name", "true");
		return map;
	}
    
    @RequestMapping(value = "/signup")
    @ResponseBody
    protected Map<String,String> doSignup(
    		@RequestParam(value="yardname", required=true, defaultValue="戏剧院") String yardname,
    		@RequestParam(value="password", required=true, defaultValue="a") String password,
    		@RequestParam(value="address", required=true, defaultValue="南京市鼓楼区南京大学") String address,
    		@RequestParam(value="email", required=true, defaultValue="151250100@smail.nju.edu.cn") String email,
    		@RequestParam(value="payacount", required=true, defaultValue="15214338969") String payacount,
    		ModelMap model, 
    		HttpServletRequest request) {
		// TODO Auto-generated method stub
    	yardService.signUp(new Yard(yardname, password, address, email,payacount));
    	HashMap<String,String> map=new HashMap<String,String>();
		return map;
	}
    
    @RequestMapping(value = "/reviseinfo")
    @ResponseBody
    protected Map<String,String> reviseinfo(
    		@RequestParam(value="yardname", required=true, defaultValue="戏剧院") String yardname,
    		@RequestParam(value="address", required=true, defaultValue="南京市鼓楼区南京大学") String address,
    		@RequestParam(value="payacount", required=true, defaultValue="15214338969") String payacount,
    		ModelMap model, 
    		HttpServletRequest request) {
		// TODO Auto-generated method stub
    	HttpSession session=request.getSession();
    	Yard temp=new Yard((Yard) session.getAttribute("object"));
    	temp.setYardname(yardname);
    	temp.setAddress(address);
    	temp.setPayacount(payacount);
    	yardService.reviseInfo(temp.getYardid(), temp);
    	HashMap<String,String> map=new HashMap<String,String>();
		return map;
	}
}
