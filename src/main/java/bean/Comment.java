package bean;

public class Comment {

    private int commentId;
    private int itemId;
    private int userId;
    private String comment;
    private String createDateTime;
    private int sellerFlag; // 0:一般ユーザー, 1:出品者

    // 画面表示用（JOINして取得する投稿者のニックネーム）
    private String nickname;

    public Comment() {
        this.commentId = 0;
        this.itemId = 0;
        this.userId = 0;
        this.comment = null;
        this.createDateTime = null;
        this.sellerFlag = 0;
        this.nickname = null;
    }

    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getCreateDateTime() { return createDateTime; }
    public void setCreateDateTime(String createDateTime) { this.createDateTime = createDateTime; }

    public int getSellerFlag() { return sellerFlag; }
    public void setSellerFlag(int sellerFlag) { this.sellerFlag = sellerFlag; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}