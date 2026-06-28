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
			throw new IllegalStateException("DB接続エラー", e);
		}
	}

	public ArrayList<Sales> selectBySaleAll(int user_id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ArrayList<Sales> SalesList = new ArrayList<>();

        String sql = "SELECT item_id,item, price, image, commission, buy_date_time FROM item_info "
                + "WHERE seller_id = ? AND transaction_status = 4";
        // 1つ目の ? : ログイン中の出品者の user_id (int)
        // ※ transaction_status = 4（取引完了）になったものだけを売上として合算。

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();

                //値をカラムに入れる
                sale.setItem(rs.getString("item"));
                sale.setItemId(rs.getInt("item_id"));
                sale.setPrice(rs.getInt("price"));
                sale.setImage(rs.getString("image"));
                sale.setCommission(rs.getInt("commission"));
                sale.setBuyDateTime(rs.getDate("buy_date_time"));

                SalesList.add(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException("クエリ発行エラー", e);
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return SalesList;
    }
	
	 /**
     * 管理者用：全期間の完了済み全取引を詳細付きで取得
     */
    public ArrayList<Sales> selectAllSales() {
        ArrayList<Sales> list = new ArrayList<>();
        String sql = "SELECT i.item_id, i.item, u1.nickname as seller_name, u2.nickname as buyer_name, " +
                     "i.price, i.commission, i.buy_date_time " +
                     "FROM item_info i " +
                     "JOIN user_info u1 ON i.seller_id = u1.user_id " +
                     "JOIN user_info u2 ON i.buyer_id = u2.user_id " +
                     "WHERE i.transaction_status = 4 " +
                     "ORDER BY i.buy_date_time DESC";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Sales s = new Sales();
                s.setItemId(rs.getInt("item_id"));
                s.setItem(rs.getString("item"));
                s.setSellerName(rs.getString("seller_name"));
                s.setBuyerName(rs.getString("buyer_name"));
                s.setPrice(rs.getInt("price"));
                s.setCommission(rs.getInt("commission"));
                s.setBuyDateTime(rs.getDate("buy_date_time"));
                list.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("売上全件取得エラー", e);
        }
        return list;
    }

    /**
     * 月別検索用：年月指定で取得
     */
    public ArrayList<Sales> selectBySales(String year, String month) {
        ArrayList<Sales> SalesList = new ArrayList<>();
        if (month != null && month.length() == 1) {
            month = "0" + month;
        }

        String sql = "SELECT item_id, item, price, commission, image, buy_date_time "
                   + "FROM item_info "
                   + "WHERE transaction_status = 4 AND buy_date_time LIKE ? "
                   + "ORDER BY buy_date_time DESC";
        
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, year + "-" + month + "-%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setItemId(rs.getInt("item_id"));
                sale.setItem(rs.getString("item"));
                sale.setPrice(rs.getInt("price"));
                sale.setCommission(rs.getInt("commission"));
                sale.setImage(rs.getString("image"));
                sale.setBuyDateTime(rs.getDate("buy_date_time"));
                SalesList.add(sale);
            }
        } catch (SQLException e) {
            throw new RuntimeException("月別売上取得エラー", e);
        }
        return SalesList;
    }
}