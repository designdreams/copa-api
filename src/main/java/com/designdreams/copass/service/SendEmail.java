package com.designdreams.copass.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.net.www.MimeTable;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class SendEmail {

    @Value("${USER_TOKEN}")
    private String from;

    @Value("${ACCESS_TOKEN}")
    private String password;

    @Value("${HOST_VAL}")
    private String host;

    @Value("${HOST_KEY}")
    private String hostKey;

    @Value("${PORT_KEY}")
    private String portKey;

    @Value("${PORT_VAL}")
    private String portValue;

    @Value("${ENABLE_KEY}")
    private String enableKey;

    @Value("${AUTH_KEY}")
    private String authKey;

    @Value("${DEFAULT_SUBJECT_MESSAGE}")
    private String defaultSubjectMessage;

    public  void email (String to, String dest){

        try{
            Properties properties = new Properties();
            properties.put(hostKey, host);
            properties.put(portKey, portValue);
            properties.put(enableKey, true);
            properties.put(authKey, true);
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from,password);
                }
            });
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            Multipart multipart = new MimeMultipart();



            message.setSubject(defaultSubjectMessage + "" + dest + " !");

            message.setText("A message to your inbox");

            System.out.println("sending...");

            Transport.send(message);

            System.out.println("After Sending ....");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

