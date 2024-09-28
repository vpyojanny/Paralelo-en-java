/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.procesador_de_archivos_de_logs;

/**
 *
 * @author yojan
 */
//import com.sun.jdi.connect.Transport;
import javax.mail.Transport;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.File;


public class EnvioCorreo {
    
    private String host;
    private String port;
    private String user;
    private String password;

    public EnvioCorreo(String host, String port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public void sendEmailWithAttachments(String toAddress, String subject, String message)
            throws MessagingException {
        
        // Configuración de propiedades del servidor de correo
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Autenticación de usuario
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };

        // Crear una sesión de correo
        Session session = Session.getInstance(properties, auth);

        // Crear el mensaje de correo
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(user));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new java.util.Date());

        // Crear el cuerpo del mensaje
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // Crear multipart para contener el mensaje y archivos adjuntos
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Agregar los archivos adjuntos
//        if (attachFiles != null && attachFiles.length > 0) {
//            for (String filePath : attachFiles) {
//                MimeBodyPart attachPart = new MimeBodyPart();
//
//                try {
//                    attachPart.attachFile(new File(filePath));
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//                multipart.addBodyPart(attachPart);
//            }
//        }

        // Establecer el contenido del correo
        msg.setContent(multipart);

        // Enviar el correo
        Transport.send(msg);

        System.out.println("Correo enviado con éxito.");
    }

    public static void main() {
        // Configuración de ejemplo para SMTP (Gmail)
        String host = "smtp.gmail.com";
        String port = "587";
        String user = "yojannyvp@gmail.com";  // Tu correo
        String password = "kxrs subb cyjx ftda";  // Tu contraseña

        // Receptor del correo
        String toAddress = "yojannyvp@outlook.es";
        String subject = "Reporte de Archivos de Logs";
        String message = "Puede encontrar los archivos en detalle y agrupados en "
                + "archivos .RAR en el enlace:\n\thttps://drive.google.com/drive/folders/1rrnAwRPLm8vq-OBCKfzG01mWKT2BacY8?usp=sharing";

//        // Archivos adjuntos
//        String[] attachFiles = new String[2];
//        attachFiles[0] = "logs_with_errors.rar";  // Archivo con errores
//        attachFiles[1] = "logs_without_errors.rar";  // Archivo sin errores

        try {
            EnvioCorreo mailer = new EnvioCorreo(host, port, user, password);
            mailer.sendEmailWithAttachments(toAddress, subject, message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
