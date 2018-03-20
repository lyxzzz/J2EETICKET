package edu.nju.ticket.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.model.Yard;

public interface ManagerService {
	
	public List<Yard> findUncheckedYard();
	
	public SigninState signIn(String account,String password,HttpSession session,HttpServletRequest request, HttpServletResponse response);
	
	public boolean checkYard(boolean operate,String yardid);
	
	public List<Yard> findReviseYard();
	
	public boolean checkRevise(boolean operate,String yardid);
}
