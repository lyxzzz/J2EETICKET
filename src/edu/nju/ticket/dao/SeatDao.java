package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.SeatInfo;
import edu.nju.ticket.model.SeatState;
import edu.nju.ticket.model.Venue;

public interface SeatDao {
	public void saveSeatInfo(Venue venue);
	
	public void loadSeatInfo(Venue venue);
	
	public void loadPlanSeatInfo(SeatInfo info,Plan plan);
	
	public void loadPlanSeatInfo(Plan plan);
	
	public void createSeats(Plan plan);
	
	public void chooseSeats(String planid,List<SeatState> info);
	
	public void addUnAssuredSeats(Plan plan,List<Order> orderlist);
	
	public List<Order> distributeSeats(Plan plan);
	
	public void distributeSeat(Plan plan,Order order);
}
