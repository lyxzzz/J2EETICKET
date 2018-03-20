package edu.nju.ticket.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7604993280223644483L;
	
	private String userid;
	private String useremail;
	private String password;
	private int integral;
	private int experience;
	private LocalDateTime createtime;
	private boolean activated;
	
	public User(String userid, String useremail, String password, LocalDateTime createtime) {
		super();
		this.userid = userid;
		this.useremail = useremail;
		this.password = password;
		this.createtime = createtime;
		integral=0;
		experience=0;
	}
	public User() {
		super();
	}
	
	
	
	public User(String userid, int integral) {
		super();
		this.userid = userid;
		this.integral = integral;
		this.experience = integral;
	}
	public User(User user) {
		super();
		this.userid = user.userid;
		this.useremail = user.useremail;
		this.password = user.password;
		this.integral = user.integral;
		this.experience = user.experience;
		this.createtime = user.createtime;
		this.activated = user.activated;
	}
	public User(String userid, String useremail, String password, int integral, int experience, int level,
			LocalDateTime createtime, boolean activated) {
		super();
		this.userid = userid;
		this.useremail = useremail;
		this.password = password;
		this.integral = integral;
		this.experience = experience;
		this.createtime = createtime;
		this.activated = activated;
	}
	public User(String useremail, String password) {
		super();
		this.useremail = useremail;
		this.password = password;
		activated=false;
	}
	public User(String userid, String useremail, String password, int integral, int experience, int level,
			LocalDateTime createtime) {
		super();
		this.userid = userid;
		this.useremail = useremail;
		this.password = password;
		this.integral = integral;
		this.experience = experience;
		this.createtime = createtime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public LocalDateTime getCreatetime() {
		return createtime;
	}
	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "User [userid=" + userid + ", useremail=" + useremail + ", password=" + password + ", integral="
				+ integral + ", experience=" + experience + ", createtime=" + createtime + "]";
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
