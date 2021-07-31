package com.americadigital.libupapi.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class SaveFiles {
    public String fileUpload(MultipartFile uploadedFile, String path) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = uploadedFile.getOriginalFilename();
        String[] extension = fileName.split("\\.");
        String result = new GenerateUuid().generateUuid();
        int length = extension.length;
        String name = result + "." + extension[length - 1];
        File newFile = new File(path + name);
        try {
            inputStream = uploadedFile.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
}
