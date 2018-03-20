package edu.nju.ticket.dao;

import edu.nju.ticket.common.ManagerColumn;
import edu.nju.ticket.model.Manager;

public interface ManagerDao {
	public Manager findManager(ManagerColumn column,String value);
}
