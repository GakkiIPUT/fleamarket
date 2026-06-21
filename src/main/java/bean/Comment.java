package bean;

import java.sql.Timestamp;

public class Comment {
	private int commentId;
	private int itemId;
	private int userId;
	private String nickname; // 表示用ニックネーム
	private String subject; // ★追加：件名
	private String comment;
	private int isSeller; // ★追加：出品者フラグ (1: 出品者, 0: その他)
	private Timestamp createdAt;

	// Getter & Setter
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	// 件名のGetter/Setter
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return comment;
	}

	public void setContent(String content) {
		this.comment = content;
	}

	// 出品者フラグのGetter/Setter
	public int getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(int isSeller) {
		this.isSeller = isSeller;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}