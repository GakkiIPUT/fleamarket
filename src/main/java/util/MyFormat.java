/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：MyFormat.java
 * プログラムの説明：金額などの表示形式を変換するユーティリティクラス。
 * 作成日：2026年6月3日
 * 作成者：髙垣湧侑翔
 */

package util;

import java.text.DecimalFormat;

/**
 * 金額表示のフォーマットを提供するユーティリティクラスです。
 */
public class MyFormat {
	/**
	 * 金額を通貨表示の形式に変換します。
	 *
	 * @param price 金額
	 * @return 通貨形式に整形した文字列
	 */
	public String moneyFormat(int price) {

		DecimalFormat df = new DecimalFormat("\u00A5#,###");
		return df.format(price);

	}
}