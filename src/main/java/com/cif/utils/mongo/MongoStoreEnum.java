package com.cif.utils.mongo;

public interface MongoStoreEnum {
	enum QianZhan implements MongoStoreEnum {
		OpenLineStore, TaobaoStore, MobileStore, CreditStore, AlipayStore, BankCardStore, CreditReportStore
	}

	enum KaNiu implements MongoStoreEnum {
		INCustomerStore, INTaobaoStore, INMobileStore, INCreditCardStore, INAlipayStore
	}
}
