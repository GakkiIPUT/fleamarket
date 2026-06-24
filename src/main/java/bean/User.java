/*
 * プロジェクト名：フリマシステム
 * プログラム名：User.java
 * プログラムの説明：ユーザー情報を保持するDTO（Data Transfer Object）クラス。
 * 作成日：2026年6月19日
 * 作成者：中田佳葉
 */

package bean;

import java.sql.Timestamp;

public class User {

	//ユーザーIDを格納する変数
	private int userId;
	
	//ログインIDを格納する変数
	private int loginId;
		
	//メールアドレスを格納する変数
	private String mail;

	//パスワードを格納する変数
	private String password;

	//ニックネームを格納する変数
	private String nickname;

	//姓を格納する変数
	private String lastName;

	//名を格納する変数
	private String firstName;

	//姓カナを格納する変数
	private String lastNameRubi;

	//名カナを格納する変数
	private String firstNameRubi;

	//郵便番号を格納する変数
	private String postCode;

	//都道府県を格納する変数
	private String prefectures;

	//市区町村を格納する変数
	private String city;

	//番地を格納する変数
	private String streetAddress;

	//建物名・号室を格納する変数
	private String buildingRoom;

	//電話番号を格納する変数
	private String telephoneNumber;

	//登録日時を格納する変数
	private Timestamp createDateTime;

	//更新日時を格納する変数
	private Timestamp updateDateTime;

	//退会フラグ（0.有効 , 1.退会済み）を格納する変数
	private int withdrawalFlag;

	//権限フラグ（0.管理者 , 1.会員）を格納する変数
	private int authorityFlag;

	/**
	 * 引数なしコンストラクタです。
	 */
	public User() {
		this.userId = 0;
		this.loginId = 0;
		this.mail = null;
		this.password = null;
		this.nickname = null;
		this.lastName = null;
		this.firstName = null;
		this.lastNameRubi = null;
		this.firstNameRubi = null;
		this.postCode = null;
		this.prefectures = null;
		this.city = null;
		this.streetAddress = null;
		this.buildingRoom = null;
		this.telephoneNumber = null;
		this.createDateTime = null;
		this.updateDateTime = null;
		this.withdrawalFlag = 0;
		this.authorityFlag = 0;
	}

	/**
	 * ユーザーIDを取得します。
	 * @return ユーザーID
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * ユーザーIDを設定します。
	 * @param userId ユーザーID
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * メールアドレスを取得します。
	 * @return メールアドレス
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * メールアドレスを設定します。
	 * @param userId メールアドレス
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * ログインIDを取得します。
	 * @return ユーザーID
	 */
	public int getLoginId() {
		return loginId;
	}

	/**
	 * ログインIDを設定します。
	 * @param userId ユーザーID
	 */
	public void setLoginId(int loginId) {
		this.loginId = loginId;;
	}

	/**
	 * パスワードを取得します。
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定します。
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ニックネームを取得します。
	 * @return ニックネーム
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 *ニックネームを設定します。
	 * @param ニックネーム
	 */

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 姓を取得します。
	 * @return 姓
	 */

	public String getLastName() {
		return lastName;
	}

	/**
	 * 姓を設定します。
	 * @param 姓
	 */

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 名を取得します。
	 * @return 名
	 */

	public String getFirstName() {
		return firstName;
	}

	/**
	 *名を設定します。
	 * @param 名
	 */

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 姓カナを取得します。
	 * @return 姓カナ
	 */

	public String getLastNameRubi() {
		return lastNameRubi;
	}

	/**
	 * 姓カナを設定します。
	 * @param 姓カナ
	 */

	public void setLastNameRubi(String lastNameRubi) {
		this.lastNameRubi = lastNameRubi;
	}

	/**
	 * 名カナを取得します。
	 * @return 名カナ
	 */

	public String getFirstNameRubi() {
		return firstNameRubi;
	}

	/**
	 * 名カナを設定します。
	 * @param 名カナ
	 */

	public void setFirstNameRubi(String firstNameRubi) {
		this.firstNameRubi = firstNameRubi;
	}

	/**
	 * 郵便番号を取得します。
	 * @return 郵便番号
	 */

	public String getPostCode() {
		return postCode;
	}

	/**
	 * 郵便番号を設定します。
	 * @param 郵便番号
	 */

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 都道府県を取得します。
	 * @return 
	 */

	public String getPrefectures() {
		return prefectures;
	}

	/**
	 * 都道府県を設定します。
	 * @param 都道府県
	 */

	public void setPrefectures(String prefectures) {
		this.prefectures = prefectures;
	}

	/**
	 * 市区町村を取得します。
	 * @return 都道府県
	 */

	public String getCity() {
		return city;
	}

	/**
	 * 市区町村を設定します。
	 * @param 市区町村
	 */

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * 番地を取得します。
	 * @return 番地
	 */

	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * 番地を設定します。
	 * @param 番地
	 */

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * 建物名・号室を取得します。
	 * @return 建物名・号室
	 */

	public String getBuildingRoom() {
		return buildingRoom;
	}

	/**
	 * 建物名・号室を設定します。
	 * @param 建物名・号室
	 */

	public void setBuildingRoom(String buildingRoom) {
		this.buildingRoom = buildingRoom;
	}

	/**
	 * 電話番号を取得します。
	 * @return 電話番号
	 */

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	/**
	 * 電話番号を設定します。
	 * @param 電話番号
	 */

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	/**
	 * 登録日時を取得します。
	 * @return 
	 */

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	/**
	 * 登録日時を設定します。
	 * @param 登録日時
	 */

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	/**
	 * 更新日時を取得します。
	 * @return 
	 */

	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	/**
	 * 更新日時を設定します。
	 * @param 更新日時
	 */

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	/**
	 * 退会フラグ（0.有効 , 1.退会済み）を取得します。
	 * @return 
	 */
	public int getWithdrawalFlag() {
		return withdrawalFlag;
	}

	/**
	 * 退会フラグ（0.有効 , 1.退会済み）を設定します。
	 * @param 退会フラグ（0.有効 , 1.退会済み）
	 */

	public void setWithdrawalFlag(int withdrawalFlag) {
		this.withdrawalFlag = withdrawalFlag;
	}

	/**
	 * 権限フラグ（0.管理者 , 1.会員）を取得します。
	 * @return 権限フラグ（0.管理者 , 1.会員）
	 */
	public int getAuthorityFlag() {
		return authorityFlag;
	}

	/**
	 * 権限フラグ（0.管理者 , 1.会員）を設定します。
	 * @param userId 権限フラグ（0.管理者 , 1.会員）
	 */
	public void setAuthorityFlag(int authorityFlag) {
		this.authorityFlag = authorityFlag;
	}

}