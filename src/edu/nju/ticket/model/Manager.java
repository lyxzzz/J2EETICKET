package edu.nju.ticket.model;

import java.io.Serializable;

public class Manager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String account;
	
	private String password;

	public String getAccount() {
		return account;
	}

	public Manager() {
		super();
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Manager(String account, String password) {
		super();
		this.account = account;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
