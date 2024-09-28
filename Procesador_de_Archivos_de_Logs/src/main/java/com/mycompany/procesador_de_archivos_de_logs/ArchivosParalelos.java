/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesador_de_archivos_de_logs;

import java.io.IOException;

/**
 *
 * @author yojan
 */
public class ArchivosParalelos {

    public void iniciarProcesosInternos() {
        try {
            // Proceso para contar archivos con errores
            ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "bin/", "com.mycompany.procesador_de_archivos_de_logs.CountErrorFiles");
            Process process1 = pb1.start();

            // Proceso para comprimir archivos
            ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "bin/", "com.mycompany.procesador_de_archivos_de_logs.CompressFiles");
            Process process2 = pb2.start();

            // Proceso para enviar correos
            ProcessBuilder pb3 = new ProcessBuilder("java", "-cp", "bin/", "com.mycompany.procesador_de_archivos_de_logs.SendEmail");
            Process process3 = pb3.start();

            // Esperamos que terminen
            process1.waitFor();
            process2.waitFor();
            process3.waitFor();

            System.out.println("Todos los procesos han terminado.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
