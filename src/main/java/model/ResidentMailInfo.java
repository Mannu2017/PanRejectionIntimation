package model;

public class ResidentMailInfo {
	
	private String InwardNo;
	private String Ackno;
	private String RejReasons;
	private String MobileNo;
	private String Emailid;
	private String BranchName;
	private String Address;
	private String FullName;
	
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getInwardNo() {
		return InwardNo;
	}
	public void setInwardNo(String inwardNo) {
		InwardNo = inwardNo;
	}
	public String getAckno() {
		return Ackno;
	}
	public void setAckno(String ackno) {
		Ackno = ackno;
	}
	public String getRejReasons() {
		return RejReasons;
	}
	public void setRejReasons(String rejReasons) {
		RejReasons = rejReasons;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getEmailid() {
		return Emailid;
	}
	public void setEmailid(String emailid) {
		Emailid = emailid;
	}
	public String getBranchName() {
		return BranchName;
	}
	public void setBranchName(String branchName) {
		BranchName = branchName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
}
