package edu.nju.ticket.model;

import java.io.Serializable;
import java.util.Map;

public class Venue implements Serializable{

	public Venue() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4844486428669225974L;

	private String venueid;
	
	private String venuename;
	
	private String location;
	
	private String seatinfopath;
	
	private SeatInfo seatinfo;
	
	private String yardid;

	public Venue(String venueid, String venuename, String location, String seatinfopath, String yardid) {
		super();
		this.venueid = venueid;
		this.venuename = venuename;
		this.location = location;
		this.seatinfopath = seatinfopath;
		this.yardid = yardid;
	}
	public Venue(Map<String, Object> map) {
		this.venueid = String.valueOf(map.get("venueid"));
		this.venuename = String.valueOf(map.get("venuename"));
		this.location = String.valueOf(map.get("location"));
		this.seatinfopath = String.valueOf(map.get("seatinfopath"));
		this.yardid = String.valueOf(map.get("yardid"));
	}


	public String getVenueid() {
		return venueid;
	}

	public void setVenueid(String venueid) {
		this.venueid = venueid;
	}

	public String getVenuename() {
		return venuename;
	}

	public void setVenuename(String venuename) {
		this.venuename = venuename;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSeatinfopath() {
		return seatinfopath;
	}

	public void setSeatinfopath(String seatinfopath) {
		this.seatinfopath = seatinfopath;
	}

	public String getYardid() {
		return yardid;
	}

	public void setYardid(String yardid) {
		this.yardid = yardid;
	}
	public SeatInfo getSeatinfo() {
		return seatinfo;
	}
	public void setSeatinfo(SeatInfo seatinfo) {
		this.seatinfo = seatinfo;
	}
}
