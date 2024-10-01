package com.example.checkerAge.Service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.IOExceptionList;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OCRService {

    public String realizarOCR(MultipartFile imagen) throws IOException {

        File archivoTemporal = File.createTempFile("cedula", ".png");

        ITesseract tesseract = new Tesseract();

        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");

        tesseract.setLanguage("spa");

    try{
        BufferedImage bufferedImage = ImageIO.read(imagen.getInputStream());

        String textoExtraido = tesseract.doOCR(bufferedImage);

        archivoTemporal.delete();

        return textoExtraido;
    } catch (TesseractException | IOException e) {

        e.printStackTrace();
        return "Error al procesar la imagen";

    }
}
}
