package edu.nju.ticket.common;

public class SeatType {
	private static SeatType singleton;
	private int max;
	private int min;
	public static SeatType getInstance()
	{
		if(singleton==null) singleton=new SeatType();
		return singleton;
	}
	private SeatType()
	{
		max=5;
		min=1;
	}
	public int getMaxSeatType()
	{
		return max;
	}
	public int getMinSeatType()
	{
		return min;
	}
	public boolean isLegalSeatType(int typenum)
	{
		return min<=typenum && typenum <= max;
	}
}
