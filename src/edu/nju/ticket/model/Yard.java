package edu.nju.ticket.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Yard implements Serializable{

	public Yard() {
		super();
	}
	static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public Yard(Yard yard)
	{
		this.yardid = yard.yardid;
		this.yardname = yard.yardname;
		this.password = yard.password;
		this.address = yard.address;
		this.email = yard.email;
		this.activated = yard.activated;
		this.createtime = yard.createtime;
		this.payacount=yard.payacount;
	}
	
	public Yard(String yardname, String password, String address, String email,String payacount) {
		super();
		this.yardname = yardname;
		this.password = password;
		this.address = address;
		this.email = email;
		this.payacount=payacount;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activated ? 1231 : 1237);
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((createtime == null) ? 0 : createtime.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((payacount == null) ? 0 : payacount.hashCode());
		result = prime * result + ((yardid == null) ? 0 : yardid.hashCode());
		result = prime * result + ((yardname == null) ? 0 : yardname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Yard other = (Yard) obj;
		if (activated != other.activated)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (createtime == null) {
			if (other.createtime != null)
				return false;
		} else if (!createtime.equals(other.createtime))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (payacount == null) {
			if (other.payacount != null)
				return false;
		} else if (!payacount.equals(other.payacount))
			return false;
		if (yardid == null) {
			if (other.yardid != null)
				return false;
		} else if (!yardid.equals(other.yardid))
			return false;
		if (yardname == null) {
			if (other.yardname != null)
				return false;
		} else if (!yardname.equals(other.yardname))
			return false;
		return true;
	}

	public Yard(Map<String, Object> map)
	{
		super();
		this.yardid = String.valueOf(map.get("yardid"));
		this.yardname = String.valueOf(map.get("yardname"));
		this.password = String.valueOf(map.get("password"));
		this.address = String.valueOf(map.get("address"));
		this.email = String.valueOf(map.get("email"));
		this.activated = ((int)map.getOrDefault("activated",1)==1);
		Timestamp t=(Timestamp)map.getOrDefault("createtime",new Timestamp(System.currentTimeMillis()));
		this.createtime = t.toLocalDateTime();
		this.payacount=String.valueOf(map.get("payacount"));
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4453995752947520755L;

	private String yardid;
	
	private String yardname;
	
	private String password;
	
	private String address;
	
	private String email;

	private boolean activated;
	
	private String payacount;
	
	private LocalDateTime createtime;
	
	public LocalDateTime getCreatetime() {
		return createtime;
	}

	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}

	public String getYardid() {
		return yardid;
	}

	public void setYardid(String yardid) {
		this.yardid = yardid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getYardname() {
		return yardname;
	}

	public void setYardname(String yardname) {
		this.yardname = yardname;
	}



	@Override
	public String toString() {
		return "Yard [yardid=" + yardid + ", yardname=" + yardname + ", password=" + password + ", address=" + address
				+ ", email=" + email + ", activated=" + activated + ", payacount=" + payacount + ", createtime="
				+ createtime + "]";
	}

	public String getPayacount() {
		return payacount;
	}

	public void setPayacount(String payacount) {
		this.payacount = payacount;
	}
}
