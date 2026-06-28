/*
 * プロジェクト名：フリマシステム
 * プログラム名：ListUserServlet.java
 * プログラムの説明：ユーザー一覧の表示および検索を制御するサーブレットクラス。
 * 作成日：2026年6月23日
 * 作成者：大瀬莉晏
 */
package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

/**
 * 出品者一覧の表示と検索を制御するサーブレットクラスです。
 */
@WebServlet("/admin/saleUsers")
public class ListSaleUserServlet extends HttpServlet {
	/**
	 * ユーザー一覧の表示または検索結果を取得してフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = "/view/listUser.jsp";
		String error = null;
		String cmd = "";
		try {

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// セッション切れチェック
			if (user == null) {
				error = "セッション切れの為、ユーザ一覧表示は行えません。";
				cmd = "logout";
				path = "/view/error.jsp";
				return;
			}

			UserDAO dao = new UserDAO();
			List<User> userList = null;
			String searchUserid = request.getParameter("searchUserid");

			// 検索ワードの有無で全件検索か曖昧検索かを分岐
			if (searchUserid == null || searchUserid.equals("")) {
				userList = dao.AllSellerUsers();
				cmd = "allSeller";
			} else {
				userList = dao.SellerUsersSearch(searchUserid);
				cmd = "allSeller";
			}

			request.setAttribute("user_list", userList);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、ユーザ一覧は表示出来ません。";
			path = "/view/error.jsp";
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.setAttribute("cmd", cmd);
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	/**
	 * POSTリクエストをdoGetに委譲します。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POSTで送られてきても、上で書いたdoGetと同じ処理を実行させる
		doGet(request, response);
	}
}