package mannu;

import java.awt.EventQueue;
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

public class BranchMail {
	
	PanUtility utility=new PanUtility();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				new BranchMail();
			}
		});
	}

	public BranchMail() {
		StringBuilder body = null; 
		List<BranchDetail> branchDetails=utility.getBranchDetail1("branchmail","na");
		if(branchDetails.size()>0) {
			for(BranchDetail branchDetail : branchDetails) {
				Properties prop=new Properties();
				prop.put("mail.smtp.port", "25");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.ssl.trust", "srv-mail-ch5.karvy.com");
				Session session = Session.getInstance(prop,new javax.mail.Authenticator() {
					protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
						return new javax.mail.PasswordAuthentication("help.tin@karvy.com","Wkewpw?73");
					}
				});
				DateFormat df1=new SimpleDateFormat("dd-MM-yyyy");
				DateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
				body = new StringBuilder();
				body.append("<div style=\"font-style: oblique; font-size: 15px;\">Ref	: KDMSL/TIN/PAN/"+branchDetail.getBranchcode()+"/"+df.format(new Date())+"</div>")
				.append("<div style=\"font-style: oblique; font-size: 15px;\">Date	: "+df1.format(new Date())+"</div>")
				.append("<br></br>")
				.append("<div style=\"font-style: oblique; font-size: 17px;\">Dear Concerned,</div>")
				.append("<div style=\"font-style: oblique; font-size: 17px;\">Following discrepancies have been observed while processing the below mentioned PAN applications. You are requested to rectify these discrepancies and re-scan the applications for further process.</div>")
				.append("<br></br>");
				List<ReportBranch> reportBranchs=utility.getRejdetail1("branchmail",branchDetail.getBranchcode());
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
						utility.updateBranch1(reportBranch.getInwardno());
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
					body.append("</table>");
					body.append("<br></br>")
						.append("<div style=\"font-style: oblique; font-size: 17px;\">Regards,</div>")
						.append("<div style=\"font-style: oblique; font-size: 17px;\"><b>KDMS Tin Department</b></div>")
						.append("<div style=\"font-style: oblique; font-size: 17px;\">Karvy House,Avenue 4,</div>")
						.append("<div style=\"font-style: oblique; font-size: 17px;\">Street No. 1, Banjara Hills,</div>")
						.append("<div style=\"font-style: oblique; font-size: 17px;\">Hyderabad, Telangana 500034</div>")
						.append("<br></br>")
						.append("<div style=\"font-style: oblique; font-size: 14px;\"><b>Note: This is an electronic message. Please do not reply to this email.</b></div>");
					
					MimeMessage msg = new MimeMessage(session);
					try {
						msg.setFrom(new InternetAddress("help.tin@karvy.com"));
						msg.setSubject("Pan Rejection");
						String[] bmail=branchDetail.getEmailid().split("\\;");
						for(int i=0; i<bmail.length; i++ ) {
							 if(bmail[i].length()>0) {
								 msg.addRecipient(Message.RecipientType.TO, new InternetAddress(bmail[i].trim().toLowerCase().toString()));
								 System.out.println("SS: "+bmail[i].trim().toLowerCase().toString());
							 }
						 }
//						msg.addRecipient(Message.RecipientType.CC, new InternetAddress("anjana.satish@karvy.com"));
						msg.addRecipient(Message.RecipientType.CC, new InternetAddress("help.tin@karvy.com"));
//						msg.addRecipient(Message.RecipientType.CC, new InternetAddress("manobendra.biswas@karvy.com"));
						 Multipart multipart = new MimeMultipart();
					        BodyPart htmlBodyPart = new MimeBodyPart();
					        htmlBodyPart.setContent(body.toString() , "text/html"); 
					        multipart.addBodyPart(htmlBodyPart);
					        msg.setContent(multipart);
						Transport transport = session.getTransport("smtp");
				        transport.connect("srv-mail-ch5.karvy.com", 25,"help.tin@karvy.com","Wkewpw?73");
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
