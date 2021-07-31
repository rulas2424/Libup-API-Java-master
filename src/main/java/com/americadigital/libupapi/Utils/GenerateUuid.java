package com.americadigital.libupapi.Utils;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class GenerateUuid {
    public String generateUuid(){
        UUID generate = Generators.timeBasedGenerator().generate();
        String uuid = generate.toString().substring(0, 20);
        String replace = uuid.replace("-", "");
        return replace;
    }
}
