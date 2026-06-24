/*
 * プロジェクト名：フリマシステム
 * プログラム名：InsertUserServlet.java
 * プログラムの説明：ユーザー登録処理を制御するサーブレットクラス。
 * 作成日：2026年6月22日
 * 作成者：中田佳葉
 */

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.User;
import dao.UserDAO;

/**
 * ユーザー登録処理を制御するサーブレットクラスです。
 */
@WebServlet("/insertUser")
public class InsertUserServlet extends HttpServlet {
	/**
	 * ユーザー登録処理を実行し、結果画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 制御用の変数を初期化
		String path = "/list";
		String error = null;
		String cmd = "logout";

		try {

			String lastName = request.getParameter("lastName");
			String firstName = request.getParameter("firstName");
			String nickname = request.getParameter("nickname");
			String mail = request.getParameter("mail");
			String lastNameRubi = request.getParameter("lastNameRubi");
			String firstNameRubi = request.getParameter("firstNameRubi");
			String password = request.getParameter("password");
			String passwordConfirm = request.getParameter("passwordConfirm");
			String postCode = request.getParameter("postCode");
			String prefectures = request.getParameter("prefectures");
			String city = request.getParameter("city");
			String streetAddress = request.getParameter("streetAddress");
			String buildingRoom = request.getParameter("buildingRoom");
			String telephoneNumber = request.getParameter("telephoneNumber");

			int authorityFlag = Integer.parseInt(request.getParameter("authorityFlag"));

			// 入力値のエラーチェック

			if (lastName == null || lastName.isEmpty()) {
				error = "姓入力値不正の為、登録できません。";
				return;
			}
			if (firstName == null || firstName.isEmpty()) {
				error = "名入力値不正の為、登録できません。";
				return;
			}
			if (lastNameRubi == null || lastNameRubi.isEmpty()) {
				error = "姓カナ入力値不正の為、登録できません。";
				return;
			}
			if (firstNameRubi == null || firstNameRubi.isEmpty()) {
				error = "名カナ入力値不正の為、登録できません。";
				return;
			}
			if (nickname == null || nickname.isEmpty()) {
				error = "ニックネーム入力値不正の為、登録できません。";
				return;
			}
			if (mail == null || mail.isEmpty()) {
			    error = "メールアドレス入力値不正の為、登録できません。";
			    return;
			}
			if (password == null || password.isEmpty()) {
				error = "パスワード入力値不正の為、登録できません。";
				return;
			}
			if (passwordConfirm == null || passwordConfirm.isEmpty()) {
				error = "パスワード(確認用)入力値不正の為、登録できません。";
				return;
			}
			if (postCode == null || postCode.isEmpty()) {
				error = "郵便番号入力値不正の為、登録できません。";
				return;
			}
			if (prefectures == null || prefectures.isEmpty()) {
				error = "都道府県入力値不正の為、登録できません。";
				return;
			}
			if (city == null || city.isEmpty()) {
				error = "市区町村入力値不正の為、登録できません。";
				return;
			}
			if (streetAddress == null || streetAddress.isEmpty()) {
				error = "番地入力値不正の為、登録できません。";
				return;
			}
			if (telephoneNumber == null || telephoneNumber.isEmpty()) {
				error = "電話番号入力値不正の為、登録できません。";
				return;
			}
			if (buildingRoom == null || buildingRoom.isEmpty()) {
				error = "建物名・号室入力値不正の為、登録できません。";
				return;
			}
			if (authorityFlag == 0) {
				error = "権限が未選択の為、登録できません。";
				return;
			}
			if (!password.equals(passwordConfirm)) {
				error = "入力パスワードがパスワード(確認用)と一致しない為、登録できません。";
				return;
			}

			// エラーがなければ登録
			User newUser = new User();
			newUser.setLastName(lastName);
			newUser.setFirstName(firstName);
			newUser.setNickname(nickname);
			newUser.setMail(mail); 
			newUser.setLastNameRubi(lastNameRubi);
			newUser.setFirstNameRubi(firstNameRubi);
			newUser.setPassword(password);
			
			newUser.setPostCode(postCode);
			newUser.setPrefectures(prefectures);
			newUser.setCity(city);
			newUser.setStreetAddress(streetAddress);
			newUser.setBuildingRoom(buildingRoom);
			newUser.setTelephoneNumber(telephoneNumber);
			newUser.setAuthorityFlag(authorityFlag);
			

			UserDAO userDao = new UserDAO();
			userDao.insert(newUser, password);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザー登録処理は行えませんでした。";
			cmd = "logout";
			e.printStackTrace();

		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			if (error != null) {
				path = "/view/error.jsp";
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}