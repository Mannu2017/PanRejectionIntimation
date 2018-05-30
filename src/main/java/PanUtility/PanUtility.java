package PanUtility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connection.DbConn;
import model.BranchDetail;
import model.ReportBranch;
import model.ResidentMailInfo;

public class PanUtility {
	private Connection con;
	
	public PanUtility() {
		this.con=DbConn.getConnection();
	}

	public List<ResidentMailInfo> getClintMail(String string) {
		List<ResidentMailInfo> mailInfos=new ArrayList<ResidentMailInfo>();
		ResidentMailInfo info=null;
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call PanRejections (?,?)}");
			cs.setString(1, string);
			cs.setString(2, "na");
			boolean sta=cs.execute();
			int rowef=0;
			while(sta || rowef!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowef=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				info=new ResidentMailInfo();
				info.setInwardNo(rs.getString(1));
				info.setAckno(rs.getString(2));
				info.setFullName(rs.getString(3));
				info.setRejReasons(rs.getString(4));
				info.setMobileNo(rs.getString(5));
				info.setEmailid(rs.getString(6));
				info.setBranchName(rs.getString(7));
				info.setAddress(rs.getString(8));
				mailInfos.add(info);
			}
			cs.close();
			rs.close();
			con.close();
			
		} catch (Exception e) {
			return mailInfos;
		}
		return mailInfos;
	}

	public List<BranchDetail> getBranchDetail(String string, String branchcode) {
		List<BranchDetail> branchDetails=new ArrayList<BranchDetail>();
		BranchDetail branchDetail=null;
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call PanRejections (?,?)}");
			cs.setString(1, string);
			cs.setString(2, branchcode);
			boolean sta=cs.execute();
			int rowef=0;
			while(sta || rowef!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowef=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				branchDetail=new BranchDetail();
				branchDetail.setBranchcode(rs.getString(1));
				branchDetail.setEmailid(rs.getString(2));
				branchDetails.add(branchDetail);
			}
			cs.close();
			rs.close();
			con.close();
			
		} catch (Exception e) {
			return branchDetails;
		}
		return branchDetails;
	}

	public List<ReportBranch> getRejdetail(String string, String branchcode) {
		List<ReportBranch> reportBranchs=new ArrayList<ReportBranch>();
		ReportBranch reportBranch=null;
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call PanRejections (?,?)}");
			cs.setString(1, string);
			cs.setString(2, branchcode);
			boolean sta=cs.execute();
			int rowef=0;
			while(sta || rowef!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowef=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			int sl=0;
			while (rs.next()) {
				sl=1+sl;
				reportBranch=new ReportBranch();
				reportBranch.setId(sl);
				reportBranch.setInwardno(rs.getString(1));
				reportBranch.setAckno(rs.getString(2));
				reportBranch.setFullname(rs.getString(3));
				reportBranch.setMobileno(rs.getString(4));
				reportBranch.setRejReasons(rs.getString(5));
				reportBranchs.add(reportBranch);
			}
			cs.close();
			rs.close();
			con.close();
			
		} catch (Exception e) {
			return reportBranchs;
		}
		return reportBranchs;
	}

	public void updateclimail(String inwardNo) {
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			PreparedStatement ps=con.prepareStatement("update PanRejectionIntimation set EmailStatus='Send',EmailDateTime=GETDATE() where InwardNo="+inwardNo);
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateclisms(String inwardNo) {
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			PreparedStatement ps=con.prepareStatement("update PanRejectionIntimation set SMSStatus='Send',SMSDateTime=GETDATE() where InwardNo="+inwardNo);
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateBranch(String inwardno) {
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			PreparedStatement ps=con.prepareStatement("update PanRejectionIntimation set BranchStatus='Send',BranchStatusDate=GETDATE() where InwardNo="+inwardno);
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public List<BranchDetail> getBranchDetail1(String string, String string2) {
		List<BranchDetail> branchDetails=new ArrayList<BranchDetail>();
		BranchDetail branchDetail=null;
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call PanRejections1 (?,?)}");
			cs.setString(1, string);
			cs.setString(2, string2);
			boolean sta=cs.execute();
			int rowef=0;
			while(sta || rowef!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowef=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			while (rs.next()) {
				branchDetail=new BranchDetail();
				branchDetail.setBranchcode(rs.getString(1));
				branchDetail.setEmailid(rs.getString(2));
				branchDetails.add(branchDetail);
			}
			cs.close();
			rs.close();
			con.close();
			
		} catch (Exception e) {
			return branchDetails;
		}
		return branchDetails;
	}
	
	public List<ReportBranch> getRejdetail1(String string, String branchcode) {
		List<ReportBranch> reportBranchs=new ArrayList<ReportBranch>();
		ReportBranch reportBranch=null;
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			ResultSet rs=null;
			CallableStatement cs=con.prepareCall("{call PanRejections1 (?,?)}");
			cs.setString(1, string);
			cs.setString(2, branchcode);
			boolean sta=cs.execute();
			int rowef=0;
			while(sta || rowef!=-1) {
				if(sta) {
					rs=cs.getResultSet();
					break;
				} else {
					rowef=cs.getUpdateCount();
				}
				sta=cs.getMoreResults();
			}
			int sl=0;
			while (rs.next()) {
				sl=1+sl;
				reportBranch=new ReportBranch();
				reportBranch.setId(sl);
				reportBranch.setInwardno(rs.getString(1));
				reportBranch.setAckno(rs.getString(2));
				reportBranch.setFullname(rs.getString(3));
				reportBranch.setMobileno(rs.getString(4));
				reportBranch.setRejReasons(rs.getString(5));
				reportBranchs.add(reportBranch);
			}
			cs.close();
			rs.close();
			con.close();
			
		} catch (Exception e) {
			return reportBranchs;
		}
		return reportBranchs;
	}

	public void updateBranch1(String inwardno) {
		try {
			if(con.isClosed()) {
				con=DbConn.getConnection();
			}
			PreparedStatement ps=con.prepareStatement("update PanRejectionIntimation set BranchStatus='Send',BranchStatusDate=GETDATE() where InwardNo="+inwardno);
			ps.execute();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
