package ResidentMail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import PanUtility.PanUtility;
import model.ResidentMailInfo;

public class ResidentMail extends Thread{
	PanUtility utility=new PanUtility();
	
	public void run() {
		List<ResidentMailInfo> mailInfos=utility.getClintMail("email");
		if(mailInfos.size()>0) {
			for(ResidentMailInfo mailinfo : mailInfos) {
				Properties prop=new Properties();
				prop.put("mail.smtp.port", "25");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.ssl.trust", "srv-mail-ch5.karvy.com");
				Session session = Session.getInstance(prop,new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication("manobendra.biswas@karvy.com","Rina@2018@");
					}
				});
				DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
				String sysdate=df.format(new Date());
				DateFormat yr=new SimpleDateFormat("yyyy");
				
				StringBuilder body = new StringBuilder();
				body.append("<div>")
					.append("<div style=\"font-style: oblique; font-size: 15px;\">Ref  : KDMSL/TIN/PAN/"+mailinfo.getAckno()+"/"+mailinfo.getInwardNo()+"/"+yr.format(new Date()).toString()+"</div>")
					.append("<div style=\"font-style: oblique; font-size: 15px;\">Acknowledgement No :  "+mailinfo.getAckno()+"</div>")
					.append("<br></br>");
				body.append("<div style=\"font-style: oblique; font-size: 17px;\">Dear Sir/Madam,</div>")
					.append("<p></p>")
					.append("<div style=\"font-style: oblique; font-size: 17px;\">This is with reference to the PAN application filed by you vide the captioned acknowledgment number.</div> " + 
							"<div style=\"font-style: oblique; font-size: 17px;\">We wish to inform you that, the following discrepancies were observed while processing your application: " + 
							"<p><b>"+mailinfo.getRejReasons()+"</b></p></div>")
					.append("<p></p>")
					.append("<div style=\"font-style: oblique; font-size: 17px;\">We request you to arrange to furnish the supporting valid documents / information along with the application form within 10 days from the receipt of this mail to the below Karvy Branch."
							+ "<p></p>"
							+ "In case the same is not received within 10 days, we shall treat your application as cancelled, and you will have to submit a fresh application to process the same. Please quote your acknowledgement number while submitting the above details."
							+ "<p></p>"
							+ "<p>Yours truly,</p>" 
							+ "<p><b>KDMSL- TIN Department</b></p>"
							+ "<p></p>"
							+ "<b>BRANCH ADDRESS:</b></div>");
				body.append("<div style=\"font-style: oblique; font-size: 17px;\"><b>Branch Name: "+mailinfo.getBranchName()+"</b></div>");
				String[] resadd=mailinfo.getAddress().split("\\^");
				String ad = "";
				for(int i=0; i<resadd.length; i++ ) {
					 if(resadd[i].length()>0) {
						 ad+="<div style=\"font-style: oblique; font-size: 17px;\">"+resadd[i]+"</div>";
					 }
				 }
				body.append(ad);
				body.append("<br></br>");
				body.append("<div style=\"font-style: oblique; font-size: 17px;\"><b>Note: This is an electronic message. Please do not reply to this email.</b></div>");
				MimeMessage msg = new MimeMessage(session);
				try {
					msg.setFrom(new InternetAddress("manobendra.biswas@karvy.com"));
					msg.setSubject("Pan Rejection Sample Mail");
//					msg.addRecipient(Message.RecipientType.TO, new InternetAddress("narender@karvy.com"));
//					msg.addRecipient(Message.RecipientType.TO, new InternetAddress("anjana.satish@karvy.com"));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress("manobendra.biswas@karvy.com"));
					 Multipart multipart = new MimeMultipart();
				        BodyPart htmlBodyPart = new MimeBodyPart();
				        htmlBodyPart.setContent(body.toString() , "text/html"); 
				        multipart.addBodyPart(htmlBodyPart);
				        msg.setContent(multipart);
					Transport transport = session.getTransport("smtp");
			        transport.connect("srv-mail-ch5.karvy.com", 25,"manobendra.biswas@karvy.com","Rina@2018@");
			        transport.sendMessage(msg, msg.getAllRecipients());
			        transport.close();
					 
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
	}

}
