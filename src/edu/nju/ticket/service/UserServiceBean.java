package edu.nju.ticket.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ticket.common.AccountType;
import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.common.UserColumn;
import edu.nju.ticket.dao.UserDao;
import edu.nju.ticket.model.User;
import edu.nju.ticket.utils.EmailService;
import edu.nju.ticket.utils.Encryption;

@Service
public class UserServiceBean implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public SigninState signIn(String email, String password) {
		User user=userDao.findUser(UserColumn.useremail,email);
		
		if(user!=null&&user.getPassword().equals(Encryption.getInstance().sha1(password)))
		{
			return user.isActivated()?SigninState.Success:SigninState.NotActivated;
		}
		else
		{
			return user==null?SigninState.AccountNotFound:SigninState.PasswordWrong;
		}
	}

	@Override
	public boolean signUp(User user) {
		if(userDao.hasUser(user.getUseremail()))
		{
			return false;
		}
		else
		{
			user.setPassword(Encryption.getInstance().sha1(user.getPassword()));
			String uuid=null;
			do
			{
				uuid=UUID.randomUUID().toString().replace("-", "");
			}while(userDao.hasUUID(uuid));
			user.setUserid(uuid);
			user.setCreatetime(LocalDateTime.now());
			userDao.createUser(user);
			EmailService service=new EmailService(AccountType.user,user.getUseremail(),user.getUserid());
			service.start();
			return true;
		}
	}

	@Override
	public boolean checkSignUp(String uuid) {
		User user=userDao.findUser(UserColumn.userid,uuid);
		if(user!=null&&!user.isActivated())
		{
			long time=Duration.between(user.getCreatetime(),LocalDateTime.now()).toMinutes();
			if(time>15)
			{
				userDao.deleteUser(user);
				return false;
			}
			else
			{
				user.setActivated(true);
				userDao.updateUser(user);
				return true;
			}
		}
		return false;
	}
	
	public void reviseInfo(User user)
	{
		user.setPassword(Encryption.getInstance().sha1(user.getPassword()));
		userDao.updateUser(user);
	}
}
