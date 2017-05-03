package com.cif.trickle.dataProvider.bean;

/**
 * 用例对应的表头字段，数据插入mysql后的列名
 * @author WangJian  20170426 
 *
 */

public class HiveBean {
	private String house_overdue_currency;
	private String money;
	private String apply_no;
	private String report_no;

	public String getName() {
		String className = this.getClass().getName();
		return className;
	}

	public String getHouse_overdue_currency() {
		return house_overdue_currency;
	}

	public void setHouse_overdue_currency(String house_overdue_currency) {
		this.house_overdue_currency = house_overdue_currency;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}

	public String getReport_no() {
		return report_no;
	}

	public void setReport_no(String report_no) {
		this.report_no = report_no;
	}
}
