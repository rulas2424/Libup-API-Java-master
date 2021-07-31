package com.americadigital.libupapi.Utils;
import java.io.File;
public class DeleteImages {
    public String deleteImage(String pathImage) {

        File file = new File(pathImage);

        try {

            if (file.delete()) {

                return "Successfully delete image";

            } else {

                return "No existe ninguna coincidencia en el directorio";

            }

        } catch (Exception e) {

            System.out.println(e);

            return "error";

        }


    }
}
