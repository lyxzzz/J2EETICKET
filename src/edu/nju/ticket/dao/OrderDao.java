package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.common.OrderColumn;
import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.PlanSeatInfo;
import edu.nju.ticket.model.User;

public interface OrderDao {
	public List<Order> findOrder(List<OrderColumn> column,List<String> value);
	
	public void updateOrder(List<Order> order);
	
	public void createOrder(List<Order> order);
	
	public void loadUnAssuredOrder(String planid,PlanSeatInfo seatinfo);
	
	public List<User> settleOrder(Plan plan);
}
