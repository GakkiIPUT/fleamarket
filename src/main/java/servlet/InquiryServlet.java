/*
 * プログラム名：フリマシステム
 * プログラムの説明：会員が問い合わせを送信するためのプログラムです。
 * 作成者：栗原紫苑
 * 作成日：2026年6月22日
 * 
 */

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Inquiry;
import dao.InquiryDAO;

@WebServlet("/inquiry")
public class InquiryServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String error = null;
		String cmd = "list";
		String path = "/list";
		boolean isRedirect = false;

		try {
//			HttpSession session = request.getSession();
//			User user = (User) session.getAttribute("user");
//
//			if (user == null) {
//				error = "セッション切れの為、お問い合わせ処理は行えませんでした。";
//				cmd = "logout";
//				path = "/view/error.jsp";
//				return;
//			}

			// JSPから送られてきた「どっちのボタンが押されたか」のフラグを取得
			String action = request.getParameter("action");

			// フォームから送られてきた入力値を取得
			String subject = request.getParameter("subject");
			String comments = request.getParameter("comments");

			// =================================================================
			// パターン1：「確認ボタン」が押された場合（入力チェックをして確認画面へ）
			// =================================================================
			if ("confirm".equals(action)) {
				boolean hasError = false;

				// 入力チェック（バリデーション）
				if (subject == null || subject.trim().isEmpty()) {
					request.setAttribute("subjectErr", "件名が未入力です");
					hasError = true;
				}
				if (comments == null || comments.trim().isEmpty()) {
					request.setAttribute("commentsErr", "お問い合わせ内容が未入力です");
					hasError = true;
				}

				// エラーがあった場合は入力画面にもどす
				if (hasError) {
					request.setAttribute("subjectVal", subject);
					request.setAttribute("commentsVal", comments);

					path = "/view/inquiry.jsp";
					return;
				}

				// エラーが無ければ、確認画面へ進む
				request.setAttribute("subject", subject);
				request.setAttribute("comments", comments);

				path = "/view/inquiryCheck.jsp";
				return;

			}
			// =================================================================
			// パターン2：「送信ボタン」が押された場合（DBへ登録して完了画面へ）
			// =================================================================

			if ("send".equals(action)) {
				Inquiry inquiry = new Inquiry();
				//User有効になり次第変更
				// inquiry.setUserId(user.getUserId());
				inquiry.setUserId(1);
				inquiry.setSubject(subject);
				inquiry.setComments(comments);

				InquiryDAO dao = new InquiryDAO();
				dao.insert(inquiry);

				// 登録成功時はトップ画面へ戻る
				request.setAttribute("message", "お問い合わせの送信が完了しました。");
				path = request.getContextPath() + "/list"; 
				isRedirect = true; 
				return;
			}

			
		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、お問い合わせの送信は行えませんでした。";
			path = "/view/error.jsp";
			cmd = "logout";
		} catch (RuntimeException e) {
			e.printStackTrace(); 
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
			cmd = "logout";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			if (isRedirect && error == null) {
				response.sendRedirect(path);
			} else {
				request.getRequestDispatcher(path).forward(request, response);
			}
		}
	}
}