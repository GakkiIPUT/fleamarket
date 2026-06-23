/*
 * プロジェクト名：フリマシステム
 * プログラム名：SalesDAO.java
 * プログラムの説明：売上情報を取得するSQLの実行を担当する。
 * 作成日：2026年6月19日
 * 作成者：栗原紫苑
 */

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Sales;

/**
 * 売上集計情報を取得するDAOクラスです。
 */
public class SalesDAO {
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/flea_market_db";
	private static String USER = "root";
	private static String PASSWD = "root123";

	/**
	 * データベースへのコネクションを取得します。
	 * @return 確立されたデータベース接続
	 * @throws IllegalStateException JDBCドライバーのロードや接続に失敗した場合
	 */
	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			return DriverManager.getConnection(URL, USER, PASSWD);
		} catch (Exception e) {
			throw new IllegalStateException("DB接続エラー",e);
		}
	}
	
	
	public ArrayList<Sales> selectBySaleAll(int user_id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Sales> SalesList = new ArrayList<>();
        
        String sql = "SELECT item, price, commission, buy_date_time FROM item_info "
        		+ "WHERE seller_id = ? AND transaction_status = 3";
        // 1つ目の ? : ログイン中の出品者の user_id (int)
        // ※ transaction_status = 3（取引完了）になったものだけを売上として合算。

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();
                
                //値をカラムに入れる
                sale.setItem(rs.getString("item"));
                sale.setPrice(rs.getInt("price"));
                sale.setCommission(rs.getInt("commission"));
                sale.setBuyDateTime(rs.getDate("buy_date_time"));
                
                SalesList.add(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException("クエリ発行エラー", e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return SalesList;
	}
	

	/**
	 * 年月ごとの売上情報を取得します。
	 *
	 * @param year 集計対象の年
	 * @param month 集計対象の月
	 * @return 売上集計の一覧
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public ArrayList<Sales> selectBySales(String year, String month) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<Sales> SalesList = new ArrayList<>();

		// 月が一桁の場合は頭に0をつける
		if (month != null && month.length() == 1) {
			month = "0" + month;
		}

		String sql = "SELECT item_id, item, price, commission, image, buy_date_time "
	               + "FROM item_info "
	               + "WHERE transaction_status = 3 AND buy_date_time LIKE ? "
	               + "ORDER BY buy_date_time DESC";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, year + "-" + month + "-%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
	            Sales sale = new Sales();
	            
	            //値をカラムに入れる
	            sale.setItemId(rs.getInt("item_id"));
	            sale.setItem(rs.getString("item"));
	            sale.setPrice(rs.getInt("price"));
	            sale.setCommission(rs.getInt("commission"));
	            sale.setImage(rs.getString("image"));
	            sale.setBuyDateTime(rs.getDate("buy_date_time"));
	            
	            SalesList.add(sale);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ignore) {
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException ignore) {
			}
		}
		return SalesList;
	}
}