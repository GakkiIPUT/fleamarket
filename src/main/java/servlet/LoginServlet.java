package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

/**
 * パスワード保護のため doPost で処理を行う。
 * ログインフォームの POST を処理します。
 * UserDAO による認証を行い、成功時はセッションとクッキーを設定してメニュー画面へ遷移、
 * 失敗時はログイン画面へ戻します。DB エラー時はエラーページへフォワードします。
 */
public class LoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 入力パラメータ（email, password）を取得する
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// 制御用の変数を初期化
		String path = "/view/login.jsp";
		String error = null;
		String cmd = "logout";

		try {
			// UserDAOをインスタンス化し、結合検索メソッドを呼び出す
			UserDAO userDaoObj = new UserDAO();
			User user = userDaoObj.selectByUser(email, password);

			// User情報取得の有無で遷移先を切り替える
			if (user != null && user.getUserId() != 0) {
				// ログイン成功時：セッションスコープに"user"という名前で登録
				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				// クッキーにメールアドレスのみを登録する（期間は5日間 = 60秒 * 60分 * 24時間 * 5日）
				Cookie cookieEmail = new Cookie("savedEmail", email);
				cookieEmail.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(cookieEmail);

				// 権限フラグ(authority_flag)によって遷移先を分岐
				if (user.getAuthorityFlag() == 1) {
					// 管理者の場合：管理者メニュー画面へ
					path = "/view/adminMenu.jsp"; 
				} else {
					// 一般ユーザーの場合：商品一覧画面を司るサーブレットへ
					path = "/list"; 
				}
			} else {
				// ログイン失敗時
				request.setAttribute("message", "メールアドレス、またはパスワードが間違っています。");
				path = "/view/login.jsp";
			}

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、ログインは出来ません。";
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
		} catch (Exception e) {
			path = "/view/error.jsp";
			error = "予期せぬエラーが発生しました。" + e.getMessage();
			e.printStackTrace();
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			// 決定したpathへフォワード
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/view/login.jsp").forward(request, response);
	}
}