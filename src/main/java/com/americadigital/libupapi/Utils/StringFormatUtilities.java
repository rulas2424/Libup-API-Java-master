package com.americadigital.libupapi.Utils;

public class StringFormatUtilities {
    public static String exceptionMsgBuilder(Exception e) {
        String errorMsg = "Error En la app: ";

        errorMsg += e.getMessage() + " " + e.getLocalizedMessage();

        if (e.getCause() != null) {
            errorMsg += " Cause: " + e.getCause().getCause().toString();
        }

        return errorMsg;
    }
}
