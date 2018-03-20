package edu.nju.ticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ticket.common.PlanColumn;
import edu.nju.ticket.dao.PlanDao;
import edu.nju.ticket.dao.SeatDao;
import edu.nju.ticket.model.Plan;
import edu.nju.ticket.utils.Time;
@Service
public class PlanServiceBean implements PlanService{
	@Autowired
	PlanDao planDao;
	@Autowired
	SeatDao seatDao;
	@Override
	public boolean publishPlan(Plan plan) {
		List<PlanColumn> columnlist=new ArrayList<PlanColumn>();
		List<String> valuelist=new ArrayList<String>();
		columnlist.add(PlanColumn.venueid);
		valuelist.add(plan.getVenueid());
		List<Plan> list=planDao.findPlan(columnlist,valuelist);
		Time time=Time.getInstance();
		for(int i=0;i<list.size();i++)
		{
			if(time.between(plan.getStartdate(), plan.getEnddate(),list.get(i).getStartdate(), list.get(i).getEnddate()))
			{
				return false;
			}
		}
		planDao.createPlan(plan);
		seatDao.createSeats(plan);
		return true;
	}
	@Override
	public List<Plan> findPlan(List<PlanColumn> column, List<String> value) {
		return planDao.findPlan(column, value);
	}
	@Override
	public Plan getPlan(String planid) {
		List<PlanColumn> columnlist=new ArrayList<PlanColumn>();
		List<String> valuelist=new ArrayList<String>();
		columnlist.add(PlanColumn.planid);
		valuelist.add(planid);
		List<Plan> list=planDao.findPlan(columnlist,valuelist);
		return list.size()==0?null:list.get(0);
	}
}
