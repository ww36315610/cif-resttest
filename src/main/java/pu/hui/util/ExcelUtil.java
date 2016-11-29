package pu.hui.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;  
  
public class ExcelUtil {  
  
    private boolean override;  
    private String file;  
    HSSFWorkbook wb = new HSSFWorkbook();  
    List<ExcelSheet> sheets = new ArrayList<ExcelSheet>();  
  
    private HSSFCellStyle titleStyle;  
 
    public ExcelUtil(String file) {  
        this(file, false);  
    }  
  
    public ExcelUtil(String file, boolean override) {  
        this.file = file;  
        this.override = override;  
        File f=new File(file);  
        if (override) {  
            // delete the exsited one  
        }  
        try {  
            if(f.exists()){  
                wb= new HSSFWorkbook(new FileInputStream(file)); 
                HSSFCellStyle cellStyle =wb.createCellStyle();
                cellStyle.setFillBackgroundColor(HSSFColor.RED.index);
            }else{  
                wb= new HSSFWorkbook();  
            }  
            titleStyle = wb.createCellStyle();  
            HSSFFont titleFont = wb.createFont();  
            titleFont.setColor(HSSFFont.COLOR_RED);  
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
            titleStyle.setFont(titleFont);  
              
            // create the Excel file.  
            int num=wb.getNumberOfSheets();  
            for (int i = 0; i < num; i++) {  
                HSSFSheet sheet = wb.getSheetAt(i);  
                String name=wb.getSheetName(i);  
                sheets.add(new ExcelSheet(name,sheet,titleStyle));  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * @author Stony Zhang 
     * @date Feb 23, 2009 
     * @param sheetName 
     *            If can't find the sheet, new one. 
     * @return 
     */  
    
    
    public List<String> getSheetNames(){
    	int num = wb.getNumberOfSheets();
    	List<String> sheetNames = new ArrayList<String>();

    	for(int i=0;i<num ;i++){
    		String sheetName = wb.getSheetName(i);
    		sheetNames.add(sheetName);
    	}
    	
    	return sheetNames;
    }
    public ExcelSheet getSheet(String sheetName) {  
        for (ExcelSheet esh : this.sheets) {  
            if (esh.getName().equalsIgnoreCase(sheetName)) {  
                return esh;  
            }  
        }  
        HSSFSheet sheet = wb.createSheet(sheetName);  
        return new ExcelSheet(sheetName, sheet,titleStyle);  
    }  
  
    public void save() {  
        try {  
            FileOutputStream fileOut = new FileOutputStream(file);  
            wb.write(fileOut);  
            fileOut.close();  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
    }  
      
   
  
}  