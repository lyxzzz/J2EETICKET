package edu.nju.ticket.utils;

import edu.nju.ticket.common.AccountType;

public class EmailService extends Thread{
	private AccountType type;
	private String to;
	private String id;
	public EmailService(AccountType type,String to,String idurl)
	{
		this.type=type;
		this.to=to;
		this.id=idurl;
	}
	@Override
	public void run() {
		Email.getInstance().sendEmail(type, to, id);
	}
}
