package edu.nju.ticket.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Member {
	private static Member singleton;
	private List<Integer> memberlevel;
	private static final String memberprop="memberlevel.properties";
	private Member()
	{
		memberlevel=new ArrayList<Integer>();
		Properties prop=new Properties();
		try {
			System.out.println(Config.getInstance().get("ConfigPath")+memberprop);
			InputStream in=new FileInputStream(Config.getInstance().get("ConfigPath")+memberprop);
			prop.load(in);
			int key=0;
			int value=0;
			do {
				memberlevel.add(value);
				key++;
				value=Integer.parseInt((String) prop.getOrDefault(String.valueOf(key), "-1"));
				System.out.println(memberlevel.size()+":"+value);
			}while(value>=0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getMemberLevel(int value)
	{
		int result=0;
		for(int i=0;i<memberlevel.size();i++)
		{
			if(value<memberlevel.get(i))
			{
				break;
			}
			else
			{
				result++;
			}
		}
		return result;
	}
	public static Member getInstance()
	{
		if(singleton==null) singleton=new Member();
		return singleton;
	}
}
