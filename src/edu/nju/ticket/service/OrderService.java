package edu.nju.ticket.service;

import java.util.List;

import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;

public interface OrderService {
	public void generateAssuredOrder(List<Order> orderlist);
	public void generateUnAssuredOrder(Plan plan,List<Order> orderlist);
}
