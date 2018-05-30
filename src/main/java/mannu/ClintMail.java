package mannu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import BranchMail.Branchmail;
import PanUtility.PanUtility;
import ResidentMail.ResidentMail;
import ResidentSMS.ResidentSMS;
import model.BranchDetail;
import model.ReportBranch;
import model.ResidentMailInfo;

public class ClintMail {
	PanUtility utility=new PanUtility();
	
	@SuppressWarnings("unused")
	private String[] args;
	
	public ClintMail() {
		List<String> rqtype=new ArrayList<String>();
		rqtype.add("email");
		rqtype.add("sms");
		rqtype.add("branch");
		
		for(String rqtyp : rqtype) {
			if(rqtyp.equals("email")) {
				List<ResidentMailInfo> mailInfos=utility.getClintMail("email");
				if(mailInfos.size()>0) {
					for(ResidentMailInfo mailinfo : mailInfos) {
						utility.updateclimail(mailinfo.getInwardNo());
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
						DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
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
						body.append("<div style=\"font-style: oblique; font-size: 14px;\"><b>Note: This is an electronic message. Please do not reply to this email.</b></div>");
						MimeMessage msg = new MimeMessage(session);
						try {
							msg.setFrom(new InternetAddress("help.tin@karvy.com"));
							msg.setSubject("Pan Additional Documents / Information Required");
							msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailinfo.getEmailid()));
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress("anjana.satish@karvy.com"));
							msg.addRecipient(Message.RecipientType.CC, new InternetAddress("help.tin@karvy.com"));
//							msg.addRecipient(Message.RecipientType.CC, new InternetAddress("manobendra.biswas@karvy.com"));
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
				
			} else if (rqtyp.equals("sms")) {
				
//				List<ResidentMailInfo> mailInfos=utility.getClintMail("sms");
//				if(mailInfos.size()>0) {
//					for(ResidentMailInfo smsinfo : mailInfos) {
//						utility.updateclisms(smsinfo.getInwardNo());
//						String msg="Dear Customer,\n Pan application made vide Acknowledge No. "+smsinfo.getAckno()+" rejected for Reason - "+smsinfo.getRejReasons()+". Pease contact your branch within 10 days to avoid cancellation of application.";
//						String data = "username=aadhar-token&password=Tok@3690&mobile="+smsinfo.getMobileNo()+"&sendername=KARVYD&message="+msg+"&routetype=1"; 
//						String MsgUrl="http://192.168.82.241/SMS_API/sendsms.php?";
//					      URL url;
//						try {
//							url = new URL(MsgUrl);
//							 URLConnection conn = url.openConnection();
//						      conn.setDoOutput(true);
//						      OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//						      wr.write(data);
//						      wr.flush();
//						      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//						      String line;
//						      while ((line = rd.readLine()) != null) {
//						          System.out.println(line);
//						      }
//						      wr.close();
//						      rd.close();
//						} catch (MalformedURLException e) {
//							e.printStackTrace();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					     
//						
//					}
//				}
				
			} else if(rqtyp.equals("branch")) {
				
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
								utility.updateBranch(reportBranch.getInwardno());
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
								msg.addRecipient(Message.RecipientType.CC, new InternetAddress("anjana.satish@karvy.com"));
								msg.addRecipient(Message.RecipientType.CC, new InternetAddress("help.tin@karvy.com"));
//								msg.addRecipient(Message.RecipientType.CC, new InternetAddress("manobendra.biswas@karvy.com"));
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
	}
	
	public void setArgs(String[] args) {
		this.args=args;
	}

}
