package bean;

import java.sql.Timestamp;

public class Inquiry {
	private int inquiryId;
	private int userId;
	private String subject;
	private String comments;
	private String administratorReply;
	private int compatibilityStatus;
	private Timestamp createDateTime;
	private Timestamp updatedDateTime;
	private boolean readingFlag;
	private boolean recipientFlag;
	
	/**
	 * 引数なしコンストラクタです
	 */
	public Inquiry() {
		this.inquiryId = 0;
		this.userId = 0;
		this.subject = null;
		this.comments = null;
		this.administratorReply = null;
		this.compatibilityStatus = 0;
		this.createDateTime = null;
		this.updatedDateTime = null;
		this.readingFlag = false;
		this.recipientFlag = false;
	}
	
	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getAdministratorReply() {
		return administratorReply;
	}
	
	public void setAdministratorReply(String administratorReply) {
		this.administratorReply = administratorReply;
	}
	
	public int getCompatibilityStatus() {
		return compatibilityStatus;
	}
	
	public void setCompatibilityStatus(int compatibilityStatus) {
		this.compatibilityStatus = compatibilityStatus;
	}
	
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}
	
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}
	
	public Timestamp getUpdatedDateTime() {
		return updatedDateTime;
	}
	
	public void setUpdatedDateTime(Timestamp updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	
	public boolean isReadingFlag() {
		return readingFlag;
	}
	
	public void setReadingFlag(boolean readingFlag) {
		this.readingFlag = readingFlag;
	}
	
	public boolean isRecipientFlag() {
		return recipientFlag;
	}
	
	public void setRecipientFlag(boolean recipientFlag) {
		this.recipientFlag = recipientFlag;
	}

}	