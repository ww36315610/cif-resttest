package pu.hui.restauto.restassure;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;

import com.alibaba.fastjson.JSON;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;

import net.sf.json.JSONObject;
import pu.hui.util.Constants;
import pu.hui.util.ExcelSheet;
import pu.hui.util.ExcelUtil;

public class TestCaseBase {
	private static Logger logger = Logger.getLogger(TestCaseBase.class);
	private static ExcelUtil eu = new ExcelUtil(
			TestCaseBase.class.getClassLoader().getResource("cif-interface-test.xls").getPath());
	private static ExcelSheet excelSheet = eu.getSheet("Interface");
	private static ExcelSheet excelSheet2 = eu.getSheet("Datas");
	public static String token = "";
	//成功个数
	public static int sucCount = 0;
	//失败个数
	public static int failCount = 0;

	@BeforeSuite
	public void setUp() {

	}

	public static TestCaseEntity readTestCaseFromExcel(String testCaseNo) {
		System.out.println(TestCaseBase.class.getClassLoader().getResource("cif-interface-test.xls").getPath());

		int rowCount = excelSheet.getRowCount();
		TestCaseEntity testCaseEntity = new TestCaseEntity();
		for (int i = 0; i < rowCount; i++) {
			String testCaseNum = excelSheet.getCellValue(i, 0);
			if (testCaseNo.equals(testCaseNum)) {
				String interfaceDesc = excelSheet.getCellValue(i, Constants.COL_INTERFACE_DESC);
				String interfaceName = excelSheet.getCellValue(i, Constants.COL_INTERFACE_NAME);
				String interfaceBaseURI = excelSheet.getCellValue(i, Constants.COL_BASE_URI);
				String interfaceBasePath = excelSheet.getCellValue(i, Constants.COL_BASE_PATH);
				String interfaceAddress = excelSheet.getCellValue(i, Constants.COL_ADDRESS);
				String requestParam = excelSheet.getCellValue(i, Constants.COL_REQUEST_PARAM);
				String expectValue = excelSheet.getCellValue(i, Constants.COL_EXPECT_VALUE);
				String paramType = excelSheet.getCellValue(i, Constants.COL_PARAM_TYPE);
				String replaceParam = excelSheet.getCellValue(i, Constants.COL_REQUEST_REPLACE_PARAM);
				testCaseEntity.setTestCaseNo(testCaseNum);
				testCaseEntity.setInterface_name(interfaceName);
				testCaseEntity.setInterface_desc(interfaceDesc);
				testCaseEntity.setInterface_baseURI(interfaceBaseURI);
				testCaseEntity.setInterface_basePath(interfaceBasePath);
				testCaseEntity.setInterface_address(interfaceAddress);
				//testCaseEntity.setRequestParam(requestParam);
				
				isParametered(requestParam);
				
				testCaseEntity.setRequestParam(requestParam);
				testCaseEntity.setRepectValue(expectValue);
				testCaseEntity.setRownNum(i);
				testCaseEntity.setParamType(paramType);
				testCaseEntity.setReplaceParam(replaceParam);
				if("json".equals(paramType)&&!StringUtils.isEmpty(replaceParam)){
					replaceRequestParam(testCaseEntity);
				}

				//break;
			}

		}
		return testCaseEntity;
	}
	
	
	public static boolean isParametered(String tag){
		if("@isParametered".equals(tag)){
			return true;
		}else
			return false;	
	}
	

	public TestCaseEntity postJson(TestCaseEntity testCaseEntity) {
		RestAssured.baseURI = testCaseEntity.getInterface_baseURI();
		RestAssured.port = 30333;
		RestAssured.basePath = testCaseEntity.getInterface_basePath();
		RestAssured.registerParser("text/plain", Parser.JSON);
		String xx = testCaseEntity.getRequestParam();
		String yy = testCaseEntity.getInterface_address();
		String json = given().contentType("application/json").body(testCaseEntity.getRequestParam()).when()
				.post(testCaseEntity.getInterface_address()).asString();
		testCaseEntity.setServerResponse(json);
		System.out.println("++++++++++++++++++++json:" + json + "-------");
		return testCaseEntity;
	}

	public TestCaseEntity postKeyValue(TestCaseEntity testCaseEntity) {
		RestAssured.baseURI = testCaseEntity.getInterface_baseURI();
		RestAssured.port = 8888;
		RestAssured.basePath = testCaseEntity.getInterface_basePath();
		RestAssured.registerParser("text/plain", Parser.JSON);
		Map<String,String> keyValueToMap = keyValueToMap(testCaseEntity);
		System.out.println("keyValueToMap:" + keyValueToMap);

		String json = given().contentType("application/json").body(testCaseEntity.getRequestParam()).when()
				.post(testCaseEntity.getInterface_address()).asString();
		
		testCaseEntity.setServerResponse(json);
		System.out.println("++++++++++++++++++++json:" + json + "-------" );
		return testCaseEntity;
	}

	public TestCaseEntity assertResult(TestCaseEntity testCaseEntity) {

		Map<String, String> keyValueMap = dealExpectValue(testCaseEntity.getRepectValue());
		fillActualValue(keyValueMap, testCaseEntity);
		boolean result = compareResult(keyValueMap, testCaseEntity.getServerResponse());
		testCaseEntity.setResult(result);
		if(result){
			sucCount++;
		}else{
			failCount++;
		}
		return testCaseEntity;
	}

	public Map<String, String> dealExpectValue(String expectValue) {
		// status=904##code=1##body.mobile=18211097924
		Map<String, String> keyValueMap = new HashMap<String, String>();
		String[] expectValues = expectValue.split("##");
		for (int i = 0; i < expectValues.length; i++) {
			String[] keyValues = expectValues[i].split("=");
			keyValueMap.put(keyValues[0], keyValues[1]);
		}
		return keyValueMap;
	}

	public void setExcelValue(TestCaseEntity testCaseEntity,Data data) {

//		excelSheet.setValue(testCaseEntity.getRownNum(), Constants.COL_SERVER_RESPONSE,
//				testCaseEntity.getServerResponse());
		excelSheet.setValue(testCaseEntity.getRownNum(), Constants.COL_ACTUAL_VALUE, testCaseEntity.getActualValue());
		excelSheet.setValue(testCaseEntity.getRownNum(), Constants.COL_RESULT, testCaseEntity.isResult() + "");
		excelSheet2.setValue(data.getRow(), 0, testCaseEntity.isResult() + "");

		eu.save();
	}

	public boolean compareResult(Map<String, String> keyValueMap, String result) {

		boolean flag = false;
		int size = keyValueMap.keySet().size();
		int count = 0;
		for (String key : keyValueMap.keySet()) {
			count++;
			String value = keyValueMap.get(key);
			String actualValue = JsonPath.from(result).get(key) + "";
			if (!value.equals(actualValue)) {
				return flag;
			}
			if (count == size) {
				return true;
			}
		}
		return false;
	}

	public void fillActualValue(Map<String, String> keyValueMap, TestCaseEntity testCaseEntity) {

		StringBuffer sb = new StringBuffer();
		for (String key : keyValueMap.keySet()) {
			String actualValue = JsonPath.from(testCaseEntity.getServerResponse()).get(key) + "";
			sb.append(key).append("=").append(actualValue).append("##");
		}
		String value = sb.toString();
		if (value.endsWith("##")) {
			testCaseEntity.setActualValue(value.substring(0, value.lastIndexOf("##")));
		}

	}

	public static void replaceRequestParam(TestCaseEntity testCaseEntity) {

		String jsonObjectParam = testCaseEntity.getRequestParam();
		String replaceParam = testCaseEntity.getReplaceParam();
		String[] replaceParamValuesArr = replaceParam.split(",");
		if(!"".equals(replaceParam)){
			Map<String, String> nameValuePair = new HashMap<String, String>();
			String[] values = new String[replaceParamValuesArr.length+1];
			for (int i = 0; i < replaceParamValuesArr.length; i++) {
				values[i] = replaceParamValuesArr[i].split("=")[1];			
			}
			if(!"".equals(token)){
				values[values.length] = token;
			}
			jsonObjectParam = String.format(jsonObjectParam, values);
			
			System.out.println("替换过后的json参数："+jsonObjectParam);
		}
		jsonObjectParam = String.format(jsonObjectParam, new String[]{token});
		testCaseEntity.setRequestParam(jsonObjectParam);
	}
	
	public void calculateSuccAndFail(){
		
		float susseccRate = ((float)sucCount)/(sucCount+failCount)*100;//((double)la)/b;
		excelSheet.setValue(0, 0, "运行总数："+(sucCount+failCount)+"    "+"成功个数："+sucCount+"    "+"失败个数："+failCount+"   "+"通过率："+susseccRate+"%");
		eu.save();
	}
	
	public Map<String,String> keyValueToMap(TestCaseEntity testCaseEntity){
		Map<String,String> map = new HashMap<String,String>();
		String requestParam = testCaseEntity.getRequestParam();
		//key1=value1##key2=value2
		String[] requestParams = requestParam.split("##");
		for(int i = 0 ;i<requestParams.length;i++){
			String key = requestParams[i].split("=")[0];
			String value = requestParams[i].split("=")[1];
			map.put(key, value);
		}		
		return map;
	}
}
