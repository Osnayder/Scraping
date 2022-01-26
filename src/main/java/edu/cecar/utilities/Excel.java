package edu.cecar.utilities;

import edu.cecar.models.Hoja;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;


public final class Excel {
    
    public static boolean createFile(Hoja[] listOfSheet){
        boolean state = true;
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        //se crea el contenido de la cabecera
        String[] headers = new String[]{
                "Total confirmed cases",
                "Total confirmed new cases",
                "Total deaths",
                "Total new deaths"
        };
          
        //se crean las hojas
        for(int i=0; i<10; i++){
            if(!createSheet(workbook,"Hoja "+(i+1), headers,listOfSheet[i])){ 
                state = false;
            }
        }
        return state;
    }

    private static boolean createSheet(HSSFWorkbook workbook, String nameSheet, String[] headerSheet, Hoja bodySheet){
        HSSFSheet sheet = workbook.createSheet(nameSheet);
        
        String[] headers = headerSheet;

        //se crea los estilos de la cabecera las hojas
        CellStyle headerStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        //se crean los estilos para el cuerpo de las hojas
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //se crea la cabecera de la hoja
        HSSFRow headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; ++i) {
            String header = headers[i];
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }

        //se crea el cuerpo de la hoja
        for (int i = 0; i <5; ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);

            dataRow.createCell(0).setCellValue(Double.parseDouble(bodySheet.getToConfCases().get(i)));
            dataRow.createCell(1).setCellValue(Double.parseDouble(bodySheet.getToConfNewCases().get(i)));
            dataRow.createCell(2).setCellValue(Double.parseDouble(bodySheet.getTotalDeaths().get(i)));
            dataRow.createCell(3).setCellValue(Double.parseDouble(bodySheet.getTotalNewDeaths().get(i)));
        }
        
        try(FileOutputStream  file = new FileOutputStream("Resport_OMS.xls")) {
            workbook.write(file);
            file.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        
}
