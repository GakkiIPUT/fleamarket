/*
 * プログラム名：フリマシステム
 * プログラムの説明：フリマシステムで、データベースにアクセスして
 * 					購入情報について検索系、更新系のSQLを実行するプログラムです。
 * 作成者：田中杏佳
 * 作成日：2026/06/22
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bean.Item;

public class OrderDAO {
	//データベース接続情報
	private static String RDB_DRIVE = "com.mysql.cj.jdbc.Driver";
	private static String URL = "jdbc:mysql://localhost/flea_market_db";
	private static String USER = "root";
	private static String PASS = "root123";

	/**
	 * フィールド変数の情報を元にDB接続を行う。
	 * 
	 * @return Connectionオブジェクト
	 */
	private static Connection getConnection() {
		try {
			Class.forName(RDB_DRIVE);
			Connection con = DriverManager.getConnection(URL, USER, PASS);
			return con;
		} catch (Exception e) {
			throw new IllegalStateException("DBエラー", e);
		}
	}

	/**
	 * 引数の購入データを元にDBの取引情報テーブルに取引情報登録処理を行う
	 * 
	 * @param Itemオブジェクト
	 */
	public void insert(Item itemObj) {
		//DBと接続するためにConnectionクラスのオブジェクトを生成
		Connection con = null;
		//DBを操作するためにPreparedStatementクラスのオブジェクトを生成
		PreparedStatement ps = null;

		//SQL文を文字列として定義
		String sql = "UPDATE item_info SET buyer_id = ?, "
				+ "commission = ?, proceeds = ?, payment_method = ?, list_status = 1, transaction_status = 1, buy_date_time = NOW(), update_date_time = NOW() "
				+ "WHERE item_id = ? AND list_status = 0";

		try {
			//Connectionオブジェクトを定義
			con = getConnection();

			//PreparedStatementオブジェクトを定義
			ps = con.prepareStatement(sql);

			// 1〜4番目の ? : 購入者のuser_id(int), 手数料(int), 出品者の手取り(int), 支払方法(int) 5番目の ? : 対象の item_id (int)
			
			ps.setInt(1, itemObj.getBuyerId());
			ps.setInt(2, itemObj.getCommission());
			ps.setInt(3, itemObj.getProceeds());
			ps.setInt(4, itemObj.getPayment());
			ps.setInt(5, itemObj.getItemId());
			//SQL文を送信
			ps.executeUpdate();

			//例外処理
		} catch (Exception e) {
			throw new IllegalStateException("DBエラー", e);

		} finally {
			if (ps != null) {
				//PreparedStatementオブジェクトをクローズ
				try {
					ps.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				//Connectionオブジェクトをクローズ
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

}