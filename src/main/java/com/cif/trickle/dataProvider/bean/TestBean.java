package com.cif.trickle.dataProvider.bean;

import java.util.Date;

public class TestBean extends Table01_Bean{
	private int person_id;
	private String name;
	private String country;
	private int wan_key;
	private Date addcol_2;
	private Date addcol_4;

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getWan_key() {
		return wan_key;
	}

	public void setWan_key(int wan_key) {
		this.wan_key = wan_key;
	}

	public Date getAddcol_2() {
		return addcol_2;
	}

	public void setAddcol_2(Date addcol_2) {
		this.addcol_2 = addcol_2;
	}

	public Date getAddcol_4() {
		return addcol_4;
	}

	public void setAddcol_4(Date addcol_4) {
		this.addcol_4 = addcol_4;
	}
}
