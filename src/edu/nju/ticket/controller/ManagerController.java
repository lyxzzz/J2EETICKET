package edu.nju.ticket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nju.ticket.model.Yard;
import edu.nju.ticket.service.ManagerService;


@Controller //@Controller用于标注控制层组件(如struts中的action)
@RequestMapping(value="/manager")
public class ManagerController {
	@Autowired
	private ManagerService managerService; 
	
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    protected Map<String,String> doSignin(
    		@RequestParam(value="email", required=true, defaultValue="151250100@smail.nju.edu.cn") String email,
    		@RequestParam(value="password", required=true, defaultValue="a") String password,
    		ModelMap model, 
    		HttpServletRequest request,
    		HttpServletResponse response
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
        map.put("name", managerService.signIn(email,password, request.getSession(false), request, response).name());
		return map;
	}
    
    @RequestMapping(value = "/uncheckedlist")
    @ResponseBody
    protected Map<String,String> getUncheckedList(
    		ModelMap model, 
    		HttpServletRequest request
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
        List<Yard> list=managerService.findUncheckedYard();
        for(int i=0;i<list.size();i++)
        {
        	map.put(String.valueOf(i), list.get(i).toString());
        }
		return map;
	}
    
    @RequestMapping(value = "/operateyard")
    @ResponseBody
    protected Map<String,String> operateYard(
    		@RequestParam(value="operate", required=true, defaultValue="true") String operate,
    		@RequestParam(value="yardid") String yardid,
    		ModelMap model, 
    		HttpServletRequest request
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
    	map.put("name", managerService.checkYard(Boolean.parseBoolean(operate), yardid)?"true":"false");
		return map;
	}
    
    @RequestMapping(value = "/revisedlist")
    @ResponseBody
    protected Map<String,String> getRevisedList(
    		ModelMap model, 
    		HttpServletRequest request
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
        List<Yard> list=managerService.findReviseYard();
        for(int i=0;i<list.size();i++)
        {
        	map.put(String.valueOf(i), list.get(i).toString());
        }
		return map;
	}
    
    @RequestMapping(value = "/operaterevise")
    @ResponseBody
    protected Map<String,String> operateRevise(
    		@RequestParam(value="operate", required=true, defaultValue="true") String operate,
    		@RequestParam(value="yardid") String yardid,
    		ModelMap model, 
    		HttpServletRequest request
    		) 
    {
    	HashMap<String,String> map=new HashMap<String,String>();
    	map.put("name", managerService.checkRevise(Boolean.parseBoolean(operate), yardid)?"true":"false");
		return map;
	}
}
