package com.cif.savecase;

public class ThreadStart {

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			ExcellOperation_Mysql eom = new ExcellOperation_Mysql();
			new Thread(eom).start();
		}
	}
}
