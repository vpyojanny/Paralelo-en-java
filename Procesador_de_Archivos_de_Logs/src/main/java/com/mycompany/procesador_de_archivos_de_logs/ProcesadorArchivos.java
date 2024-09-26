/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesador_de_archivos_de_logs;

/**
 *
 * @author yojan
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorArchivos {
    
    // Directorio donde están los logs
    private String directoryPath;
    
    public ProcesadorArchivos(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    // Verificar archivos en la carpeta
    public List<File> getTxtFiles() {
        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        List<File> txtFiles = new ArrayList<>();
        
        if (files != null) {
            for (File file : files) {
                txtFiles.add(file);
            }
        }
        return txtFiles;
    }

    // Verificar si un archivo tiene errores (simularemos con una búsqueda de la palabra "ERROR")
    public boolean fileHasErrors(File file) {
        try {
            Path path = Paths.get(file.getAbsolutePath());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.contains("ERROR")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mostrar la cantidad de archivos que tienen errores
    public int countFilesWithErrors() {
        List<File> txtFiles = getTxtFiles();
        int errorFilesCount = 0;
        for (File file : txtFiles) {
            if (fileHasErrors(file)) {
                errorFilesCount++;
            }
        }
        return errorFilesCount;
    }

    // Mostrar la cantidad de archivos sin errores
    public int countFilesWithoutErrors() {
        List<File> txtFiles = getTxtFiles();
        int nonErrorFilesCount = 0;
        for (File file : txtFiles) {
            if (!fileHasErrors(file)) {
                nonErrorFilesCount++;
            }
        }
        return nonErrorFilesCount;
    }
    
     // Método para comprimir archivos en un archivo .rar
    public void compressFiles(List<File> files, String outputFilePath) {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (File fileToZip : files) {
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Comprimir archivos con errores y sin errores
    public void compressErrorAndNonErrorFiles() {
        List<File> txtFiles = getTxtFiles();
        List<File> errorFiles = new ArrayList<>();
        List<File> nonErrorFiles = new ArrayList<>();

        for (File file : txtFiles) {
            if (fileHasErrors(file)) {
                errorFiles.add(file);
            } else {
                nonErrorFiles.add(file);
            }
        }

        // Comprimir archivos con errores
        compressFiles(errorFiles, "logs_with_errors.rar");

        // Comprimir archivos sin errores
        compressFiles(nonErrorFiles, "logs_without_errors.rar");

        System.out.println("Archivos comprimidos correctamente.");
    }

    public void processLogsAndSendEmail() {
        String logDirectory = "C:/Users/yojan/OneDrive/Documents/GitHub/Paralelo en java/Procesador_de_Archivos_de_Logs/logs"; // Cambia esto por tu directorio
        ProcesadorArchivos processor = new ProcesadorArchivos(logDirectory);

        int errorFiles = processor.countFilesWithErrors();
        int nonErrorFiles = processor.countFilesWithoutErrors();

        System.out.println("Archivos con errores: " + errorFiles);
        System.out.println("Archivos sin errores: " + nonErrorFiles);

        // Comprimir archivos
        processor.compressErrorAndNonErrorFiles();

        // Enviar correo con archivos adjuntos
        String host = "smtp.gmail.com";
        String port = "587";
        String user = "yojannyvp@gmail.com";
        String password = "TechFun-27112015";

        String toAddress = "yojannyvp@outlook.es";
        String subject = "Reporte de archivos logs";
        String message = "Se adjuntan los archivos .rar con los logs procesados.";

        String[] attachFiles = new String[2];
        attachFiles[0] = "logs_with_errors.rar";
        attachFiles[1] = "logs_without_errors.rar";

        try {
            EnvioCorreo mailer = new EnvioCorreo(host, port, user, password);
            mailer.sendEmailWithAttachments(toAddress, subject, message, attachFiles);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ProcesadorArchivos logProcessor = new ProcesadorArchivos();
        logProcessor.processLogsAndSendEmail();
    }
}