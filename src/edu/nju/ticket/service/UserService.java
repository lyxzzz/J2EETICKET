package edu.nju.ticket.service;

import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.model.User;

public interface UserService {
	
	public SigninState signIn(String email,String password);
	
	public boolean signUp(User user);
	
	public boolean checkSignUp(String uuid);
	
	public void reviseInfo(User user);
}
