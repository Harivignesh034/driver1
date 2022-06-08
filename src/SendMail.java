

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail{

    public static void sendFile(String filename,String filename1,String[] Email,int a,int b,int t) {
        //authentication info
        final String username = "harivigneshhari1@gmail.com";
        final String password = "kyvsxporumwpdzan";
        String fromEmail = "harivigneshahari1@gmail.com";


        //String toEmail = "gopalakrishnan.a@zohocorp.com";
       // String toEmail1="nisanth.c@zohocorp.com";
        //String toEmail2="harivignesh.18ei@kct.ac.in";
        //gopalakrishnan.a@zohocorp.com

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));

           // msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail1));
            for(String i : Email) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(i));
            }
            msg.setSubject("VENDOR_DRIVER verification");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();

            textBodyPart.setText("Dear Team :\n            Vendor drivers Metadata Json verification done  and it will check Downloads size , extract size , MD5checksum ,SHA256checksum and not matched drivers are list out separate file\n            "+t+" totals drivers"+"\n            "+a +" drivers are all matched \n            "+b +" mis matched drivers\n");


            //Attachment body part.
            MimeBodyPart pdfAttachment = new MimeBodyPart();
            pdfAttachment.attachFile("C:\\filehandle\\"+filename+".csv");
            pdfAttachment.setFileName("DRIVER_DETAILS.csv");
            MimeBodyPart pdfAttachment1 = new MimeBodyPart();
            pdfAttachment1.attachFile("C:\\filehandle\\"+filename1+".csv");
            pdfAttachment1.setFileName("NOT_MATCHED_DRIVERS.csv");

            MimeBodyPart logAttachment = new MimeBodyPart();
            logAttachment.attachFile("C:\\Newlog\\DRIVERS_LOG.log");

            //Attach body parts
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfAttachment);

            /*MimeBodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(String.valueOf(pdfAttachment));
            messageBodyPart.setDataHandler(new DataHandler(source));
            String fileAttachment = null;
            messageBodyPart.setFileName(fileAttachment);
            Multipart multipart = null;
            multipart.addBodyPart(messageBodyPart);*/


            
            emailContent.addBodyPart(pdfAttachment1);
            emailContent.addBodyPart(logAttachment);

            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}