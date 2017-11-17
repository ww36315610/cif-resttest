package com.cif.now.utils.resource;

import java.util.HashMap;
import java.util.Map;

public class Contants {
	public static final String FILE = "dbconn.conf";
	public static final String FILEA = "sql_easy.conf";

	public static final String UATQZ_ALIPAY_INFO_CONTACTS_ALL = "uat_qz.alipay_info.contacts.all";
	public static final String UATQZ_ALIPAY_INFO_BANK_ALL = "uat_qz.alipay_info.alipay_bank.all";
	public static final String UATQZ_ALIPAY_INFO_BASIC_ALL = "uat_qz.alipay_info.alipay_basic.all";

	public static final String UATQZ_CREDITREPORT_INFO_CUSTOMERINFO_ALL = "uat_qz.credit_report_info.customerInfo.all";
	public static final String UATQZ_CREDITREPORT_INFO_INFOSUMMARY_ALL = "uat_qz.credit_report_info.infoSummary.all";
	public static final String UATQZ_CREDITREPORT_INFO_INFOOTHEROVERDUE_ALL = "uat_qz.credit_report_info.infoOtherOverdue.all";
	public static final String UATQZ_CREDITREPORT_INFO_INFOOTHERNOTMAL_ALL = "uat_qz.credit_report_info.infoOtherNormal.all";
	public static final String UATQZ_CREDITREPORT_INFO_QUERYINFO_ALL = "uat_qz.credit_report_info.queryInfo.all";

	public static final String UATQZ_MOBILE_INFO_VOICES_ALL = "uat_qz.mobile_info.voices.all";
	public static final String UATQZ_MOBILE_INFO_INTERSECTPHONES_ALL = "uat_qz.mobile_info.contacts_intersectphones.all";

	public static Map<String, String> map;
	static {
		map = new HashMap<String, String>();
		map.put("alipay_contacts", UATQZ_ALIPAY_INFO_CONTACTS_ALL);
		map.put("alipay_bank", UATQZ_ALIPAY_INFO_BANK_ALL);
		map.put("alipay_basic", UATQZ_ALIPAY_INFO_BASIC_ALL);
		map.put("creditreport_customerInfo", UATQZ_CREDITREPORT_INFO_CUSTOMERINFO_ALL);
		map.put("creditreport_infoSummary", UATQZ_CREDITREPORT_INFO_INFOSUMMARY_ALL);
		map.put("creditreport_infoOtherOverdue", UATQZ_CREDITREPORT_INFO_INFOOTHEROVERDUE_ALL);
		map.put("creditreport_infoOtherNormal", UATQZ_CREDITREPORT_INFO_INFOOTHERNOTMAL_ALL);
		map.put("creditreport_queryInfo", UATQZ_CREDITREPORT_INFO_QUERYINFO_ALL);
		map.put("mobile_voices", UATQZ_MOBILE_INFO_VOICES_ALL);
		map.put("mobile_intersectphones", UATQZ_MOBILE_INFO_INTERSECTPHONES_ALL);
	}
}
