package com.americadigital.libupapi.Utils;

public class HeaderGeneric {
    public String severity;
    public int code;
    public String message;

    public HeaderGeneric(String severity, int code, String message) {
        this.severity = severity;
        this.code = code;
        this.message = message;
    }
}
