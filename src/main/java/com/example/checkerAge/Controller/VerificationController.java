package com.example.checkerAge.Controller;

import com.example.checkerAge.Model.DatosCedula;
import com.example.checkerAge.Service.OCRService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    private OCRService ocrService;
    private DatosCedula datosCedula;

    public VerificationController(OCRService ocrService, DatosCedula datosCedula) {
        this.ocrService = ocrService;
        this.datosCedula = datosCedula;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyDocument(@RequestParam("imagen")MultipartFile imagen) throws Exception {

        String textoExtraido = ocrService.realizarOCR(imagen);

        if(!isCedulaValida(textoExtraido)){
            return ResponseEntity.badRequest().body("Documento invalido");
        }

        DatosCedula datos = extraerDatosCedula(textoExtraido);

        boolean esMayorEdad = verificarDatosCedula(textoExtraido);

        if (esMayorEdad){
            return ResponseEntity.ok("Verificacion exitosa. Usuario Mayor de edad");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario menor de edad. Acceso denegado")
        }

    }

    private boolean isCedulaValida(String textoExtraido){


        return textoExtraido.contains("Republica de Colombia") && textoExtraido.contains("Cédula de ciudadanía");

    }

    private DatosCedula extraerDatosCedula(String textoExtraido){

        String nombre = extraerNombre(textoExtraido);
        LocalDate fechaNacimiento = extraerFechaNacimiento(textoExtraido);
        String numeroCedula = extraerNumeroCedula(textoExtraido);
        //TODO crear metodo para extraerNumeroCedula

        return new DatosCedula(nombre, numeroCedula, fechaNacimiento);

    }

    private boolean verificarMayorEdad(DatosCedula datosCedula){
        LocalDate fechaNacimiento = LocalDate.parse(datosCedula.getFechaNacimiento());
        //TODO corregir esto
        LocalDate fechaActual = LocalDate.now();

        int edad = Period.between(fechaNacimiento, fechaActual).getYears();

        return edad >= 18;
    }

    private String extraerNombre(String texto){

        Pattern pattern = Pattern.compile("([A-ZÁÉÍÓÚÑ]+\\s[A-ZÁÉÍÓÚÑ]+\\s[A-ZÁÉÍÓÚÑ]+)");
        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()){
            return matcher.group(1);
        }

        return "Nombre no encontrado";

    }

    private LocalDate extraerFechaNacimiento(String texto){
        Pattern pattern = Pattern.compile("\\b(\\d{2}/\\d{2}/\\d{4})\\b");
        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()){
            return matcher.group(1);
        }

        return "Fecha nacimiento no encontrada";
    }




}
