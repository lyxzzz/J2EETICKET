package edu.nju.ticket.dao;

import java.util.List;

import edu.nju.ticket.common.UserColumn;
import edu.nju.ticket.model.User;

public interface UserDao {
	
	public User findUser(UserColumn column,String value);
	
	public void createUser(User user);
	
	public boolean hasUser(String email);
	
	public boolean hasUUID(String id);
	
	public void deleteUser(User user);
	
	public void updateUser(User user);
	
	public void updateUsers(List<User> userlist);
}
