package edu.nju.ticket.utils;

import java.util.HashMap;

public class Config {
	private static Config singleton;
	private HashMap<String,String> map;
	private Config()
	{
		map=new HashMap<String,String>();
		map.put("ConfigPath", "C:\\Files\\Code\\Java\\myweb\\WebContent\\config\\");
	}
	protected static Config getInstance()
	{
		if(singleton==null) singleton=new Config();
		return singleton;
	}
	public String get(String key)
	{
		return map.getOrDefault(key, "");
	}
}
