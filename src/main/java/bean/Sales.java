/*
 * プロジェクト名：フリマサイト
 * プログラム名：Sales.java
 * プログラムの説明：売上集計情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月19日
 * 作成者：栗原紫苑
 */

package bean;

import java.sql.Date;

/**
 * 売上集計情報を保持するDTOクラスです。
 */
public class Sales {
	private int itemId;
	private String item;
	private int price;
	private int commission;
	private String image;
	private Date buyDateTime;

	private String sellerName;
	private String buyerName;

	/**
	 * 引数なしコンストラクタです。
	 */
	public Sales() {
		this.itemId = 0;
		this.item = null;
	}

	/**
	 * 商品情報から売上情報を生成します。
	 *
	 * @param item 商品情報
	 */
	public Sales(Item item, int quantity) {
		this.itemId = item.getItemId();
		this.item = item.getItem();
		this.price = item.getPrice();
		this.commission = item.getCommission();
		this.image = item.getImage();
		this.buyDateTime = item.getBuyDateTime();
	}

	/**
	 * itemIdを取得します。
	 * @return itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * itemIdを設定します。
	 * @param itemId itemId
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * 商品名を取得します。
	 * @return 商品名
	 */
	public String getItem() {
		return item;
	}

	/**
	 * タイトルを設定します。
	 * @param item タイトル
	 */
	public void setItem(String item) {
		this.item = item;
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
	public void setCommission(int quantity) {
		this.commission = quantity;
	}

	/**
	 * 画像を取得します。
	 * @return 画像リンク
	 */
	public String getImage() {
		return image;
	}

	/**
	 * 画像リンクを設定します
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 購入日付を取得します。
	 * @return
	 */
	public Date getBuyDateTime() {
		return this.buyDateTime;
	}

	/**
	 * 購入日付を設定します
	 * @param buyDateTime
	 */
	public void setBuyDateTime(Date buyDateTime) {
		this.buyDateTime = buyDateTime;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

}