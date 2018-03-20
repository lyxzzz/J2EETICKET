package edu.nju.ticket.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeatInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8878055598989199541L;
	
	private ArrayList<ArrayList<Boolean>> seatinfo;
	
	public SeatInfo()
	{
		
	}
	
	public ArrayList<ArrayList<Boolean>> getSeatinfo() {
		return seatinfo;
	}

	public void setSeatinfo(String seatinfopath) {
		seatinfo=new ArrayList<ArrayList<Boolean>>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(seatinfopath));
			while(br.ready())
			{
				ArrayList<Boolean> temp=new ArrayList<Boolean>();
				String str=br.readLine();
				for(int i=0;i<str.length();i++)
				{
					char state=str.charAt(i);
					if(Character.isDigit(state))
					{
						temp.add(state=='1');
					}
				}
				seatinfo.add(temp);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSeatinfo(List<String> seatinfo) {
		this.seatinfo=new ArrayList<ArrayList<Boolean>>();
		for(int i=0;i<seatinfo.size();i++)
		{
			String str=seatinfo.get(i);
			ArrayList<Boolean> temp=new ArrayList<Boolean>();
			for(int index=0;index<str.length();index++)
			{
				char state=str.charAt(i);
				if(Character.isDigit(state))
				{
					temp.add(state=='1');
				}
			}
			this.seatinfo.add(temp);
		}
	}

	public SeatInfo(String seatinfopath)
	{
		setSeatinfo(seatinfopath);
	}
	
	public SeatInfo(List<String> seatinfo)
	{
		setSeatinfo(seatinfo);
	}
	
	public String toString()
	{
		String result="";
		if(seatinfo.size()==0)
		{
			return result;
		}
		result=translate(seatinfo.get(0));
		for(int i=1;i<seatinfo.size();i++)
		{
			result=result+"\n"+translate(seatinfo.get(i));
		}
		return result;
	}
	
	private String translate(ArrayList<Boolean> value)
	{
		String result="";
		for(int i=0;i<value.size();i++)
		{
			result+=value.get(i)?'1':'_';
		}
		return result;
	}
	
}
