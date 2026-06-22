/*
 * プロジェクト名：フリマシステム
 * プログラム名：SendMail.java
 * プログラムの説明：JavaMail APIを利用して指定された宛先へメールを送信するユーティリティクラス。
 * 作成日：2026年6月19日
 * 作成者：栗原紫苑
 */

package util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * JavaMail APIを利用してメール送信を行うユーティリティクラスです。
 */
public class SendMail {

	/**
	 * 指定された宛先、件名、本文でメールを送信します。
	 *
	 * @param to 送信先メールアドレス（購入したユーザーのアドレス）
	 * @param subject メールの件名
	 * @param body メールの本文（商品の購入・発送など）
	 */
	public void sendMail(String to, String subject, String body) {
		try {
			Properties props = System.getProperties();

			// サンプルプログラムに準拠したpstmtPサーバーの設定
			props.put("mail.smtp.host", "sv5215.xserver.jp");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.debug", "true");

			// 認証情報の生成
			Session session = Session.getInstance(
					props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("test.sender@kanda-it-school-system.com",
									"kandaSender-2025");
						}
					});

			MimeMessage mimeMessage = new MimeMessage(session);

			// 送信元メールアドレスと送信者名（神田IT School）を指定
			mimeMessage.setFrom(
					new InternetAddress("test.sender@kanda-it-school-system.com", "神田IT School", "iso-2022-jp"));

			// 送信先メールアドレス（引数のto）を指定
			mimeMessage.setRecipients(Message.RecipientType.TO, to);
			// メールのタイトルを指定
			mimeMessage.setSubject(subject, "iso-2022-jp");

			// メールの内容を指定
			mimeMessage.setText(body, "iso-2022-jp");

			// メールの形式（プレーンテキスト・文字コード）を指定
			mimeMessage.setHeader("Content-Type", "text/plain; charset=iso-2022-jp");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// メールの送信を実行
			Transport.send(mimeMessage);
			System.out.println("メールの送信に成功しました。");

		} catch (Exception e) {
			// 万が一送信に失敗した場合、システム全体が停止しないようコンソールにログを残す
			System.out.println("メールの送信に失敗しました。\n" + e);
		}
	}
}