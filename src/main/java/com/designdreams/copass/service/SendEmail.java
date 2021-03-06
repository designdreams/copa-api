package com.designdreams.copass.service;

import com.designdreams.copass.utils.AES;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class SendEmail {

    @Value("${USER_TOKEN}")
    private String from;

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

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

    @Value("${AES_TOKEN}")
    private String token;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostKey() {
        return hostKey;
    }

    public void setHostKey(String hostKey) {
        this.hostKey = hostKey;
    }

    public String getPortKey() {
        return portKey;
    }

    public void setPortKey(String portKey) {
        this.portKey = portKey;
    }

    public String getPortValue() {
        return portValue;
    }

    public void setPortValue(String portValue) {
        this.portValue = portValue;
    }

    public String getEnableKey() {
        return enableKey;
    }

    public void setEnableKey(String enableKey) {
        this.enableKey = enableKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getDefaultSubjectMessage() {
        return defaultSubjectMessage;
    }

    public void setDefaultSubjectMessage(String defaultSubjectMessage) {
        this.defaultSubjectMessage = defaultSubjectMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static final Logger logger = LogManager.getLogger(SendEmail.class);

    public void email (String to,String src,  String dest){

        try{

            String decrypted_email_access_token = AES.decrypt(getAccessToken(),token);

            Properties properties = new Properties();
            properties.put(hostKey, host);
            properties.put(portKey, portValue);
            properties.put(enableKey, true);
            properties.put(authKey, true);
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from,decrypted_email_access_token);
                }
            });
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            Multipart multipart = new MimeMultipart();

            message.setSubject(defaultSubjectMessage + "" + dest + " !");

            message.setText("A message to your inbox");

            logger.info("sending...");

            Transport.send(message);

            logger.info("After Sending ....");

        }catch(Exception e){
            logger.error("SEND EMAIL ERROR ",e);
        }
    }
}

