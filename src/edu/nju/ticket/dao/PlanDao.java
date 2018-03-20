package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.common.PlanColumn;
import edu.nju.ticket.model.Plan;

public interface PlanDao {
	public List<Plan> findPlan(List<PlanColumn> column,List<String> value);
	
	public void createPlan(Plan plan);
	
	public boolean hasUUID(String id);
	
	public void deletePlan(String planid);
	
	public void updatePlan(Plan plan);
	
	public List<Plan> getPlanList();
}
