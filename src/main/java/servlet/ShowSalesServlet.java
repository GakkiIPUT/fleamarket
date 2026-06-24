/*
 * プロジェクト名：フリマシステム
 * プログラム名：ShowSalesServlet
 * プログラムの説明：現在までの売上を表示するサーブレットクラス。
 * 作成日：2026年6月19日
 * 作成者：栗原紫苑
*/

package servlet;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Sales;
import bean.User;
import dao.SalesDAO;


@WebServlet("/sales")
public class ShowSalesServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/view/showSales.jsp";
		String error = "";
		String cmd = "logout";

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null) {
				error="セッション切れの為、購入状況の確認は出来ません。";
				cmd= "logout";
				path = "/view/error.jsp";
				return;
			}

			// DAOから売上履歴を取得
			SalesDAO salesDaoObj = new SalesDAO();
			ArrayList<Sales> list = salesDaoObj.selectBySaleAll(user.getUserId());
			
			// 取得したListをリクエストスコープに格納
			request.setAttribute("salesList", list);
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、売上げ状況は表示出来ません。";
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}