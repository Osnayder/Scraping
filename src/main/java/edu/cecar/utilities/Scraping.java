package edu.cecar.utilities;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import edu.cecar.models.Data;
import edu.cecar.models.Hoja;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Scraping {
    
    private static Hoja[] listOfSheet = new Hoja[10];
    
    public static Hoja[] spaider(String folder) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folder))) {
            for (Path file: stream) {
                run(folder+'/'+file.getFileName());
            }
            
            return listOfSheet;
        } catch (IOException | DirectoryIteratorException ex) {
            System.err.println(ex);
            return null;
        }
    }
    
    private static void run(String file_path){
        
        try {
            File file = new File(file_path);
            
            /* loading pdf*/
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdffile = new PDFFile(buf);
            
            /*  iterate through the number of pages */
            int num_of_pages = pdffile.getNumPages();
            for (int i = 1; i <= num_of_pages; i++) {
                PDFPage page = pdffile.getPage(i);
                
                /* create rectangle of selection*/
                double widthpdf = page.getBBox().getWidth();
                double heightpdf = page.getBBox().getHeight() - ((page.getBBox().getHeight()*0.35));
                Rectangle rect = new Rectangle(0, 0, (int)widthpdf,(int)heightpdf);
                
                /*  create new image */
                Image img = page.getImage( 
                        rect.width * 2, rect.height * 2, //width & height
                        rect, // clip rect
                        null, // null for the ImageObserver
                        true, // fill background with white
                        true // block until drawing is done
                );

                
                //si encuentra la informacion. entonces que no siga buscando en las siguientes páginas
                if(toLocate(OCR.run(img, rect),"United States of America")){
                    
                    int numeroSecciones = 4;
                    int movimientoX = 180;
                    int incremento = 84;
                    Data data = new Data();
                    
                    // Se extrae cada seccion de datos
                    for(int j=0; j<numeroSecciones; j++){
                        double widthpdf2 = page.getBBox().getWidth() - ((page.getBBox().getWidth()*0.9));
                        double heightpdf2 = page.getBBox().getHeight() - ((page.getBBox().getHeight()*0.35));
                        Rectangle rect2 = new Rectangle(movimientoX, 0, (int)widthpdf2,(int)heightpdf2);
                        Image imgFila1 = page.getImage(rect2.width * 2, rect2.height * 2,rect2,null,true,true);
                        movimientoX = movimientoX + incremento;
                        
                        switch(j){
                            case 0: 
                                data.setToConfCases(clearData(OCR.run(imgFila1, rect2)));
                                break;
                            case 1:
                                data.setToConfNewCases(clearData(OCR.run(imgFila1, rect2)));
                                break;
                            case 2: 
                                data.setTotalDeaths(clearData(OCR.run(imgFila1, rect2)));
                                break;
                            case 3: 
                               data.setTotalNewDeaths(clearData(OCR.run(imgFila1, rect2)));
                                break;
                        }
                    }
                    
                    //mostrar por consola los datos extraidos
                    System.out.println("\nLos datos estraidos son: ");
                    for(int p=0; p<10; p++){
                        System.out.println(data.getToConfCases().get(p) +" - "+data.getToConfNewCases().get(p)+" - "+
                                           data.getTotalDeaths().get(p)+" - "+data.getTotalNewDeaths().get(p));
                    }
                    
                    //organizar datos segun la hojas
                    for(int p=0; p<10; p++){
                        if(listOfSheet[p]==null){
                            listOfSheet[p] = new Hoja();
                        }
                        listOfSheet[p].setToConfCases(data.getToConfCases().get(p));
                        listOfSheet[p].setToConfNewCases(data.getToConfNewCases().get(p));
                        listOfSheet[p].setTotalDeaths(data.getTotalDeaths().get(p));
                        listOfSheet[p].setTotalNewDeaths(data.getTotalNewDeaths().get(p));
                    }
                    
                    
                    //setencia que permite dejar se seguir de buscando
                    break;
                }
            }
            
            
            buf.clear();
            channel.close();
            raf.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Scraping.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Scraping.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static boolean toLocate(String cadena, String condition){
        boolean estado = false;
        
        System.out.println("Procesando página...");
        if(cadena.contains(condition)){
            System.out.println("Página encontrada: \n");
            estado = true;
        }
        
        return estado;
    }
    
    private static ArrayList<String> clearData(String cadena){
        ArrayList<String> listaDatos = new ArrayList<>(); 
        String puente = "";
        
        for(int i=0; i<cadena.length(); i++){
            if(cadena.charAt(i)!='\n'){
                if(isNumeric(cadena.charAt(i)+"")){
                    if(cadena.charAt(i)=='i'){
                        puente = puente + 1;
                    }else{
                        puente = puente + cadena.charAt(i);
                    }
                    
                }
            }else{
                if(!puente.equals("")){
                    listaDatos.add(puente);
                    puente = "";
                }else{
                   puente = "";
                }
            }
        }
        
        return listaDatos;
    }
    
    public static boolean isNumeric(String str) {
        boolean returno = true;
        if(str.equals(".") || str.equals("_") || str.equals("-")){
            returno = false;
        }
        if(str.equals("i")){
            return true;
        }
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return returno; 
    }
}
