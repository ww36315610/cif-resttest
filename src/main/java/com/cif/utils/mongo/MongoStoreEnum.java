package com.cif.utils.mongo;

public interface MongoStoreEnum {
	enum QianZhan implements MongoStoreEnum {
		OpenLineStore, TaobaoStore, MobileStore, CreditStore, AlipayStore, BankCardStore, CreditReportStore
	}

	enum KaNiu implements MongoStoreEnum {
		INCustomerStore, INTaobaoStore, INMobileStore, INCreditCardStore, INAlipayStore
	}

	enum Nirvana implements MongoStoreEnum {
		AlipayBanklistWrapper, AlipayBasicInfoWrapper, MobileDetailVoiceWrapper, MobileDetailSmsWrapper, MobileBasicInfoWrapper, CreditReportInfoCredit60Wrapper, CreditReportInfoCreditNormalWrapper, CreditReportInfoCreditOverdueWrapper, CreditReportInfoHouseNormalWrapper, TaobaoBasicInfoWrapper
	}
}
