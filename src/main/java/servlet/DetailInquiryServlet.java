/*
 * プロジェクト名：フリマシステム
 * プログラム名：ListInquiryServlet
 * プログラムの説明：問い合わせ詳細を表示するサーブレットクラス。
 * 作成日：2026年6月24日
 * 作成者：栗原紫苑
*/

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Inquiry;
import dao.InquiryDAO;

@WebServlet("/admin/detailInquiry")
public class DetailInquiryServlet extends HttpServlet {
	
	/**
	 * 【詳細表示処理】
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = "/view/inquiryDetail.jsp";
		String error = null;
		String cmd = "logout";

		try {
			HttpSession session = request.getSession();
			if (session == null || session.getAttribute("user") == null) {
				// 未ログインならログイン画面へリダイレクト
				path = "/login";
				return;
			}

			// URLにくっついてきた id (例: ?id=1) を取得
			String idStr = request.getParameter("id");
			if (idStr == null) {
				response.sendRedirect(request.getContextPath() + "/admin/inquiries");
				return;
			}

			int id = Integer.parseInt(idStr);
			InquiryDAO dao = new InquiryDAO();
			
			//既読に更新
			dao.updateReadingFlag(id);

			// 更新された状態の詳細データをDBから取得
			Inquiry inquiry = dao.selectById(id);

			request.setAttribute("inquiry", inquiry);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、詳細表示は出来ません。";
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
			path = "/view/error.jsp"; 
			cmd = "logout";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
	
	/**
	 * 【返信処理】
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String error = null;
		String path = "/view/error.jsp";
		String cmd = "logout";

		// フォームから送られてきた値を取得（inquiryId と replyComment）
		String idStr = request.getParameter("inquiryId");
		String replyComment = request.getParameter("replyComment");

		try {
			if (idStr == null) {
				error = "不正なアクセスです。";
				return;
			} if(replyComment == null || replyComment.trim().isEmpty()){
				error = "返信内容が空です。";
				return;
			}
		
			int inquiryId = Integer.parseInt(idStr);

			// 更新データをセット
			Inquiry inquiry = new Inquiry();
			inquiry.setInquiryId(inquiryId);
			inquiry.setAdministratorReply(replyComment);

			// DAOを呼び出して返信をデータベースに登録
			InquiryDAO dao = new InquiryDAO();
			dao.reply(inquiry);

			// 返信成功後、画面を再表示
			response.sendRedirect(request.getContextPath() + "/admin/detailInquiry?id=" + inquiryId);
			return;

		} catch (NumberFormatException e) {
			error = "お問い合わせIDの形式が不正です。";
		} catch (IllegalStateException e) {
			error = "DB更新エラーの為、返信の登録が出来ませんでした。";
			cmd = "logout";
		} catch (Exception e) {
			e.printStackTrace();
			error = "返信処理中に予期せぬエラーが発生しました。" + e.getMessage();
		}

		if (error != null) {
			request.setAttribute("error", error);
			request.setAttribute("cmd", cmd);
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}