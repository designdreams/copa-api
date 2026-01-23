package com.designdreams.copass.service;

import com.designdreams.copass.utils.ReadTextAsString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InlineImageEmailTester {

    String emailToken;

    String emailFrom;
    
    /**
     * main entry of the program
     */
    public static void sendAlert(String userId, String name, String src, String  des, String emailId) {

        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "copayana.alerts@gmail.com";
        String password = "alerts@123";
 
        // message info
        String mailTo = emailId;
        String subject = "Your trip got a Travel companion - Copyana.com";
        StringBuffer body
            = new StringBuffer("<html>Hope you are doing Great! We found a travel match!<br>");
        body.append("<br>");
        body.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
        body.append("The second one is a cube:<br>");
        body.append("<img src=\"cid:image2\" width=\"15%\" height=\"15%\" /><br>");
        body.append("End of message.");
        body.append("</html>");

        // inline images
        Map<String, String> inlineImages = new HashMap<String, String>();

        //inlineImages.put("image2", "E:/Test/cube.jpg");
 
        try {
            String bodys = ReadTextAsString.readFileAsString("/static/alert_emaI.html");

//            String file = new File("copayana_logo_email_sml.png").getCanonicalPath();
////            inlineImages.put("image1", file);

            EmbeddedImageEmailUtil.send(host, port, mailFrom, password, mailTo,
                subject, bodys, inlineImages);
            System.out.println("Email sent.");

        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}
