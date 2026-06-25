package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Item;
import bean.User;
import dao.ItemDAO;

/**
 * Servlet implementation class ShowMyItemsServlet
 */
@WebServlet("/myItems")
public class ShowMyItemsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//エラーメッセージを表示するための変数
		String error = null;
		//遷移先を指定するための変数
		String cmd = null;

		try {
			//ユーザーIDを取得する
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("user");
			
			//全出品物を取得してArrayListに格納する
			ItemDAO itemDaoObj = new ItemDAO();

			/////テスト用/////
//			int userId = 1;
//			ArrayList<Item> itemList = itemDaoObj.selectBySellerId(userId);

			//実際：
			ArrayList<Item> itemList = itemDaoObj.selectBySellerId(userObj.getUserId());

			//全出品物を格納したArrayListをリクエストスコープに登録
			request.setAttribute("item_list", itemList);

		} catch (IllegalStateException e) {
			throw new IllegalStateException("DBエラー", e);

		} finally {
			//エラーがある場合、error.jspへフォワード
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

				//エラーがない場合、buyConfirm.jspへフォワード
			} else {
				request.getRequestDispatcher("/view/showMyItems.jsp").forward(request, response);
			}

		}
	}

}