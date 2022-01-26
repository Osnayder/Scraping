package edu.cecar.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class CSV {
    
    public static ArrayList readCSV(String file_name){
        ArrayList listaCorreo = new ArrayList<String>();;
        File file = new File(file_name);
        if(file.exists()){
            try {
                Reader reader = new FileReader(file);
                try {
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
                    
                    
                    for (CSVRecord csvRecord : records) {
                        listaCorreo.add(csvRecord.get(0));
                        System.out.println(csvRecord.get(0));
                    }
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        
        return listaCorreo;
    }
    
    public static void writerCSV(String file_name, Object object){
        File file = new File(file_name);
        
        if(object instanceof String){
            String correo = (String) object;
            ArrayList listaCorreo = null;
            
            if(!file.exists()){    
                try (CSVPrinter printer = new CSVPrinter(new FileWriter(file), CSVFormat.EXCEL.withDelimiter(';'))) {
                    printer.printRecord(correo);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }else{
                
                try {
                    Reader reader = new FileReader(file);
                    try {
                        
                        Iterable<CSVRecord> recordsOut = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
                        listaCorreo = new ArrayList<String>();
                        
                        for (CSVRecord csvRecord : recordsOut) {
                            if(csvRecord.get(0)!=""){
                                listaCorreo.add(csvRecord.get(0));
                            }
                        }

                        listaCorreo.add(correo);  
                        reader.close();
                        file.delete();                        
                    } catch (IOException ex) {
                        Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try (CSVPrinter printer = new CSVPrinter(new FileWriter(file_name), CSVFormat.EXCEL.withDelimiter(';'))) {
                    for (int i = 0; i<listaCorreo.size(); i++) {
                        printer.printRecords(listaCorreo.get(i));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
                }                        
            }
        }else{
            
        }
    }
     
    public static boolean deleteItemCSV(String file_name, String file_to_delete){
        File file = new File(file_name);
        ArrayList listaCorreo = null;
        boolean retorno = false;
        try {
            Reader reader = new FileReader(file);
            try {

                Iterable<CSVRecord> recordsOut = CSVFormat.EXCEL.withDelimiter(';').parse(reader);
                listaCorreo = new ArrayList<String>();
                
                for (CSVRecord csvRecord : recordsOut) {
                    if(!csvRecord.get(0).equals("") && !csvRecord.get(0).equals(file_to_delete)){
                        listaCorreo.add(csvRecord.get(0));
                    }
                }

                reader.close();
                file.delete();                        
            } catch (IOException ex) {
                Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (CSVPrinter printer = new CSVPrinter(new FileWriter(file_name), CSVFormat.EXCEL.withDelimiter(';'))) {
            for (int i = 0; i<listaCorreo.size(); i++) {
                printer.printRecords(listaCorreo.get(i));
                retorno = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }                      
        
        return retorno;
    }
    
}
