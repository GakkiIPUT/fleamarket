/*
 * プロジェクト名：フリマシステム
 * プログラム名：UserDAO.java
 * プログラムの説明：DB接続とuser_infoテーブルに対するユーザー情報の取得や
 * 					パスワードに合致する情報を取得するSQL実行を担当する。
 * 作成日：2026年6月19日
 * 作成者：中田佳葉
*/

package servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
		 * メールアドレスとパスワードに基づいてユーザーを認証し、
		 * 詳細情報を一括で取得します。（ログイン処理用）
		 * 認証の際、アカウントが「凍結されていないこと(freeze_flag=0)」かつ
		 * 「退会していないこと(withdrawal_flag=0)」を条件とします。
		 *
		 * @param mail 入力されたメールアドレス
		 * @param password 照合するパスワード
		 * @return 認証成功時はユーザーの詳細情報が格納されたUserオブジェクト、失敗時はnull
		 */
	public User selectByUser(String mail, String password) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		// login(l) と user_info(u) を結合し、未凍結かつ未退会のユーザーを検索
		String sql = "SELECT l.login_id, l.mail, l.authority_flag, "
				+ "u.user_id, u.nickname, u.last_name, u.first_name, u.last_name_rubi, u.first_name_rubi, "
				+ "u.post_code, u.prefectures, u.city, u.street_address, u.building_room, u.telephone_number "
				+ "FROM login l "
				+ "LEFT JOIN user_info u ON l.login_id = u.login_id "
				+ "WHERE l.mail = ? AND l.password = ? AND l.freeze_flag = 0 "
				+ "AND (u.withdrawal_flag = 0 OR u.withdrawal_flag IS NULL)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mail);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setLoginId(rs.getInt("login_id"));
				user.setMail(rs.getString("mail"));
				user.setAuthorityFlag(rs.getInt("authority_flag"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastNameRubi(rs.getString("last_name_rubi"));
				user.setFirstNameRubi(rs.getString("first_name_rubi"));
				user.setPostCode(rs.getString("post_code"));
				user.setPrefectures(rs.getString("prefectures"));
				user.setCity(rs.getString("city"));
				user.setStreetAddress(rs.getString("street_address"));
				user.setBuildingRoom(rs.getString("building_room"));
				user.setTelephoneNumber(rs.getString("telephone_number"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("ログイン認証時のクエリ発行に失敗しました。", e);
		} finally {
			closeResources(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * 新規ユーザーを登録します。
	 * データの不整合を防ぐため、
	 * loginテーブルへのインサートと
	 * user_infoテーブルへのインサートを同一トランザクション内で実行します。
	 *
	 * @param user 登録するユーザー詳細情報（Userオブジェクト）
	 * @param password 登録するパスワード文字列
	 */
	public void insert(User user, String password) {
		Connection con = null;
		PreparedStatement pstmtLogin = null;
		PreparedStatement pstmtInfo = null;
		ResultSet rs = null;

		String sqlLogin = "INSERT INTO login (password, mail, authority_flag, freeze_flag) VALUES (?, ?, 0, 0)";
		String sqlInfo = "INSERT INTO user_info (login_id, nickname, last_name, first_name, last_name_rubi, first_name_rubi, "
				+ "post_code, prefectures, city, street_address, building_room, telephone_number, create_date_time, update_date_time, withdrawal_flag) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 0)";

		try {
			con = getConnection();
			con.setAutoCommit(false); // トランザクション制御開始

			// 1. loginテーブルにデータを挿入し、自動発番された login_id を取得
			pstmtLogin = con.prepareStatement(sqlLogin, Statement.RETURN_GENERATED_KEYS);//自動採番されたidを取得できるはず
			pstmtLogin.setString(1, password);
			pstmtLogin.setString(2, user.getMail());
			pstmtLogin.executeUpdate();

			rs = pstmtLogin.getGeneratedKeys();
			int generatedLoginId = 0;
			if (rs.next()) {
				generatedLoginId = rs.getInt(1);
			}

			// 2. 取得した login_id を用いて user_info テーブルに詳細情報を挿入
			pstmtInfo = con.prepareStatement(sqlInfo);
			pstmtInfo.setInt(1, generatedLoginId);
			pstmtInfo.setString(2, user.getNickname());
			pstmtInfo.setString(3, user.getLastName());
			pstmtInfo.setString(4, user.getFirstName());
			pstmtInfo.setString(5, user.getLastNameRubi());
			pstmtInfo.setString(6, user.getFirstNameRubi());
			pstmtInfo.setString(7, user.getPostCode());
			pstmtInfo.setString(8, user.getPrefectures());
			pstmtInfo.setString(9, user.getCity());
			pstmtInfo.setString(10, user.getStreetAddress());
			pstmtInfo.setString(11, user.getBuildingRoom());
			pstmtInfo.setString(12, user.getTelephoneNumber());
			pstmtInfo.executeUpdate();

			con.commit(); // すべて成功したらコミット
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback(); // エラー時はロールバック
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw new RuntimeException("新規ユーザー登録処理に失敗しました。", e);
		} finally {
			if (pstmtInfo != null) {
				try {
					pstmtInfo.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			closeResources(con, pstmtLogin, rs);
		}
	}

	/**
	 * 既存ユーザーの登録情報を更新します。（マイページ等の編集用）
	 * メールアドレス(loginテーブル)と詳細情報(user_infoテーブル)を同時に更新します。
	 *
	 * @param user 更新する情報が含まれたUserオブジェクト
	 */
	public void update(User user) {
		Connection con = null;
		PreparedStatement pstmtInfo = null;
		PreparedStatement pstmtLogin = null;

		String sqlInfo = "UPDATE user_info SET nickname = ?, last_name = ?, first_name = ?, last_name_rubi = ?, first_name_rubi = ?, "
				+ "post_code = ?, prefectures = ?, city = ?, street_address = ?, building_room = ?, telephone_number = ?, update_date_time = NOW() "
				+ "WHERE user_id = ?";
		String sqlLogin = "UPDATE login SET mail = ? WHERE login_id = ?";

		try {
			con = getConnection();
			con.setAutoCommit(false); // トランザクション開始

			// 1. user_info テーブルの更新
			pstmtInfo = con.prepareStatement(sqlInfo);
			pstmtInfo.setString(1, user.getNickname());
			pstmtInfo.setString(2, user.getLastName());
			pstmtInfo.setString(3, user.getFirstName());
			pstmtInfo.setString(4, user.getLastNameRubi());
			pstmtInfo.setString(5, user.getFirstNameRubi());
			pstmtInfo.setString(6, user.getPostCode());
			pstmtInfo.setString(7, user.getPrefectures());
			pstmtInfo.setString(8, user.getCity());
			pstmtInfo.setString(9, user.getStreetAddress());
			pstmtInfo.setString(10, user.getBuildingRoom());
			pstmtInfo.setString(11, user.getTelephoneNumber());
			pstmtInfo.setInt(12, user.getUserId());
			pstmtInfo.executeUpdate();

			// 2. login テーブルのメールアドレス更新
			pstmtLogin = con.prepareStatement(sqlLogin);
			pstmtLogin.setString(1, user.getMail());
			pstmtLogin.setInt(2, user.getLoginId());
			pstmtLogin.executeUpdate();

			con.commit(); // コミット
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			throw new RuntimeException("ユーザー情報の更新に失敗しました。", e);
		} finally {
			if (pstmtInfo != null) {
				try {
					pstmtInfo.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			closeResources(con, pstmtLogin, null);
		}
	}

	/**
	 * ユーザーの退会処理を行います（論理削除）。
	 * データを物理削除せず、withdrawal_flag を 1 (退会状態) に更新します。
	 *
	 * @param userId 退会させるユーザーのuser_id
	 */
	public void delete(int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		String sql = "UPDATE user_info SET withdrawal_flag = 1, update_date_time = NOW() WHERE user_id = ?";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("退会処理（論理削除）クエリの発行に失敗しました。", e);
		} finally {
			closeResources(con, pstmt, null);
		}
	}

	/**
	 * 指定されたユーザーID(user_id)に紐づく詳細情報を1件取得します。
	 *
	 * @param userId 検索対象のuser_id
	 * @return ユーザー情報が格納されたUserオブジェクト。存在しない場合はnull
	 */
	public User selectById(int userId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		String sql = "SELECT u.user_id, l.login_id, l.mail, l.authority_flag, "
				+ "u.nickname, u.last_name, u.first_name, u.last_name_rubi, u.first_name_rubi, "
				+ "u.post_code, u.prefectures, u.city, u.street_address, u.building_room, u.telephone_number, "
				+ "u.create_date_time, u.update_date_time,u.withdrawal_flag "
				+ "FROM user_info u JOIN login l ON u.login_id = l.login_id "
				+ "WHERE u.user_id = ?;";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setLoginId(rs.getInt("login_id"));
				user.setMail(rs.getString("mail"));
				user.setAuthorityFlag(rs.getInt("authority_flag"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastNameRubi(rs.getString("last_name_rubi"));
				user.setFirstNameRubi(rs.getString("first_name_rubi"));
				user.setPostCode(rs.getString("post_code"));
				user.setPrefectures(rs.getString("prefectures"));
				user.setCity(rs.getString("city"));
				user.setStreetAddress(rs.getString("street_address"));
				user.setBuildingRoom(rs.getString("building_room"));
				user.setTelephoneNumber(rs.getString("telephone_number"));
				user.setCreateDateTime(rs.getTimestamp("create_date_time"));
				user.setUpdateDateTime(rs.getTimestamp("update_date_time"));
				user.setWithdrawalFlag(rs.getInt("withdrawal_flag"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("ユーザー詳細情報の取得に失敗しました。", e);
		} finally {
			closeResources(con, pstmt, rs);
		}
		return user;
	}

	/**
	 * 全ユーザーの一覧を取得します（管理者用機能）。
	 * 名前、ニックネーム、メールアドレス、権限を一括で取得します。
	 *
	 * @return 登録されている全ユーザーのリスト
	 */
	public List<User> selectAllUsers() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<>();

		String sql = "SELECT u.user_id, l.login_id, l.mail, l.authority_flag, "
				+ "u.nickname, u.last_name, u.first_name "
				+ "FROM user_info u "
				+ "JOIN login l ON u.login_id = l.login_id "
				+ "ORDER BY u.user_id DESC";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setLoginId(rs.getInt("login_id"));
				user.setMail(rs.getString("mail"));
				user.setAuthorityFlag(rs.getInt("authority_flag"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("ユーザー一覧の取得に失敗しました。", e);
		} finally {
			closeResources(con, pstmt, rs);
		}
		return userList;
	}

	/**		大瀬追加
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
		String sql = "SELECT u.*, l.mail FROM user_info u JOIN login l ON u.login_id = l.login_id WHERE u.withdrawal_flag = 0   AND (u.last_name LIKE ? OR u.first_name LIKE ? OR u.nickname LIKE ?)";
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + userId + "%");
			pstmt.setString(2, "%" + userId + "%");
			pstmt.setString(3, "%" + userId + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				user.setMail(rs.getString("mail"));

				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			closeResources(con, pstmt, null);
		}
		return userList;
	}

	/**
	 * 全ユーザーの一覧を取得します（管理者用機能）。
	 * 名前、ニックネーム、メールアドレス、権限を一括で取得します。
	 *
	 * @return 登録されている全ユーザーのリスト
	 */
	public List<User> AllSellerUsers() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> userList = new ArrayList<>();

		String sql = "SELECT DISTINCT u.user_id, l.login_id, l.mail, i.seller_id, "
				+ "u.nickname, u.last_name, u.first_name "
				+ "FROM user_info u "
				+ "JOIN login l ON u.login_id = l.login_id "
				+ "JOIN item_info i ON u.user_id = i.seller_id "
				+ "ORDER BY u.user_id";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("seller_id"));
				user.setMail(rs.getString("mail"));

				user.setNickname(rs.getString("nickname"));
				user.setLastName(rs.getString("last_name"));
				user.setFirstName(rs.getString("first_name"));
				userList.add(user);
			}
		} catch (SQLException e) {
			throw new RuntimeException("ユーザー一覧の取得に失敗しました。", e);
		} finally {
			closeResources(con, pstmt, rs);
		}
		return userList;
	}

	/**
	/**
	* DBリソースをクローズします。
	*
	* @param con DB接続
	* @param pstmt ステートメント
	*/
	private void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
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