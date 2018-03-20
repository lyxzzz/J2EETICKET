package edu.nju.ticket.utils;

import java.util.HashMap;

import javax.servlet.http.Cookie;

public class CookiesMap {
	private HashMap<String,String> map;
	public CookiesMap(Cookie[] cookies)
	{
		map=new HashMap<String,String>();
		for(Cookie cookie:cookies)
		{
			map.put(cookie.getName(), cookie.getValue());
		}
	}
	public HashMap<String, String> getMap() {
		return map;
	}
}
