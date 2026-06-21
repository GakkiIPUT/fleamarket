package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bean.Comment;

public class CommentDAO {
    private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost/flea_market_db";
    private static String USER = "root";
    private static String PASSWD = "password";

    private static Connection getConnection() throws Exception {
        Class.forName(RDB_DRIVE);
        return DriverManager.getConnection(URL, USER, PASSWD);
    }

    /**
     * コメントをデータベースに登録します。
     */
    public void insert(Comment comment) {
        // ★ subject と is_seller を追加
        String sql = "INSERT INTO comments (item_id, user_id, subject, content, is_seller, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             
            pstmt.setInt(1, comment.getItemId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getSubject());
            pstmt.setString(4, comment.getContent());
            pstmt.setInt(5, comment.getIsSeller());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 指定された商品のコメント一覧を、投稿者のニックネーム付きで取得します。
     */
    public ArrayList<Comment> selectByItemId(int itemId) {
        ArrayList<Comment> list = new ArrayList<>();
        // ★ subject と is_seller を取得カラムに追加
        String sql = "SELECT c.comment_id, c.item_id, c.user_id, c.subject, c.content, c.is_seller, c.created_at, u.nickname "
                   + "FROM comments c JOIN users u ON c.user_id = u.user_id "
                   + "WHERE c.item_id = ? ORDER BY c.created_at ASC";
                   
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
             
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();
                c.setCommentId(rs.getInt("comment_id"));
                c.setItemId(rs.getInt("item_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setSubject(rs.getString("subject"));
                c.setContent(rs.getString("content"));
                c.setIsSeller(rs.getInt("is_seller"));
                c.setCreatedAt(rs.getTimestamp("created_at"));
                c.setNickname(rs.getString("nickname"));
                list.add(c);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return list;
    }
}