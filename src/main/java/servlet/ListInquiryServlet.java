/*
 * プロジェクト名：フリマシステム
 * プログラム名：ListInquiryServlet
 * プログラムの説明：問い合わせ一覧を表示するサーブレットクラス。
 * 作成日：2026年6月23日
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

import bean.Inquiry;
import dao.InquiryDAO;

@WebServlet("/admin/inquiries")
public class ListInquiryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null;
		String path = "/view/listInquiry.jsp";
		String cmd = "menu";
		try {
			HttpSession session = request.getSession();
//			User user = (User) session.getAttribute("user");
//
//			// セッションから"user"のUserオブジェクトを取得する(セッション切れチェック)
//			if (user == null) {
//				path = "/view/error.jsp";
//				error = "セッション切れの為、お問い合わせ一覧は表示出来ません。";
//				cmd = "logout";
//				return;
//			}
			// DAOを使って全件取得
			InquiryDAO dao = new InquiryDAO();
			ArrayList<Inquiry> list = dao.selectAll();

			// リクエストスコープに格納
			request.setAttribute("inquiry_list", list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、お問い合わせ一覧は表示できませんでした。";
			path = "/view/error.jsp";
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
		} catch (Exception e) {
			path = "/view/error.jsp";
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		}finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}