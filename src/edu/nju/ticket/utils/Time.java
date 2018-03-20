package edu.nju.ticket.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class Time {
	public final long distribute_time=7*24*60;
	private static Time singleton;
	private Time()
	{
		
	}
	public static Time getInstance()
	{
		if(singleton==null) singleton=new Time();
		return singleton;
	}
	public boolean between(LocalDateTime t1,LocalDateTime t2,LocalDateTime start,LocalDateTime end)
	{
		long distance=Duration.between(start,t1).toNanos();
		if(distance<0)
		{
			distance=Duration.between(start, t2).toNanos();
			if(distance>=0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			distance=Duration.between(t1,end).toNanos();
			if(distance>=0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
