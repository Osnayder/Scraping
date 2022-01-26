package edu.cecar.utilities;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class RegExp {
    
    public static boolean validateEmail(String expresion){
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[A-Za-z0-9-]+)*@[A-za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(expresion);
        
        return mather.find();
    }
}
