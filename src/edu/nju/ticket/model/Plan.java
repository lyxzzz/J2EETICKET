package edu.nju.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import edu.nju.ticket.common.PlanType;

public class Plan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String planid;
	
	private String yardid;
	
	private String venueid;
	
	private LocalDateTime startdate;
	
	private LocalDateTime enddate;
	
	private String seatinfopath;
	
	private PlanType type;
	
	private String description;
	
	private double income;
	
	private boolean completed;
	
	private PlanSeatInfo state;

	public Plan()
	{
		
	}
	
	public Plan(String planid, String yardid, String venueid, LocalDateTime startdate, LocalDateTime enddate,
			String seatinfopath, PlanType type, String description) {
		super();
		this.planid = planid;
		this.yardid = yardid;
		this.venueid = venueid;
		this.startdate = startdate;
		this.enddate = enddate;
		this.seatinfopath = seatinfopath;
		this.type = type;
		this.description = description;
		this.income=0.0;
		this.completed=false;
	}

	public Plan(Map<String,Object> map) {
		super();
		this.planid = String.valueOf(map.get("planid"));
		this.yardid = String.valueOf(map.get("yardid"));
		this.venueid = String.valueOf(map.get("venueid"));
		Timestamp t1=(Timestamp)map.getOrDefault("startdate",new Timestamp(System.currentTimeMillis()));
		this.startdate = t1.toLocalDateTime();
		Timestamp t2=(Timestamp)map.getOrDefault("enddate",new Timestamp(System.currentTimeMillis()));
		this.enddate = t2.toLocalDateTime();
		this.seatinfopath = String.valueOf(map.get("seatinfopath"));
		this.type = PlanType.valueOf(String.valueOf(map.get("type")));
		this.description = String.valueOf(map.get("description"));
		this.income = (int)map.get("income");
		this.completed = (int)map.get("completed") == 1;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getYardid() {
		return yardid;
	}

	public void setYardid(String yardid) {
		this.yardid = yardid;
	}

	public String getVenueid() {
		return venueid;
	}

	public void setVenueid(String venueid) {
		this.venueid = venueid;
	}

	public LocalDateTime getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDateTime startdate) {
		this.startdate = startdate;
	}

	public LocalDateTime getEnddate() {
		return enddate;
	}

	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}

	public String getSeatinfopath() {
		return seatinfopath;
	}

	public void setSeatinfopath(String seatinfopath) {
		this.seatinfopath = seatinfopath;
	}

	public PlanType getType() {
		return type;
	}

	public void setType(PlanType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public PlanSeatInfo getState() {
		return state;
	}

	public void setState(PlanSeatInfo state) {
		this.state = state;
	}
	
	public void flushSeatInfo(SeatInfo info)
	{
		state=new PlanSeatInfo(info);
	}
	
	public void pushSeat(Map<String,Object> map)
	{
		state.pushSeat(map);
	}
	
}
