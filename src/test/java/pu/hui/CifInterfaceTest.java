package pu.hui;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;

import pu.hui.restauto.restassure.Data;
import pu.hui.restauto.restassure.ReadTestDatas;
import pu.hui.restauto.restassure.TestCaseBase;
import pu.hui.restauto.restassure.TestCaseEntity;

public class CifInterfaceTest extends TestCaseBase {

	static TestCaseEntity testCaseEntity = null;
	static {
		testCaseEntity = readTestCaseFromExcel("TestInterface01");
	}

	@Test(dataProvider = "create", dataProviderClass = ReadTestDatas.class)
	public void TestInterface01(Object data) {

		
		Data data1 = (Data) data;
		if("y".equalsIgnoreCase(data1.getIsRun())){
			
			JSONObject jsonObject = JSONObject.parseObject(data1.getRequest());
			testCaseEntity.setRequestParam(((Data) data).getRequest());
			if ("json".equals(testCaseEntity.getParamType())) {
				testCaseEntity = postJson(testCaseEntity);
			} else {
				testCaseEntity = postKeyValue(testCaseEntity);
			}
			// 判断返回结果是否包含期望值，判断结果封装至testCaseEntity的result属性中
			testCaseEntity = assertResult(testCaseEntity);
			// 将result回写至excel中
			setExcelValue(testCaseEntity,((Data) data));
			// 最终的断言
			Assert.assertTrue(testCaseEntity.isResult());

			System.out.println(((Data) data).getType());
		}
		
	}

}
