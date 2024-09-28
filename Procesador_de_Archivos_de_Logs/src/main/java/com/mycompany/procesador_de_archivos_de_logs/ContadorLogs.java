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
/**
 *
 * @author yojan
 */
public class ContadorLogs {
    public static int[] main(String ruta){
        ContadorLogs contador=new ContadorLogs();
        int[] arreglo   =   new int[2];
        
        arreglo[0]  =   contador.countFilesWithErrors(ruta);
        arreglo[1]  =   contador.countFilesWithoutErrors(ruta);
        
        return arreglo;
    }
    public int countFilesWithErrors(String ruta) {
        List<File> txtFiles = getTxtFiles(ruta);
        int errorFilesCount = 0;
        for (File file : txtFiles) {
            if (fileHasErrors(file)) {
                errorFilesCount++;
            }
        }
        return errorFilesCount;
    }

    // Mostrar la cantidad de archivos sin errores
    public int countFilesWithoutErrors(String ruta) {
        List<File> txtFiles = getTxtFiles(ruta);
        int nonErrorFilesCount = 0;
        for (File file : txtFiles) {
            if (!fileHasErrors(file)) {
                nonErrorFilesCount++;
            }
        }
        return nonErrorFilesCount;
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
