package com.americadigital.libupapi.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertToDate {
    public Date convertDate(String fecha) throws ParseException {
        Date date=new SimpleDateFormat("dd/MM/yyyy hh:mm a").parse(fecha);
        return date;
    }

    public String convertirFecha(String fecha){
        //Input date in String format
        //Date/time pattern of input date
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        //Date/time pattern of desired output date
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        Date date = null;
        String output = null;
        try{
            //Conversion of input String to date
            date= df.parse(fecha);
            //old date format to new date format
            output = outputformat.format(date);

        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return output;
    }
}
