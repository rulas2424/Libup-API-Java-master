package com.americadigital.libupapi.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveImages {
    public String saveImages(String base64Image, String pathImages, String nameImage, String extension) throws IOException {
        DecodeBase64 decodeBase64 = new DecodeBase64();
        BufferedImage bufferedImage = decodeBase64.decodeToImage(base64Image);
        String imageName = nameImage + extension;
        File file = new File(pathImages + imageName);
        String substring = extension.substring(1);
        ImageIO.write(bufferedImage, substring, file);
        return imageName;
    }
}
