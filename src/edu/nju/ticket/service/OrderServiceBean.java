package edu.nju.ticket.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ticket.common.OrderState;
import edu.nju.ticket.dao.OrderDao;
import edu.nju.ticket.dao.PlanDao;
import edu.nju.ticket.dao.SeatDao;
import edu.nju.ticket.dao.UserDao;
import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.SeatState;
import edu.nju.ticket.model.User;
import edu.nju.ticket.utils.Time;
@Service
public class OrderServiceBean implements OrderService{
	@Autowired
	SeatDao seatDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	PlanDao planDao;
	@Autowired
	UserDao userDao;
	@Override
	public void generateAssuredOrder(List<Order> orderlist) {
		orderDao.createOrder(orderlist);
		String planid=orderlist.get(0).getPlanid();
		List<SeatState> seatstates=new ArrayList<SeatState>();
		for(int i=0;i<orderlist.size();i++)
		{
			seatstates.add(new SeatState(orderlist.get(i).getSeatplace(),true));
		}
		seatDao.chooseSeats(planid, seatstates);
	}
	@Override
	public void generateUnAssuredOrder(Plan plan,List<Order> orderlist) {
		orderDao.createOrder(orderlist);
		String planid=orderlist.get(0).getPlanid();
		seatDao.loadPlanSeatInfo(plan);
		LocalDateTime ldt=LocalDateTime.now().plusMinutes(Time.getInstance().distribute_time);
		if(!plan.getStartdate().isAfter(ldt))
		{
			for(int i=0;i<orderlist.size();i++)
			{
				seatDao.distributeSeat(plan, orderlist.get(i));
			}
		}
		else
		{
			orderDao.loadUnAssuredOrder(planid, plan.getState());
			seatDao.addUnAssuredSeats(plan, orderlist);
		}	
		orderDao.updateOrder(orderlist);
	}
}
