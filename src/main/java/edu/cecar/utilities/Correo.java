package edu.cecar.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public final class Correo {
    
    private final MultiPartEmail email;
    private static String from;
    private final String to;
    private final String description_mail;
    private final String message;  
    private static String hostNameServer;
    private static int smtpPort; 
    
    private final EmailAttachment attachment;
    private final String name_attachment;
    private final String description_attachment;
    private static String url_attachment;
    

    public Correo(String to) {
        this.email = new MultiPartEmail();
        this.to = to;
        this.description_mail = "Reporte Covid-19 OMS";
        this.message = "Hoja de calculo con datos sobre casos de covid-19";
        Correo.smtpPort = 465; 
        
        this.attachment = new EmailAttachment();
        this.name_attachment = "Resporte_OMS.xls";
        this.description_attachment = "Archivo de excel con reporte de casos de covid-119";
        Correo.url_attachment = "Resport_OMS.xls";
        Correo.hostNameServer = "smtp.gmail.com";
        Correo.from = "jesus.valdes@cecar.edu.co";
        
    }
    
    public boolean send(){
        
        //Create the attachment    
        try {
            attachment.setPath(Correo.url_attachment);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription(this.description_attachment);
            attachment.setName(this.name_attachment);
            email.attach(attachment);
        } catch (EmailException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create email message and send
        try {
            
            email.setAuthentication("jesus.valdes@cecar.edu.co","valdes0125");
            email.setSmtpPort(Correo.smtpPort);
            email.setSSLOnConnect(true);
            email.setHostName(Correo.hostNameServer);
            email.setFrom(Correo.from, "Me");
            email.addTo(this.to);
            email.setSubject(this.description_mail);
            email.setMsg(this.message);
            email.send();
            return true;
        } catch (EmailException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }        
    }
    
}
