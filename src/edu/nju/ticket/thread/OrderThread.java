package edu.nju.ticket.thread;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.nju.ticket.dao.OrderDao;
import edu.nju.ticket.dao.PlanDao;
import edu.nju.ticket.dao.SeatDao;
import edu.nju.ticket.dao.UserDao;
import edu.nju.ticket.model.Order;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.model.User;
import edu.nju.ticket.utils.Time;

@Component  
public class OrderThread {  
    @Autowired
    OrderDao orderDao;
    @Autowired
    PlanDao planDao;
    @Autowired
    SeatDao seatDao;
    @Autowired
    UserDao userDao;
    /**  
     * 定时计算。每天凌晨 01:00 执行一次  
     */    
      
    /**  
     * 心跳更新。启动时执行一次，之后每隔2秒执行一次  
     */    
    //@Scheduled(fixedRate = 1000*10)   
    public void distribute(){  
    	List<Plan> list=planDao.getPlanList();
    	List<Order> orderlist=new ArrayList<Order>();
    	LocalDateTime ldt=LocalDateTime.now().plusMinutes(Time.getInstance().distribute_time);
    	for(int i=0;i<list.size();i++)
    	{
    		Plan temp=list.get(i);
    		if(!temp.getStartdate().isAfter(ldt))
    		{
    			orderlist.addAll(seatDao.distributeSeats(temp));
    		}
    	}
    	orderDao.updateOrder(orderlist);
    	ldt=LocalDateTime.now();
    	for(int i=0;i<list.size();i++)
    	{
    		Plan temp=list.get(i);
    		if(temp.getEnddate().isBefore(ldt))
    		{
    			List<User> userlist=orderDao.settleOrder(temp);
    			userDao.updateUsers(userlist);
    		}
    	}
        System.out.println("Annotation：distribute run");  
    }  
}  
