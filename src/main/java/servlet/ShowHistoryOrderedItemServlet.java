/*
 * プロジェクト名：書籍管理システムWeb版Ver3.0
 * プログラム名：ShowHistoryOrderedItemServlet.java
 * プログラムの説明：ログインユーザー個人の購入履歴表示を制御するサーブレットクラス。
 * 作成者：大瀬莉晏
*/
package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Item;
import dao.ItemDAO;

@WebServlet("/history")
public class ShowHistoryOrderedItemServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/view/showHistoryOrderedItem.jsp";
		String error = null;
		String cmd = "logout";

		try {
			
			/*
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			
			if (user == null) {
				error="セッション切れの為、購入状況の確認は出来ません。";
				cmd= "logout";
				path = "/view/error.jsp";
				return;
			}
			*/
			
			// DAOからそのユーザー個人の購入履歴（リスト）を取得    user.getUserId()
			ItemDAO dao = new ItemDAO();
			ArrayList<Item> list = dao.selecBuyerId(1);

			// 取得したListをリクエストスコープに格納
			request.setAttribute("order_list", list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入状況確認は出来ません。";
			path = "/view/error.jsp";
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}