package pu.hui.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Stony Zhang
 * @date Feb 23, 2009
 * @return
 */
public class ExcelSheet {
	private HSSFSheet sheet;
	private String name;
	private String[] header;
	private HSSFCellStyle titleStyle;
	private int rowCount;
	

	public ExcelSheet(String sheetName, HSSFSheet sh) {
		this.name = sheetName;
		this.sheet = sh;
		this.rowCount = this.sheet.getPhysicalNumberOfRows();
		sheet.setDisplayGridlines(true);
	}

	public ExcelSheet(String sheetName, HSSFSheet sh, HSSFCellStyle titleStyle) {
		this.name = sheetName;
		this.sheet = sh;
		this.titleStyle = titleStyle;
		this.rowCount = this.sheet.getPhysicalNumberOfRows();
		
	}



	

	private String getCellValueAsString(Cell cell) {

		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) { // 日期类型
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
					value = sdf.format(date);
				} else {
					Long data = (long) cell.getNumericCellValue();
					value = data.toString();
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				Boolean data = cell.getBooleanCellValue();
				value = data.toString();
				break;
			case Cell.CELL_TYPE_ERROR:
				// System.out.println("单元格内容出现错误");
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN")) {// 如果获取的数据值非法,就将其装换为对应的字符串
					value = cell.getStringCellValue().toString();
				}
				break;
			case Cell.CELL_TYPE_BLANK:
				// System.out.println("单元格内容 为空值 ");
				break;
			default:
				value = cell.getStringCellValue().toString();
				break;
			}
		}
		return value;
	}



	public int getRowCount() {
		return this.sheet.getPhysicalNumberOfRows();

	}

	public void addRecord(String[] record) {
		if (header != null) {
			if (header.length != record.length) {
				return;
			}
		}

		fillContent(record, sheet.getLastRowNum() + 1, null);

	}

	public String[] getHeader() {
		return this.header;
	}

	public void setHeader(String[] header) {
		this.header = header;
		fillContent(header, 0, this.titleStyle);
	}

	private void fillContent(String[] crow, int rowNum, HSSFCellStyle style) {
		HSSFRow row = sheet.createRow((short) rowNum);

		for (int i = 0; i < crow.length; i++) {
			String s = crow[i];
			HSSFCell cell = row.createCell((short) i);
			if (style != null) {
				cell.setCellStyle(style);
			}
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(s);
		}
	}

	public void setValue(int rowNum, int colNum, String value) {
		HSSFRow row = this.sheet.getRow(rowNum);
		HSSFCell cell = row.getCell((short) colNum);
		if (cell == null) {
			cell = row.createCell((short) colNum);
		}
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}
	
	public void setValue(int rowNum, int colNum, String value,boolean cellStyle) {
		HSSFRow row = this.sheet.getRow(rowNum);
		HSSFCell cell = row.getCell((short) colNum);
		if (cell == null) {
			cell = row.createCell((short) colNum);
		}
		cell.setCellStyle(titleStyle);
		cell.setCellValue(value);
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public void addRecord(ArrayList<String[]> arr) {
		for (String[] row : arr) {
			this.addRecord(row);
		}

	}

	public List<String[]> getRecords() {
		ArrayList<String[]> vs = new ArrayList<String[]>();
		for (int j = 0; j < this.sheet.getLastRowNum(); j++) {
			HSSFRow row = this.sheet.getRow(j);
			ArrayList<String> cellsStr = new ArrayList<String>();
			for (short k = 0; k < row.getLastCellNum(); k++) {
				HSSFCell cell = row.getCell(k);
				if (cell != null) {
					cellsStr.add(cell.getStringCellValue());
				}
			}
			System.out.println("row=" + j + " values=" + cellsStr.toArray(new String[0]));
			vs.add(cellsStr.toArray(new String[0]));
		}

		return vs;
	}

	public List<String> getAllValuesOfColum(short i) {
		ArrayList<String> vs = new ArrayList<String>();
		for (int j = 1; j <= this.sheet.getLastRowNum(); j++) {
			HSSFRow row = this.sheet.getRow(j);
			HSSFCell cell = row.getCell(i);
			vs.add(cell.getStringCellValue());
		}
		return vs;
	}

	public String getCellValue(int i, int j) {
		// getCellValueAsString
		Cell cell = this.sheet.getRow(i).getCell(j);
		String cellValue = getCellValueAsString(cell);
		// String cellValue =
		// this.sheet.getRow(i).getCell(j).getStringCellValue();
		return cellValue;
	}
	//
	

	

	public String getCellValule(int rowNum, int colNum) {
		
		return this.getCellValueAsString(this.sheet.getRow(rowNum).getCell(colNum));
	}

	public Row getRow(int rowIndex) {
		return this.sheet.getRow(rowIndex);
	}
	
	public ArrayList<String> getallzb(){
		
		ArrayList<String> zbxs = new ArrayList<String>();
		int rowCount = this.sheet.getPhysicalNumberOfRows();
		for(int i =0 ;i<rowCount;i++){
			Row row = this.sheet.getRow(i);
			String zbx = row.getCell(0).getStringCellValue();
			zbxs.add(zbx);
		}
		
		return zbxs;
		
	}
	
}
