package edu.nju.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7560260727100494020L;
	
	private String orderid;
	private String userid;
	private String planid;
	private LocalDateTime createtime;
	private double price;
	private double coupon;
	private String seatplace;
	private int state;
	private int seattype;
	
	public Order() {
		super();
	}
	public Order(String orderid, String userid, String planid, LocalDateTime createtime, double price, double coupon,
			String seatplace, int state, int seattype) {
		super();
		this.orderid = orderid;
		this.userid = userid;
		this.planid = planid;
		this.createtime = createtime;
		this.price = price;
		this.coupon = coupon;
		this.seatplace = seatplace;
		this.state = state;
		this.seattype=seattype;
	}
	public Order(Map<String, Object> map) {
		this.orderid = String.valueOf(map.get("orderid"));
		this.userid = String.valueOf(map.get("userid"));
		this.planid = String.valueOf(map.get("planid"));
		Timestamp t=(Timestamp)map.get("createtime");
		this.createtime = t.toLocalDateTime();
		this.price = (double)map.get("price");
		this.coupon = (double)map.get("coupon");
		this.seatplace = String.valueOf(map.get("seatplace"));
		this.state = (int)map.get("state");
		this.seattype = (int)map.get("seattype");
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public LocalDateTime getCreatetime() {
		return createtime;
	}
	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public String getSeatplace() {
		return seatplace;
	}
	public void setSeatplace(String seatplace) {
		this.seatplace = seatplace;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getSeattype() {
		return seattype;
	}
	public void setSeattype(int seattype) {
		this.seattype = seattype;
	}
	
}
