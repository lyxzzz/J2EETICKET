package edu.nju.ticket.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ticket.common.AccountType;
import edu.nju.ticket.common.ManagerColumn;
import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.common.YardColumn;
import edu.nju.ticket.dao.ManagerDao;
import edu.nju.ticket.dao.YardDao;
import edu.nju.ticket.handler.ManagerHandler;
import edu.nju.ticket.model.Manager;
import edu.nju.ticket.model.Yard;
import edu.nju.ticket.utils.EmailService;
import edu.nju.ticket.utils.Encryption;

@Service
public class ManagerServiceBean implements ManagerService{

	@Autowired
	private YardDao yardDao;
	
	@Autowired
	private ManagerDao managerDao;
	
	@Override
	public List<Yard> findUncheckedYard() {
		return yardDao.findUnCheckedYard();
	}

	@Override
	public SigninState signIn(String account, String password,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		Manager manager=managerDao.findManager(ManagerColumn.account, account);
		if(manager!=null&&manager.getPassword().equals(Encryption.getInstance().sha1(password)))
		{
			ManagerHandler.initSession(session, request, response, manager);
			return SigninState.Success;
		}
		else
		{
			return manager==null?SigninState.AccountNotFound:SigninState.PasswordWrong;
		}
	}

	@Override
	public boolean checkYard(boolean operate,String yardid) {
		Yard yard=yardDao.findYard(YardColumn.yardid, yardid);
		if(yard==null)
		{
			return false;
		}
		if(operate)
		{
			if(!yard.isActivated())
			{
				yard.setActivated(operate);
				EmailService service=new EmailService(AccountType.yard,yard.getEmail(),yard.getYardid());
				service.start();
				yardDao.updateYard(yard);
			}
		}
		else
		{
			yardDao.deleteYard(yard);
		}
		return true;
	}

	@Override
	public List<Yard> findReviseYard() {
		return yardDao.findReviseYard();
	}

	@Override
	public boolean checkRevise(boolean operate, String yardid) {
		Yard origin=yardDao.findYard(YardColumn.yardid, yardid);
		Yard yard=yardDao.findRevise(YardColumn.yardid, yardid);
		if(origin==null&&yard==null)
		{
			return false;
		}
		if(operate)
		{
			origin.setAddress(yard.getAddress());
			origin.setYardname(yard.getYardname());
			origin.setPayacount(yard.getPayacount());
			yardDao.updateYard(origin);
		}
		yardDao.deleteRevise(yardid);
		EmailService service=new EmailService(AccountType.revise,origin.getEmail(),operate?"已经":"不能");
		service.start();
		return true;
	}
	
	

}
