package edu.nju.ticket.service;

import java.util.List;

import edu.nju.ticket.common.PlanColumn;
import edu.nju.ticket.model.Plan;

public interface PlanService {
	
	public boolean publishPlan(Plan plan);
	
	public List<Plan> findPlan(List<PlanColumn> column,List<String> value);
	
	public Plan getPlan(String planid);
}
