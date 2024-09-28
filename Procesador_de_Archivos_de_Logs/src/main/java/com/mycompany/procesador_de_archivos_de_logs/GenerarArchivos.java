/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesador_de_archivos_de_logs;

/**
 *
 * @author yojan
 */
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GenerarArchivos {

    public static String main(String ruta) {
        Pantalla_Principal main_p = new Pantalla_Principal();
        String logDirectory    =   ruta;
        main_p.txt_Results.setText(logDirectory);
        //JOptionPane.showMessageDialog(null, "Ej: "+logDirectory);
        String txt_area =   "";
// Cambia esto por tu directorio de pruebas

        // Generar logs con errores
        for (int i = 1; i <= 264; i++) {
            String logFileName = logDirectory + "/log_error_" + i + ".txt";
            String logContent = generateErrorLog(i);
            createLogFile(logFileName, logContent);
        }

        // Generar logs sin errores
        for (int i = 1; i <= 523; i++) {
            String logFileName = logDirectory + "/log_no_error_" + i + ".txt";
            String logContent = generateNoErrorLog(i);
            createLogFile(logFileName, logContent);
        }
        //JOptionPane.showMessageDialog(null, "Ej2: "+txt_area);
        txt_area   =   "\nArchivos de log generados con éxito en el directorio "+logDirectory+".";
        
        return txt_area;
        //main_p.txt_Results.setText(txt_area);
    }

    // Método para generar contenido de logs con errores
    private static String generateErrorLog(int index) {
        return "[2024-09-25 10:23:11] ERROR: Sample error message for log " + index + "\n" +
               "[2024-09-25 10:24:15] INFO: Retrying action in log " + index + "\n" +
               "[2024-09-25 10:24:16] ERROR: Another error occurred in log " + index + ".";
    }

    // Método para generar contenido de logs sin errores
    private static String generateNoErrorLog(int index) {
        return "[2024-09-25 09:01:11] INFO: Sample info message for log " + index + "\n" +
               "[2024-09-25 09:05:22] INFO: All operations completed successfully in log " + index + ".";
    }

    // Método para crear el archivo de log en disco
    private static void createLogFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
