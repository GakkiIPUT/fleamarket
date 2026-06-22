/*
 * プロジェクト名：フリマシステム
 * プログラム名：UserDAO.java
 * プログラムの説明：DB接続とuser_infoテーブルに対するユーザー情報の取得や
 * 					パスワードに合致する情報を取得するSQL実行を担当する。
 * 作成日：2026年6月19日
 * 作成者：中田佳葉
*/

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

/**
 * userinfoテーブルに対するユーザー情報の取得・更新・削除を行うDAOクラスです。
 * DB接続の確立と各SQLの実行を担当します。
 */
public class UserDAO {

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
	 * userId に基づいてユーザーを選択します。
	 *
	 * @param userId 検索対象のユーザーID
	 * @return ユーザーが見つかった場合はUser DTO、見つからない場合はnull
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public User selectByUser(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		User user = null;

		String sql = "SELECT * FROM login WHERE mail = ? AND password = ? AND freeze_date_time IS NULL";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return user;
	}

	/**
	 * ユーザーIDとパスワードに基づいてユーザーを選択します（認証に使用されます）。
	 *
	 * @param userId 検索するユーザーID
	 * @param password 照合するパスワード
	 * @return 認証情報が一致した場合はUser DTO、一致しない場合はnull
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public User selectByUser(String userId, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		User user = null;

		String sql = "SELECT * FROM login WHERE mail = ? AND password = ? AND freeze_date_time IS NULL";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user"));
				user.setPassword(rs.getString("password"));

			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return user;
	}

	/**
	 * 指定したユーザー情報をloginテーブルへ登録します。
	 *
	 * @param user 登録するユーザー情報
	 * @return 登録件数
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */

	public int insert(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "INSERT INTO login (mail, password, authority_flag) VALUES (?, ?, 0)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user.getUserId());
			pstmt.setString(5, user.getPassword());
			pstmt.setBoolean(10, user.getAuthorityFlag());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	/**
	 * 指定したユーザー情報をuserinfoテーブルへ登録します。
	 *
	 * @param user 登録するユーザー情報
	 * @return 登録件数
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public int insertAll(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "INSERT INTO user_info (login_id, nickname, last_name, first_name, last_name_rubi, first_name_rubi, post_code, prefectures, city, street_address, building_room, telephone_number, create_date_time, withdrawal_flag) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,NOW(), 0)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getLastName());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getNickname());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getPassword());
			pstmt.setString(6, user.getPostCode());
			pstmt.setString(7, user.getPrefectures());
			pstmt.setString(8, user.getCity());
			pstmt.setString(9, user.getStreetAddress());
			pstmt.setString(10, user.getBuildingRoom());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	/**
	 * userinfoテーブルに存在する全ユーザーを取得します。
	 *
	 * @return 全ユーザー情報のリスト
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public java.util.ArrayList<User> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		java.util.ArrayList<User> userList = new java.util.ArrayList<>();
		String sql = "SELECT u., l.mail, l.authority_flag FROM user_info u JOIN login l ON u.login_id = l.login_id WHERE u.withdrawal_flag = 0;";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("lastName"));
				user.setFirstName(rs.getString("firstName"));
				user.setPostCode(rs.getString("postCode"));
				user.setPrefectures(rs.getString("prefectures"));
				user.setCity(rs.getString("city"));
				user.setStreetAddress(rs.getString("streetAddress"));
				user.setBuildingRoom(rs.getString("buildingRoom"));
				user.setTelephoneNumber(rs.getString("telephoneNumber"));
				user.setCreateDateTime(rs.getTimestamp("createDateTime"));
				user.setUpdateDateTime(rs.getTimestamp("updateDateTime"));
				user.setWithdrawalFlag(rs.getBoolean("withdrawalFlag"));

				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return userList;
	}

	/**
	 * userinfoテーブルに存在する（ID、ニックネーム、本名）を取得します。
	 *
	 * @return ID、ニックネーム、本名
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public java.util.ArrayList<User> selectPart() {
		Connection con = null;
		PreparedStatement pstmt = null;
		java.util.ArrayList<User> userList = new java.util.ArrayList<>();
		String sql = "SELECT u., l.mail, l.authority_flag FROM user_info u JOIN login l ON u.login_id = l.login_id WHERE u.withdrawal_flag = 0;";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("lastName"));
				user.setFirstName(rs.getString("firstName"));

				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return userList;
	}

	/**
	 * ユーザー情報を更新します。
	 *
	 * @param user 更新対象のユーザー情報
	 * @return 更新件数
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public int update(User user) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "UPDATE user_info SET nickname = ?, post_code = ?, prefectures = ?, city = ?, street_address = ?, building_room = ?, telephone_number = ?, update_date_time = NOW() WHERE user_id = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getLastName());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getNickname());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getPassword());
			pstmt.setString(6, user.getPostCode());
			pstmt.setString(7, user.getPrefectures());
			pstmt.setString(8, user.getCity());
			pstmt.setString(9, user.getStreetAddress());
			pstmt.setString(10, user.getBuildingRoom());
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	/**
	 * userId を指定してユーザー情報を削除します。
	 *
	 * @param userId 削除対象のユーザーID
	 * @return 削除件数
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public int delete(int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		String sql = "UPDATE user_info SET withdrawal_flag = 1, update_date_time = NOW() WHERE user_id = ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return count;
	}

	/**
	 * userId の部分一致でユーザーを検索します。
	 *
	 * @param userId 検索キーワードとなるユーザーID
	 * @return 検索条件に一致したユーザー情報のリスト
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public java.util.ArrayList<User> search(String userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		java.util.ArrayList<User> userList = new java.util.ArrayList<>();
		String sql = "SELECT u.*, l.mail FROM user_info u JOIN login l ON u.login_id = l.login_id WHERE u.withdrawal_flag = 0   AND (u.last_name LIKE ? OR u.first_name LIKE ? OR u.nickname LIKE ?";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + userId + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user"));
				user.setPassword(rs.getString("password"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("lastName"));
				user.setFirstName(rs.getString("firstName"));
				user.setPostCode(rs.getString("postCode"));
				user.setPrefectures(rs.getString("prefectures"));
				user.setCity(rs.getString("city"));
				user.setStreetAddress(rs.getString("streetAddress"));
				user.setBuildingRoom(rs.getString("buildingRoom"));
				user.setTelephoneNumber(rs.getString("telephoneNumber"));
				user.setCreateDateTime(rs.getTimestamp("createDateTime"));
				user.setUpdateDateTime(rs.getTimestamp("updateDateTime"));
				user.setWithdrawalFlag(rs.getBoolean("withdrawalFlag"));

				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt);
		}
		return userList;
	}

	/**
	 * DBリソースをクローズします。
	 *
	 * @param con DB接続
	 * @param pstmt ステートメント
	 */
	private void closeResources(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}