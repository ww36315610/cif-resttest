package pu.hui.restauto.restassure;

public class TestCaseEntity {
	
	private String testCaseNo;
	private String interface_name;
	private String interface_desc;
	private String interface_uri;
	private String requestParam;
	private String repectValue;
	private String actualValue;
	private boolean result;
	private String interface_baseURI;
	private String serverResponse;
	private int rownNum;
	private String paramType;
	private String replaceParam;
	
	
	public String getReplaceParam() {
		return replaceParam;
	}
	public void setReplaceParam(String replaceParam) {
		this.replaceParam = replaceParam;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public int getRownNum() {
		return rownNum;
	}
	public void setRownNum(int rownNum) {
		this.rownNum = rownNum;
	}
	public String getServerResponse() {
		return serverResponse;
	}
	public void setServerResponse(String serverResponse) {
		this.serverResponse = serverResponse;
	}
	public String getTestCaseNo() {
		return testCaseNo;
	}
	public void setTestCaseNo(String testCaseNo) {
		this.testCaseNo = testCaseNo;
	}
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	public String getInterface_desc() {
		return interface_desc;
	}
	public void setInterface_desc(String interface_desc) {
		this.interface_desc = interface_desc;
	}
	public String getInterface_uri() {
		return interface_uri;
	}
	public void setInterface_uri(String interface_uri) {
		this.interface_uri = interface_uri;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getRepectValue() {
		return repectValue;
	}
	public void setRepectValue(String repectValue) {
		this.repectValue = repectValue;
	}
	public String getActualValue() {
		return actualValue;
	}
	public void setActualValue(String actualValue) {
		this.actualValue = actualValue;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getInterface_baseURI() {
		return interface_baseURI;
	}
	public void setInterface_baseURI(String interface_baseURI) {
		this.interface_baseURI = interface_baseURI;
	}
	public String getInterface_basePath() {
		return interface_basePath;
	}
	public void setInterface_basePath(String interface_basePath) {
		this.interface_basePath = interface_basePath;
	}
	public String getInterface_address() {
		return interface_address;
	}
	public void setInterface_address(String interface_address) {
		this.interface_address = interface_address;
	}
	private String interface_basePath;
	private String interface_address;
	

}
