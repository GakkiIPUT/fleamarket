/*
 * プロジェクト名：フリマシステム
 * プログラム名：Item.java
 * プログラムの説明：商品情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月19日
 * 作成者：大瀬莉晏
 */

package bean;

import java.sql.Date;

/**
 * 商品情報を保持するDTOクラスです。
 */
public class Item {

	//商品番号を格納する変数
	private int itemId;

	//出品者番号を格納する変数
	private int sellerId;

	//出品者番号を格納する変数
	private String sellerNickname;

	//購入者番号を格納する変数
	private int buyerId;

	//種類を格納する変数
	private String type;

	//出品名を格納する変数

	private String item;

	//個数を格納する変数
	private int quantity;

	//価格を格納する変数
	private int price;

	//手数料を格納する変数
	private int commission;

	//備考欄を格納する変数
	private String description;

	//画像ファイル名を格納する変数
	private String image;

	//売上を格納する変数
	private int proceeds;

	//出品ステータスを格納する変数
	private int listStatus;

	//取引情報ステータスを格納する変数
	private int transactionStatus;

	//支払い方法を格納する変数
	private int payment;

	//購入日時を格納する変数

	private Date buyDateTime;

	//登録日時を格納する変数
	private Date createDateTime;

	//更新日時を格納する変数
	private Date updateDateTime;

	/**
	 * 引数なしコンストラクタです。
	 */
	public Item() {

		this.itemId = 0;

		this.sellerId = 0;

		this.sellerNickname = null;

		this.buyerId = 0;

		this.type = null;

		this.item = null;

		this.quantity = 0;

		this.price = 0;

		this.commission = 0;

		this.description = null;

		this.image = null;

		this.proceeds = 0;

		this.listStatus = 0;

		this.transactionStatus = 0;

		this.payment = 0;

		this.buyDateTime = null;

		this.createDateTime = null;

		this.updateDateTime = null;

	}

	/**
	 * 商品IDを取得します。
	 * @return itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * 商品IDを設定します。
	 * @param itemId 商品ID
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * 出品者IDを取得します。
	 * @return 出品者ID
	 */
	public int getSellerId() {
		return sellerId;
	}

	/**
	 * 出品者IDを設定します。
	 * @param sellerId 出品者ID
	 */
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	
	/**
	 * 出品者のニックネームを取得します。
	 * @return 出品者ニックネーム
	 */
	public String getSellerNickname() {
		return sellerNickname;
	}
	
	/**
	 * 出品者のニックネームを設定します。
	 * @param sellerNickname 
	 */
	public void setSellerNickname(String sellerNickname) {
		this.sellerNickname = sellerNickname;
	}
	
	/**
	 * 購入者IDを取得します。
	 * @return 購入者ID
	 */
	public int getBuyerId() {
		return buyerId;
	}

	/**
	 * 購入者IDを設定します。
	 * @param buyerId 購入者ID
	 */
	public void setBuyerId(int buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * 種類を取得します。
	 * @return 種類
	 */
	public String getType() {
		return type;
	}

	/**
	 * 種類を設定します。
	 * @param type 種類
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 出品名を取得します。
	 * @return item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * 出品名を設定します。
	 * @param item 出品名
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * 個数を取得します。
	 * @return 個数
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 個数を設定します。
	 * @param quantity 個数
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 価格を取得します。
	 * @return 価格
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * 価格を設定します。
	 * @param price 価格
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * 手数料を取得します。
	 * @return 手数料
	 */
	public int getCommission() {
		return commission;
	}

	/**
	 * 手数料を設定します。
	 * @param commission 手数料
	 */
	public void setCommission(int commission) {
		this.commission = commission;
	}

	/**
	 * 備考欄を取得します。
	 * @return itemId
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 備考欄を設定します。
	 * @param itemId 備考欄
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 画像ファイル名者を取得します。
	 * @return 画像ファイル名
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 画像ファイル名を設定します。
	 * @param image 画像ファイル名
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 売上を取得します。
	 * @return 売上
	 */
	public int getProceeds() {
		return proceeds;
	}

	/**
	 * 売上を設定します。
	 * @param proceeds 売上
	 */
	public void setProceeds(int proceeds) {
		this.proceeds = proceeds;
	}

	/**
	 * 出品ステータスを取得します。
	 * @return 種類
	 */
	public int getListStatus() {
		return listStatus;
	}

	/**
	 * 出品ステータスを設定します。
	 * @param listStatus 出品ステータス
	 */
	public void setListStatus(int listStatus) {
		this.listStatus = listStatus;
	}

	/**
	 * 取引情報ステータスを取得します。
	 * @return item
	 */
	public int getTransactionStatus() {
		return transactionStatus;
	}

	/**
	 * 取引情報ステータスを設定します。
	 * @param item 取引情報ステータス
	 */
	public void setTransactionStatus(int transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	/**
	 * 支払い方法を取得します。
	 * @return 支払い方法
	 */
	public int getPayment() {
		return payment;
	}

	/**
	 * 支払い方法を設定します。
	 * @param quantity 支払い方法
	 */
	public void setPayment(int payment) {
		this.payment = payment;
	}

	/**
	 * 購入日時を取得します。
	 * @return 購入日時
	 */
	public Date getBuyDateTime() {
		return buyDateTime;
	}

	/**
	 * 購入日時を設定します。
	 * @param price 購入日時
	 */
	public void setBuyDateTime(Date buyDateTime) {
		this.buyDateTime = buyDateTime;
	}

	/**
	 * 登録日時を取得します。
	 * @return 登録日時
	 */
	public Date getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * 登録日時を設定します。
	 * @param commission 登録日時
	 */
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * 更新日時を取得します。
	 * @return 更新日時
	 */
	public Date getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * 更新日時を設定します。
	 * @param commission 更新日時
	 */
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
}