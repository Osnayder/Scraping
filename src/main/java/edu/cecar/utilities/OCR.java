package edu.cecar.utilities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {

    public static String run(Image image, Rectangle rect){
        
        String textExtracted = "";
        BufferedImage bufferedImage = new BufferedImage(rect.width * 2, rect.height * 2, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("D:/Tesseract-OCR/tessdata");
            
            textExtracted = tesseract.doOCR(bufferedImage);
            
        } catch (TesseractException ex) {
            Logger.getLogger(OCR.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return textExtracted;
    }
    
}
