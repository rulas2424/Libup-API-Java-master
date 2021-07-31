package com.americadigital.libupapi.Utils;

public class GetExtensions {
    public static String getExtensionImage(String profilePicture){
        char caracter = profilePicture.charAt(0);

        if(caracter == 'i'){
            return ".png";
        } else if(caracter == '/'){
            return ".jpg";
        }
        return ".png";
    }
}
