/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesador_de_archivos_de_logs;

import java.io.File;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 *
 * @author yojan
 */
public class ComprimirArchivos {
    
    public static void main(String ruta, String ruta_cmp){
        ComprimirArchivos compresor=new ComprimirArchivos();
        int[] arreglo   =   new int[2];
        compresor.compressErrorAndNonErrorFiles(ruta, ruta_cmp);
        //JOptionPane.showMessageDialog(null, "Cmp: "+ruta);
    }
    
    // Método para comprimir archivos en un archivo .rar
    public void compressFiles(List<File> files, String outputFilePath) {
        //System.out.println(outputFilePath);
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
    public void compressErrorAndNonErrorFiles(String ruta, String ruta_cmp) {
        List<File> txtFiles = getTxtFiles(ruta);
        List<File> errorFiles = new ArrayList<>();
        List<File> nonErrorFiles = new ArrayList<>();

        for (File file : txtFiles) {
            if (fileHasErrors(file)) {
                errorFiles.add(file);
            } else {
                nonErrorFiles.add(file);
            }
        }
//        JOptionPane.showMessageDialog(null, ruta);
        // Comprimir archivos con errores
        compressFiles(errorFiles, ruta_cmp+"\\Archivos_Con_Errores.rar");

        // Comprimir archivos sin errores
        compressFiles(nonErrorFiles, ruta_cmp+"\\Archivos_Sin_Errores.rar");

        System.out.println("Archivos comprimidos correctamente.");
    }
    public List<File> getTxtFiles(String ruta) {
        File folder = new File(ruta);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        List<File> txtFiles = new ArrayList<>();
        
        if (files != null) {
            for (File file : files) {
                txtFiles.add(file);
            }
        }
        return txtFiles;
    }
    
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
}
