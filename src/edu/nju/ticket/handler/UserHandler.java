package edu.nju.ticket.handler;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.nju.ticket.common.AccountType;
import edu.nju.ticket.model.User;
import edu.nju.ticket.utils.CookiesMap;

public class UserHandler  implements HandlerInterceptor{
	@Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {
         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {
    	String url = request.getServletPath();
    	System.out.println(url);
    	System.out.println("handler3");
		HttpSession session=request.getSession(false);
		if(session==null)
		{
			//initSession(session,request,response);
			System.out.println("null session");
		}
		else
		{
			CookiesMap map=new CookiesMap(request.getCookies());
			HashMap<String,String> cookies=map.getMap();
			for(Entry<String,String> entry:cookies.entrySet())
			{
				if(entry.getKey().equals("type"))
				{
					if(!entry.getValue().equals(AccountType.user.name()))
					{
						//initSession(session,request,response);
						System.out.println("handler");
						return false;
					}
				}
				else
				{
					if(!entry.getValue().equals(session.getAttribute(entry.getKey())))
					{
						//initSession(session,request,response);
						System.out.println("handler");
						return false;
					}
				}
			}
			System.out.println("has session");
		}
		return true;
    }  
    
    public static void initSession(HttpSession session,HttpServletRequest request, HttpServletResponse response,User user)
    {
    	session=request.getSession();
    	session.setMaxInactiveInterval(1*24*60);
		String key=UUID.randomUUID().toString().replace("-", "");
		Cookie keycookie = new Cookie("key",key);   // 新建Cookie
		keycookie.setMaxAge(Integer.MAX_VALUE);           // 设置生命周期为MAX_VALUE
		response.addCookie(keycookie);
		Cookie typecookie = new Cookie("type",AccountType.manager.name());   // 新建Cookie
		typecookie.setMaxAge(Integer.MAX_VALUE);           // 设置生命周期为MAX_VALUE
		response.addCookie(typecookie);
		session.setAttribute("JSESSIONID", session.getId());
		session.setAttribute("key", key);
		session.setAttribute("type", AccountType.manager.name());
		session.setAttribute("object", user);
    }
}
