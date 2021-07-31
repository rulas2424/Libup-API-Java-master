package com.americadigital.libupapi.Utils;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class GenerateNameImg {
    public String generateUuidImage() {
        UUID generate = Generators.timeBasedGenerator().generate();
        return generate.toString();
    }
}
