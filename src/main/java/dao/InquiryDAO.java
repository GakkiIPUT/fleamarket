/*
 * 書籍管理Ver4.0からコピー。SQL文要変更です。
 * →変更しました。6/19 林
 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Inquiry;

public class InquiryDAO {

	// DB接続情報
	private final String URL = "jdbc:mysql://localhost/mybookdb";
	private final String USER = "root";
	private final String PASS = "root123";

	private Connection con = null;

	public void connect() {
		try {
			// JDBCドライバのロード (MySQL 8.0以降)
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			throw new IllegalStateException("DB接続エラー",e);
		}
	}

	public void disconnect() {
		try {
			if (con != null) {
				con.close();
			}
		} catch (Exception e) {
			throw new IllegalStateException("DB切断エラー", e);
		}
	}

	/**
	 *  お問い合わせの新規登録（ユーザー用）
	 * @param inquiry
	 */
	public void insert(Inquiry inquiry) {
		String sql = "INSERT INTO inquiry (user_id, subject, comments, compatibility_status, create_date_time) "
						+ "VALUES (?, ?, ?, 0, NOW())";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, inquiry.getUserId());
			stmt.setString(2, inquiry.getSubject());
			stmt.setString(3, inquiry.getComments());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB更新エラー", e);
		} finally {
			disconnect();
		}
	}

	/**
	 *  お問い合わせ返信（管理者）
	 * @param inquiry
	 */
	public void reply(Inquiry inquiry) {
		String sql = "UPDATE inquiry SET admin_reply = ?, compatibility_status = 1 WHERE inquiry_id = ?";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, inquiry.getAdministratorReply());
			stmt.setInt(2, inquiry.getInquiryId());

			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB更新エラー", e);
		} finally {
			disconnect();
		}
	}
	
	/**
	 *  お問い合わせの全件取得（管理者用）
	 * @return
	 */
	public ArrayList<Inquiry> selectAll() {
		ArrayList<Inquiry> list = new ArrayList<Inquiry>();
		String sql = "SELECT * FROM inquiry ORDER BY creqte_date_time DESC"; // 新しい順
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Inquiry inquiry = new Inquiry();
				inquiry.setInquiryId(rs.getInt("inquiry_id"));
				inquiry.setUserId(rs.getInt("user_id"));
				inquiry.setSubject(rs.getString("subject"));
				inquiry.setComments(rs.getString("comments"));
				inquiry.setAdministratorReply(rs.getString("administrator_reply"));
				inquiry.setCompatibilityStatus(rs.getInt("compatibility_status"));
				inquiry.setCreateDateTime(rs.getTimestamp("create_date_time"));
				inquiry.setUpdatedDateTime(rs.getTimestamp("updated_date_time"));
				inquiry.setReadingFlag(rs.getBoolean("reading_flag"));
				inquiry.setRecipientFlag(rs.getBoolean("recipient_flag"));
				list.add(inquiry);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB検索エラー", e);
		} finally {
			disconnect();
		}
		return list;
	}

	/**
	 *  お問い合わせ詳細の取得（管理者用）
	 * @param inquiryId
	 * @return
	 */
	public Inquiry selectById(int inquiryId) {
		Inquiry inquiry = new Inquiry();
		String sql = "SELECT * FROM inquiry WHERE inquiry_id = ?";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, inquiryId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				inquiry.setInquiryId(rs.getInt("inquiry_id"));
				inquiry.setUserId(rs.getInt("user_id"));
				inquiry.setSubject(rs.getString("subject"));
				inquiry.setComments(rs.getString("comments"));
				inquiry.setAdministratorReply(rs.getString("administrator_reply"));
				inquiry.setCompatibilityStatus(rs.getInt("compatibility_status"));
				inquiry.setCreateDateTime(rs.getTimestamp("create_date_time"));
				inquiry.setUpdatedDateTime(rs.getTimestamp("updated_date_time"));
				inquiry.setReadingFlag(rs.getBoolean("reading_flag"));
				inquiry.setRecipientFlag(rs.getBoolean("recipient_flag"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB検索エラー", e);
		} finally {
			disconnect();
		}
		return inquiry;
	}

	/**
	 *  お問い合わせの削除（管理者用）
	 * @param id
	 */
	public void delete(int inquiryId) {
		String sql = "DELETE FROM inquiry WHERE inquiry_id = ?";
		try {
			connect();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, inquiryId);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new IllegalStateException("DB更新エラー", e);
		} finally {
			disconnect();
		}
	}
}