package edu.nju.ticket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.nju.ticket.common.OrderState;
import edu.nju.ticket.common.SeatType;

public class PlanSeatInfo {
	private ArrayList<ArrayList<SeatState>> seatinfo;
	private ArrayList<Order> unassuredorderlist;
	private ArrayList<SeatState> inserttype;
	private List<Integer> columnlist;
	private List<Integer> rowlist;
	public ArrayList<ArrayList<SeatState>> getSeatinfo() {
		return seatinfo;
	}
	public void setSeatinfo(ArrayList<ArrayList<SeatState>> seatinfo) {
		this.seatinfo = seatinfo;
	}
	public PlanSeatInfo(SeatInfo in)
	{
		this.columnlist=new ArrayList<Integer>(SeatType.getInstance().getMaxSeatType());
		this.rowlist=new ArrayList<Integer>(SeatType.getInstance().getMaxSeatType());
		for(int i=0;i<SeatType.getInstance().getMaxSeatType();i++)
		{
			this.columnlist.set(i, 0);
			this.rowlist.set(i, 0);
		}
		ArrayList<ArrayList<Boolean>> info=in.getSeatinfo();
		unassuredorderlist=new ArrayList<Order>();
		for(int i=0;i<info.size();i++)
		{
			ArrayList<SeatState> temp=new ArrayList<SeatState>(info.get(i).size());
			seatinfo.add(temp);
		}
	}
	public void pushSeat(Map<String,Object> map)
	{
		SeatState state=new SeatState(map);
		String[] str=state.getSeatplace().split(":");
		int row=Integer.valueOf(str[0])-1;
		int column=Integer.valueOf(str[1])-1;
		if(seatinfo.size()>row)
		{
			if(seatinfo.get(row).size()>column)
			{
				seatinfo.get(row).set(column, state);
			}
		}
	}
	public void chooseSeat(SeatState state)
	{
		String[] str=state.getSeatplace().split(":");
		int row=Integer.valueOf(str[0])-1;
		int column=Integer.valueOf(str[1])-1;
		if(seatinfo.size()>row)
		{
			if(seatinfo.get(row).size()>column)
			{
				seatinfo.get(row).get(column).setState(state.getState());
			}
		}
	}
	public int size()
	{
		int result=0;
		for(int i=0;i<seatinfo.size();i++)
		{
			result=result+seatinfo.get(i).size();
		}
		return result;
	}
	public SeatState get(int index)
	{
		if(inserttype==null) init();
		return inserttype.get(index);
	}
	private void init()
	{
		for(int i=0;i<seatinfo.size();i++)
		{
			inserttype.addAll(seatinfo.get(i));
		}
	}
	public ArrayList<Order> getUnassuredorderlist() {
		return unassuredorderlist;
	}
	public void setUnassuredorderlist(ArrayList<Order> unassuredorderlist) {
		this.unassuredorderlist = unassuredorderlist;
	}
	public void pushUnassuredorderlist(List<Order> orderlist)
	{
		this.unassuredorderlist.addAll(orderlist);
	}
	public List<Order> distributeSeats()
	{
		List<Order> resultlist=new ArrayList<Order>();
		for(int index=0;index<this.unassuredorderlist.size();index++)
		{
			Order temporder=this.unassuredorderlist.get(index);
			this.distributeSeat(temporder);
			resultlist.add(temporder);
		}
		this.unassuredorderlist=new ArrayList<Order>();
		return resultlist;
	}
	public void distributeSeat(Order temporder)
	{
		int seattype=temporder.getSeattype()-1;
		int row=rowlist.get(seattype);
		int column=columnlist.get(seattype);
		while(row<this.seatinfo.size())
		{
			if(column<this.seatinfo.get(row).size())
			{
				if(this.seatinfo.get(row).get(column).getState()&&this.seatinfo.get(row).get(column).getType()==seattype)
				{
					String place=String.valueOf(row+1)+":"+String.valueOf(column+1);
					temporder.setSeatplace(place);
					temporder.setState(OrderState.distributed.toInt());
					break;
				}
				else
				{
					column++;
				}
			}
			else
			{
				column=0;
				row++;
			}
		}
		rowlist.set(seattype, row);
		columnlist.set(seattype, column);
		if(temporder.getState()!=OrderState.distributed.toInt())
		{
			temporder.setState(OrderState.lackseat.toInt());
		}
	}
}
