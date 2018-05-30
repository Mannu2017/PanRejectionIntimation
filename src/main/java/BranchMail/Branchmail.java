package BranchMail;

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
import model.BranchDetail;
import model.ReportBranch;

public class Branchmail extends Thread{
	
	PanUtility utility=new PanUtility();
	
	public void run() {
		StringBuilder body = null; 
		List<BranchDetail> branchDetails=utility.getBranchDetail("branchmail","na");
		if(branchDetails.size()>0) {
			for(BranchDetail branchDetail : branchDetails) {
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
				DateFormat df1=new SimpleDateFormat("dd-MM-yyyy");
				DateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
				body = new StringBuilder();
				body.append("<div style=\"font-style: oblique; font-size: 15px;\">Ref	: KDMSL/TIN/PAN/"+branchDetail.getBranchcode()+"/"+df.format(new Date())+"</div>")
				.append("<div style=\"font-style: oblique; font-size: 15px;\">Date	: "+df1.format(new Date())+"</div>")
				.append("<br></br>")
				.append("<div style=\"font-style: oblique; font-size: 15px;\">Dear Concerned,</div>")
				.append("<div style=\"font-style: oblique; font-size: 17px;\">Following discrepancies have been observed while processing the below mentioned PAN applications. You are requested to rectify these discrepancies and re-scan the applications for further process.</div>")
				.append("<br></br>");
				List<ReportBranch> reportBranchs=utility.getRejdetail("branchmail",branchDetail.getBranchcode());
				if(reportBranchs.size()>0) {
					body.append("<p></p>")
					.append("<table border=\"2\" style=\"border-collapse:collapse;text-align:center\">")
					.append("  <tr>")
				     .append("    <th style=\"padding:5px\">SlNo</th>")
				     .append("    <th style=\"padding:5px\">InwardNo</th>")
				     .append("    <th style=\"padding:5px\">AcknowledgeNo</th>")
				     .append("    <th style=\"padding:5px\">FullName</th>")
				     .append("    <th style=\"padding:5px\">MobileNo</th>")
				     .append("    <th style=\"padding:5px\">RejectionReason</th>")
				     .append("  </tr>");
					for(ReportBranch reportBranch : reportBranchs) {
						body.append("  <tr>")
					     .append("    <td>"+reportBranch.getId()+"</td>")
					     .append("    <td>"+reportBranch.getInwardno()+"</td>")
					     .append("    <td>"+reportBranch.getAckno()+"</td>")
					     .append("    <td>"+reportBranch.getFullname()+"</td>")
					     .append("    <td>"+reportBranch.getMobileno()+"</td>")
					     .append("    <td>"+reportBranch.getRejReasons()+"</td>")
					     .append("  </tr>")
					     .append("");
						
					}
					
					MimeMessage msg = new MimeMessage(session);
					try {
						msg.setFrom(new InternetAddress("manobendra.biswas@karvy.com"));
						msg.setSubject("Pan Rejection Sample Branch Mail");
//						msg.addRecipient(Message.RecipientType.TO, new InternetAddress("narender@karvy.com"));
//						msg.addRecipient(Message.RecipientType.TO, new InternetAddress("anjana.satish@karvy.com"));
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


}
