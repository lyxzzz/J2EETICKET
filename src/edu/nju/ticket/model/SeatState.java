package edu.nju.ticket.model;

import java.io.Serializable;
import java.util.Map;

public class SeatState implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1184472719899050040L;
	
	private String planid;
	private String seatplace;
	private boolean state;
	private int type;
	private double price;
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getSeatplace() {
		return seatplace;
	}
	public void setSeatplace(String seatplace) {
		this.seatplace = seatplace;
	}
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public SeatState(String planid, String seatplace, boolean state, int type, double price) {
		super();
		this.planid = planid;
		this.seatplace = seatplace;
		this.state = state;
		this.type = type;
		this.price = price;
	}
	
	public SeatState(String seatplace, boolean state) {
		super();
		this.seatplace = seatplace;
		this.state = state;
	}
	public SeatState() {
		super();
		state=true;
		type=0;
	}
	public SeatState(Map<String,Object> map) {
		super();
		this.planid = String.valueOf(map.get("planid"));
		this.seatplace = String.valueOf(map.get("seatplace"));
		this.state = (int)map.get("state")==1;
		this.type = (int)map.get("type");
		this.price = (double)map.get("price");
	}
}
