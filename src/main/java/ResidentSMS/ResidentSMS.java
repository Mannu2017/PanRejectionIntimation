package ResidentSMS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import PanUtility.PanUtility;
import model.ResidentMailInfo;

public class ResidentSMS extends Thread{
	PanUtility utility=new PanUtility();
	
	public void run() {
		List<ResidentMailInfo> mailInfos=utility.getClintMail("sms");
		if(mailInfos.size()>0) {
			for(ResidentMailInfo smsinfo : mailInfos) {
				String msg="Dear Customer,\n Pan application made vide Acknowledge No. "+smsinfo.getAckno()+" rejected for Reason - "+smsinfo.getRejReasons()+". Pease contact your branch within 10 days to avoid cancellation of application.";
				String data = "username=aadhar-token&password=Tok@3690&mobile=9933605640&sendername=KARVYD&message="+msg+"&routetype=1"; 
				String MsgUrl="http://192.168.82.241/SMS_API/sendsms.php?";
			      URL url;
				try {
					url = new URL(MsgUrl);
					 URLConnection conn = url.openConnection();
				      conn.setDoOutput(true);
				      OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				      wr.write(data);
				      wr.flush();
				      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				      String line;
				      while ((line = rd.readLine()) != null) {
				          System.out.println(line);
				      }
				      wr.close();
				      rd.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			     
				
			}
		}
		
	}


}
