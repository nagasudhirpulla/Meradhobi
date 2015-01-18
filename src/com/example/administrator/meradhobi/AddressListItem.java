package com.example.administrator.meradhobi;

public class AddressListItem {
	private String pname;
	private String pcity;
	private String pstate;
	private String paddress;
	private String ppin;

	public AddressListItem(String pname, String pcity, String pstate, String paddress, String ppin)
	{
		this.pname = pname;
		this.pcity = pcity;
		this.pstate = pstate;
		this.paddress = paddress;
		this.ppin = ppin;
	}

	public String getpname() {
		// TODO Auto-generated method stub
		return this.pname;
	}

	public String getpcity() {
		// TODO Auto-generated method stub
		return this.pcity;
	}

	public String getpstate() {
		// TODO Auto-generated method stub
		return this.pstate;
	}

	public String getpaddress() {
		// TODO Auto-generated method stub
		return this.paddress;
	}

	public String getppin() {
		// TODO Auto-generated method stub
		return this.ppin;
	}

	public void setpname(String pname) {
		// TODO Auto-generated method stub
		this.pname = pname;
	}

	public void setpcity(String pcity) {
		// TODO Auto-generated method stub
		this.pcity = pcity;
	}

	public void setpstate(String pstate) {
		// TODO Auto-generated method stub
		this.pstate = pstate;
	}

	public void setpaddress(String paddress) {
		// TODO Auto-generated method stub
		this.paddress = paddress;
	}

	public void setppin(String ppin) {
		// TODO Auto-generated method stub
		this.ppin = ppin;
	}


}
