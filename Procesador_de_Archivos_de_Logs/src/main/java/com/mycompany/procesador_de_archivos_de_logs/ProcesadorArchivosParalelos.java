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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcesadorArchivosParalelos {
    private String directoryPath;
    private List<File> txtFiles = new ArrayList<>();
    private int errorFilesCount = 0;
    private int nonErrorFilesCount = 0;

    public ProcesadorArchivosParalelos(String directoryPath) {
        this.directoryPath = "C:/Users/yojan/OneDrive/Documents/GitHub/Paralelo en java/Procesador_de_Archivos_de_Logs/logs";
    }

    public void getTxtFiles() {
        File folder = new File("C:/Users/yojan/OneDrive/Documents/GitHub/Paralelo en java/Procesador_de_Archivos_de_Logs/logs");
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                txtFiles.add(file);
            }
        }
    }

    public void countFilesWithErrors() {
        for (File file : txtFiles) {
            if (fileHasErrors(file)) {
                errorFilesCount++;
            } else {
                nonErrorFilesCount++;
            }
        }
    }

    private boolean fileHasErrors(File file) {
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

    public void processLogsInParallel() {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        executor.submit(this::getTxtFiles);
        executor.submit(this::countFilesWithErrors);
        // Puedes agregar otros hilos para la compresión y el envío del correo aquí.

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Esperar a que todos los hilos terminen
        }

        System.out.println("Archivos con errores: " + errorFilesCount);
        System.out.println("Archivos sin errores: " + nonErrorFilesCount);
    }

    public static void main(String[] args) {
        String logDirectory = "C:/Users/yojan/OneDrive/Documents/GitHub/Paralelo en java/Procesador_de_Archivos_de_Logs/logs"; // Cambia esto por tu directorio
        ProcesadorArchivosParalelos processor = new ProcesadorArchivosParalelos(logDirectory);
        processor.processLogsInParallel();
    }
}
