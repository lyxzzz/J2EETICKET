package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.common.YardColumn;
import edu.nju.ticket.model.Yard;

public interface YardDao {

	public Yard findYard(YardColumn column,String value);
	
	public void createYard(Yard user);
	
	public boolean hasUUID(String id);
	
	public void deleteYard(Yard yard);
	
	public void updateYard(Yard yard);
	
	public List<Yard> findUnCheckedYard();
	
	public List<Yard> findReviseYard();
	
	public Yard findRevise(YardColumn column,String value);
	
	public void insertRevise(Yard yard);
	
	public void updateRevise(Yard yard);
	
	public void deleteRevise(String yardid);
}
