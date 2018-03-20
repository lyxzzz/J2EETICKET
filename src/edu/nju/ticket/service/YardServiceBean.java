package edu.nju.ticket.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.nju.ticket.common.SigninState;
import edu.nju.ticket.common.YardColumn;
import edu.nju.ticket.dao.YardDao;
import edu.nju.ticket.handler.YardHandler;
import edu.nju.ticket.model.Venue;
import edu.nju.ticket.model.Yard;
import edu.nju.ticket.utils.Encryption;

@Service
public class YardServiceBean implements YardService {
	@Autowired
	YardDao yardDao;


	@Override
	public void signUp(Yard yard) {
		String uuid=null;
		do
		{
			uuid=UUID.randomUUID().toString().replace("-", "").substring(0, 7);
		}while(yardDao.hasUUID(uuid));
		yard.setPassword(Encryption.getInstance().sha1(yard.getPassword()));
		yard.setYardid(uuid);
		yard.setCreatetime(LocalDateTime.now());
		yardDao.createYard(yard);
	}
	
	

	@Override
	public SigninState signIn(String yardid, String password,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Yard yard=yardDao.findYard(YardColumn.yardid,yardid);
		
		if(yard!=null&&yard.getPassword().equals(Encryption.getInstance().sha1(password)))
		{
			if(yard.isActivated())
			{
				YardHandler.initSession(session, request, response, yard);
				return SigninState.Success;
			}
			else
			{
				return SigninState.AccountNotFound;
			}
		}
		else
		{
			return yard==null?SigninState.AccountNotFound:SigninState.PasswordWrong;
		}
	}

	@Override
	public boolean reviseInfo(String yardid,Yard yard) {
		Yard temp=yardDao.findYard(YardColumn.yardid,yardid);
		if(temp.equals(yard))
		{
			return false;
		}
		else
		{
			Yard reviseyard=yardDao.findRevise(YardColumn.yardid, yardid);
			if(reviseyard==null)
			{
				yardDao.insertRevise(yard);
			}
			else
			{
				yardDao.updateRevise(yard);
			}
			return true;
		}
	}





	@Override
	public void createVenue(Venue venue) {
		// TODO Auto-generated method stub
		
	}
}
