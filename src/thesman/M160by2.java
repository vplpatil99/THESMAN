
package thesman;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.jaunt.component.Form;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class M160by2 {

    public static String Token;
    Form sms;
    UserAgent agent;

    /**
     * Used to login at http://www.160by2.com bu using username and password
     * @param username
     * @param Password
     * @throws ResponseException
     * @throws NotFound
     */
    public void login(String username,String Password) throws ResponseException, NotFound {

        agent=new UserAgent();
        agent.visit("http://www.160by2.com/Index");
        Form form=agent.doc.getForm(0);
        form.setTextField("username",username);
        form.setPassword("password",Password);
        form.submit();

        Token=agent.getLocation().substring(agent.getLocation().indexOf("?id=")+4);
        agent.visit("http://www.160by2.com/SendSMS?id="+Token);
        sms=agent.doc.getForm(0);
    }

    /**
     * Used to send msg to specified phone number.
     * @param message
     * @param Phone_No
     * @throws NotFound
     * @throws ResponseException
     */
    public void sendSMS(String message,String Phone_No) throws NotFound, ResponseException {

        sms.setTextField(sms.getElement().findFirst("<input type=\"text\" placeholder=\"Enter Mobile Number or Name\"").getAt("name"),Phone_No);
        sms.setTextArea("sendSMSMsg",message);
        sms.setHidden("maxwellapps",Token);
        sms.setHidden("hid_exists","no");
        sms.setAction("http://www.160by2.com/"+sms.getElement().findFirst("<input type=\"hidden\" id=\"fkapps\"").getAt("value"));
        sms.submit();
        System.out.println(agent.doc.innerHTML());
    }
    


      public void sendEmail(String from,String password,String to,String sub,String msg){  
          //Get properties object    
          Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");    
          //get Session   
          Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });    
          //compose message    
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject(sub);    
           message.setText(msg);    
           //send message  
           Transport.send(message);    
           System.out.println("message sent successfully");    

			System.out.println("Done");
		      } catch (Exception e) {
		         throw new RuntimeException(e);
		      }
		   }


}
