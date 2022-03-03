package com.example.demo.excel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.email.AwsSesExample;
public class TypeExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<TypeDate> listData;
    
    public TypeExcelExporter(List<TypeDate> listData) {
        this.listData = listData;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(13);
        style.setFont(font);
         
        //createCell(row, 0, "Project ID", style);      
        createCell(row, 0, "Type", style); 
        createCell(row, 1, "Description", style); 
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (TypeDate typeDate : listData) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            //createCell(row, columnCount++, typeDate.getProjectId(), style);
            createCell(row, columnCount++, typeDate.getTypeName(), style);
            createCell(row, columnCount++, typeDate.getTypeDes(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        
        outputStream.close();
         
    }
    
    public ByteArrayOutputStream export1()  throws IOException{
    	 writeHeaderLine();
         writeDataLines();
          
        // ServletOutputStream outputStream = response.getOutputStream();
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         workbook.write(outputStream);
         workbook.close();
         
         return outputStream;
         
         
    }
}
