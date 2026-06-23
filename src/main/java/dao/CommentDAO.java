package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Comment;

public class CommentDAO  {
	// 接続用の情報をフィールドに定数として定義
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/flea_market_db";
	private static String USER = "root";
	private static String PASSWD = "root123";

	/**
	 * データベースへのコネクションを取得します。
	 * @return 確立されたデータベース接続（Connectionオブジェクト）
	 * @throws IllegalStateException JDBCドライバーのロードや接続に失敗した場合にスローされます
	 */
	private static Connection getConnection() {
		try {
			// JDBCドライバーをメモリにロードする
			Class.forName(RDB_DRIVE);
			// URL、ユーザー名、パスワードを使用してデータベースへの接続を確立する
			Connection con = DriverManager.getConnection(URL, USER, PASSWD);
			return con;
		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
			throw new IllegalStateException("DB接続エラー", e);
		}
	}

	/**
	特定の商品(item_id)に紐づくコメント一覧を取得します。
	古いコメントから順に表示するため、日時の昇順(ASC)で取得します。*/
	public List<Comment> selectByItemId(int itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> commentList = new ArrayList<>();

		try {
			con = getConnection();
			// コメントとユーザー情報を結合し、ニックネームを取得
			String sql = "SELECT c.comment_id, c.item_id, c.user_id, c.comment, "
					+ "c.create_date_time, c.seller_flag, u.nickname "
					+ "FROM comment c "
					+ "JOIN user_info u ON c.user_id = u.user_id "
					+ "WHERE c.item_id = ? "
					+ "ORDER BY c.create_date_time ASC";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setItemId(rs.getInt("item_id"));
				comment.setUserId(rs.getInt("user_id"));
				comment.setComment(rs.getString("comment"));
				comment.setCreateDateTime(rs.getString("create_date_time"));
				comment.setSellerFlag(rs.getInt("seller_flag"));
				comment.setNickname(rs.getString("nickname"));

				commentList.add(comment);
			}
		} catch (SQLException e) {
			throw new RuntimeException("コメント一覧の取得に失敗しました", e);
		} finally {
			closeResources(con, pstmt, rs);
		}
		return commentList;
	}

	/**
	 
	新しいコメントをデータベースに登録します。*/
	public void insert(Comment comment) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = getConnection();
			// 件名(subject)は除外し、必要なデータだけを登録。日時はNOW()で現在時刻を自動挿入。
			String sql = "INSERT INTO comment (item_id, user_id, comment, create_date_time, seller_flag) "
					+ "VALUES (?, ?, ?, NOW(), ?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, comment.getItemId());
			pstmt.setInt(2, comment.getUserId());
			pstmt.setString(3, comment.getComment());
			pstmt.setInt(4, comment.getSellerFlag());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("コメントの投稿に失敗しました", e);
		} finally {
			closeResources(con, pstmt, null);
		}
	}
	/**
	 * DBリソースをクローズします。
	 *
	 * @param con DB接続
	 * @param pstmt ステートメント
	 */
	private void closeResources(Connection con, PreparedStatement pstmt ,ResultSet rs) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}