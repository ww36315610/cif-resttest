package pu.hui.restauto.restassure;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.testng.annotations.DataProvider;

import pu.hui.util.ExcelSheet;
import pu.hui.util.ExcelUtil;

public class ReadTestDatas {
	
	private static ExcelUtil eu = new ExcelUtil(
			TestCaseBase.class.getClassLoader().getResource("cif-interface-test.xls").getPath());
	private static ExcelSheet excelSheet = eu.getSheet("Datas");

	public static void main(String[] args) {

		Iterator it = getDataIterator();
		
		while(it.hasNext()){
			Data data = (Data)it.next();
			System.out.println(data.getRequest());
		}
		
	}
	
	@DataProvider(name = "create")
	public static Iterator getDataIterator(){
		Datas datas = new Datas();
		int rowCount = excelSheet.getRowCount();
		TestCaseEntity testCaseEntity = new TestCaseEntity();
		for (int i = 1; i < rowCount; i++) {
			
			Data data = new Data();
			Row row = excelSheet.getRow(i);
			int columnSize = row.getPhysicalNumberOfCells();
			data.setIsRun(row.getCell(1).getStringCellValue());
			data.setCatagory(row.getCell(2).getStringCellValue());
			data.setType(row.getCell(4).getStringCellValue());
			data.setRequest(row.getCell(5).getStringCellValue());
			data.setRow(i);
			datas.addItem(data);
		}
		
		return datas.createIrerator();
		
	}

}
