package model;

public class ReportBranch {
	
	private String inwardno;
	private String ackno;
	private String fullname;
	private String mobileno;
	private String RejReasons;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInwardno() {
		return inwardno;
	}
	public void setInwardno(String inwardno) {
		this.inwardno = inwardno;
	}
	public String getAckno() {
		return ackno;
	}
	public void setAckno(String ackno) {
		this.ackno = ackno;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getRejReasons() {
		return RejReasons;
	}
	public void setRejReasons(String rejReasons) {
		RejReasons = rejReasons;
	}
	
}
