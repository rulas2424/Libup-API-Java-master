package com.americadigital.libupapi.Utils;

import com.americadigital.libupapi.Exceptions.ConflictException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

public class SaveAudios {
    public String fileUpload(MultipartFile uploadedFile, String path) {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        String fileName = uploadedFile.getOriginalFilename();
        String[] extension = fileName.split("\\.");
        String result = UUID.randomUUID().toString();
        String replace = result.replace("-", "");
        int length = extension.length;
        String name = replace.substring(0, 15) + "." + extension[length - 1];
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
            throw new ConflictException(StringFormatUtilities.exceptionMsgBuilder(e));
        }
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new ConflictException(StringFormatUtilities.exceptionMsgBuilder(e));
        }
        return name;
    }
}
