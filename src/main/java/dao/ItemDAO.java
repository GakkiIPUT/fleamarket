/*
 * プロジェクト名：フリマシステム
 * プログラム名：OrderedItemDAO.java
 * プログラムの説明：
 * 作成日：2026年6月19日
 * 作成者：大瀬莉晏
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Item;

/**
 * 商品の情報を取得するDAOクラスです。
 */
public class ItemDAO {
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
			System.out.print(e.getStackTrace());
			System.out.print(e.getClass());

			throw new IllegalStateException("DB接続エラー", e);
		}
	}

	
	/**	 *
	 * @return 商品の一覧
	 * @throws IllegalStateException データベースエラーが発生した場合
	 */
	public ArrayList<Item> selectAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<Item> itemList = new ArrayList<>();

		// SQL文例: isbnを条件に結合
		String sql = "SELECT * FROM  item_info";

		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Item item = new Item();

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

				itemList.add(item);
			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		// 検索結果が格納されたitemオブジェクトを返す
		return itemList;
	}

	/**
	 * 引数で受け取った商品情報をデータベースに新規登録します。
	 * @param Item 登録する商品情報が格納されたitemオブジェクト
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void insert(Item item) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO item_info (seller_id, type, item, quantity, price, commission, description, image, proceeds, list_status, transaction_status, create_date_time, update_date_time) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, NOW(), NOW())";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			//パラメータに値をセット
			pstmt.setInt(1, item.getSellerId());
			pstmt.setString(2, item.getType());
			pstmt.setString(3, item.getItem());
			pstmt.setInt(4, item.getQuantity());
			pstmt.setInt(5, item.getPrice());
			pstmt.setInt(6, item.getCommission());
			pstmt.setString(7, item.getDescription());
			pstmt.setString(8, item.getImage());
			pstmt.setInt(9, item.getProceeds());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で指定された商品番号に合致する情報をデータベースから検索します。
	 * @param itemID 検索対象の商品番号
	 * @return 検索結果が格納されたItemオブジェクト（見つからない場合は初期状態のItemkオブジェクト）
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public Item selectByItem(int itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// 戻り値用のitemインスタンスを生成（初期状態）
		Item item = new Item();
		String sql = "SELECT * FROM item_info WHERE  item_id LIKE ?  ORDER BY create_date_time DESC";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			// SELECT文を組み立てて実行
			ResultSet rs = pstmt.executeQuery();

			// 結果セットからデータが存在するか確認
			if (rs.next()) {
				// データが存在する場合、DTOに値をセット

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		// 検索結果が格納されたitemオブジェクトを返す
		return item;
	}
	/**
	 * 出品した商品の内、引数で指定された商品番号に合致する情報をデータベースから検索します。
	 * @param itemID 検索対象の商品番号
	 * @return 検索結果が格納されたItemオブジェクト（見つからない場合は初期状態のItemkオブジェクト）
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public Item selectByMyItem(int itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// 戻り値用のBookインスタンスを生成（初期状態）
		Item item = new Item();
		String sql = "SELECT * FROM item_info WHERE item_id = ?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);
			// SELECT文を組み立てて実行
			ResultSet rs = pstmt.executeQuery();

			// 結果セットからデータが存在するか確認
			if (rs.next()) {
				// データが存在する場合、DTOに値をセット

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
		// 検索結果が格納されたitemオブジェクトを返す
		return item;
	}
	/**
	 * 引数で指定された商品番号の商品情報をデータベースから削除します。
	 * @param itemId 削除対象の商品番号
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void delete(int itemId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM item_info WHERE itemId = ?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, itemId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で受け取った商品情報で、データベースの該当レコードを更新します。
	 * @param Item 更新する商品情報が格納されたItemオブジェクト
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public void update(Item item) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE item_info SET sellerId= ?, buyerId=? , type= ?, item=? , "
				+ "Quantity=? ,price= ?, commission=? , description= ?, image=? , proceeds=? , "
				+ "listStatus=? ,transaction_status= ?, payment_method=? , buyDateTime= ?, createDateTime=? , "
				+ "updateDateTime=? ,ItemId=? WHERE item.getItemId=?";
		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, item.getSellerId());
			pstmt.setInt(2, item.getBuyerId());
			pstmt.setString(3, item.getType());
			pstmt.setString(4, item.getItem());
			pstmt.setInt(5, item.getQuantity());
			pstmt.setInt(6, item.getPrice());
			pstmt.setInt(7, item.getCommission());
			pstmt.setString(8, item.getDescription());
			pstmt.setString(9, item.getImage());
			pstmt.setInt(10, item.getProceeds());
			pstmt.setInt(11, item.getListStatus());
			pstmt.setInt(12, item.getTransactionStatus());
			pstmt.setInt(13, item.getPayment());
			pstmt.setDate(14, item.getBuyDateTime());
			pstmt.setDate(15, item.getCreateDateTime());
			pstmt.setDate(16, item.getUpdateDateTime());
			pstmt.setInt(17, item.getItemId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}

	/**
	 * 引数で受け取った条件（商品名、種類）で書籍情報を曖昧検索します。
	 * @param item 検索条件：商品番号（部分一致）
	 * @param type 検索条件：種類（部分一致）
	 * @return 検索条件に合致した書籍情報を含むArrayList
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public ArrayList<Item> search(String itemName, String type) {
		Connection con = null;
		PreparedStatement pstmt = null;// 戻り値として返すためのArrayListを初期化
		ArrayList<Item> itemList = new ArrayList<Item>();
		String sql = "SELECT * FROM item_info WHERE list_status = 0 AND (item LIKE ? OR type LIKE ?) ORDER BY create_date_time DESC";

		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + itemName + "%");
			pstmt.setString(2, "%" + type + "%");

			ResultSet rs = pstmt.executeQuery();
			// 結果セットのカーソルを順次進めながら全行を処理する
			while (rs.next()) {

				// 1行分のデータを格納するためのitemインスタンスを生成				
				Item item = new Item();

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

				//
				itemList.add(item);
			}
		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);
		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		} // 作成した書籍リストを返す
		return itemList;
	}

	/**
	 * 引数で受け取った条件（出品者ID）で商品情報を検索します。
	 * @param sellerId 検索条件：出品者ID
	 * @return 検索条件に合致した商品情報を含むArrayList
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public ArrayList<Item> selectBySellerId(int sellerId) {
		Connection con = null;
		PreparedStatement pstmt = null;// 戻り値として返すためのArrayListを初期化
		ArrayList<Item> itemList = new ArrayList<Item>();
		String sql = "SELECT * FROM item_info WHERE seller_id = ? ORDER BY create_date_time DESC";

		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sellerId);

			ResultSet rs = pstmt.executeQuery();
			// 結果セットのカーソルを順次進めながら全行を処理する
			while (rs.next()) {

				// 1行分のデータを格納するためのitemインスタンスを生成				
				Item item = new Item();

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

				//ArrayListにオブジェクトを格納
				itemList.add(item);

			}

		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);

		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}

		return itemList;

	}


	/**
	 * 引数で受け取った条件（購入者ID）で商品情報を検索します。
	 * @param buyerId 検索条件：購入者ID
	 * @return 検索条件に合致した商品情報を含むArrayList
	 * @throws IllegalStateException データベース処理中にSQL例外が発生した場合
	 */
	public ArrayList<Item> selecBuyerId(int buyerId) {
		Connection con = null;
		PreparedStatement pstmt = null;// 戻り値として返すためのArrayListを初期化
		ArrayList<Item> itemList = new ArrayList<Item>();
		String sql = "SELECT * FROM item_info WHERE  buyer_id = ? ORDER BY create_date_time DESC";

		try {
			// DB接続を取得
			con = getConnection();
			// SQLを発行するためのPreparedStatementオブジェクトを生成

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, buyerId);

			ResultSet rs = pstmt.executeQuery();
			// 結果セットのカーソルを順次進めながら全行を処理する
			while (rs.next()) {

				// 1行分のデータを格納するためのitemインスタンスを生成				
				Item item = new Item();

				item.setItemId(rs.getInt("item_id"));
				item.setSellerId(rs.getInt("seller_id"));
				item.setBuyerId(rs.getInt("buyer_id"));
				item.setType(rs.getString("type"));
				item.setItem(rs.getString("item"));
				item.setQuantity(rs.getInt("quantity"));
				item.setPrice(rs.getInt("price"));
				item.setCommission(rs.getInt("commission"));
				item.setDescription(rs.getString("description"));
				item.setImage(rs.getString("image"));
				item.setProceeds(rs.getInt("proceeds"));
				item.setListStatus(rs.getInt("list_status"));
				item.setTransactionStatus(rs.getInt("transaction_status"));
				item.setPayment(rs.getInt("payment_method"));
				item.setBuyDateTime(rs.getDate("buy_date_time"));
				item.setCreateDateTime(rs.getDate("create_date_time"));
				item.setUpdateDateTime(rs.getDate("update_date_time"));

				//ArrayListにオブジェクトを格納
				itemList.add(item);

			}

		} catch (SQLException e) {
			// SQL実行時にエラーが発生した場合
			throw new RuntimeException("クエリ発行エラー", e);

		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}

		return itemList;

	}

}
