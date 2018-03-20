package edu.nju.ticket.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.model.Venue;
import edu.nju.ticket.model.Yard;

public interface YardService {

	public void signUp(Yard yard);
	
	public SigninState signIn(String yardid,String password,HttpSession session,HttpServletRequest request, HttpServletResponse response);
	
	public boolean reviseInfo(String yardid,Yard yard);
	
	public void createVenue(Venue venue);
}
