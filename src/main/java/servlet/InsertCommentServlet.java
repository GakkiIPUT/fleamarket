package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Comment;
import bean.User;
import dao.CommentDAO;

@WebServlet("/insertComment")
public class InsertCommentServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String error = null;
		String cmd = "";
		String path = "/list";

		try {
			// 1. セッションからログイン中のユーザー情報を取得
			HttpSession session = request.getSession();
			if (session == null || session.getAttribute("user") == null) {
				// 未ログインならログイン画面へリダイレクト
				response.sendRedirect("/login");
				return;
			}			User user = (User) session.getAttribute("user");

			if (user == null) {
				error = "セッションが切れました。再度ログインしてください。";
				cmd = "logout";
				return;
			}

			// 2. JSPのフォームから送信されたデータを受け取る
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			String commentText = request.getParameter("comment");

			// JSPから「この投稿者は出品者か？」の判定結果を受け取る (0 or 1)
			int sellerFlag = 0;
			String sellerFlagStr = request.getParameter("sellerFlag");
			if (sellerFlagStr != null && !sellerFlagStr.isEmpty()) {
				sellerFlag = Integer.parseInt(sellerFlagStr);
			}

			// 3. 受け取ったデータをBeanにセット
			Comment commentObj = new Comment();
			commentObj.setItemId(itemId);
			commentObj.setUserId(user.getUserId());
			commentObj.setComment(commentText);
			commentObj.setSellerFlag(sellerFlag);

			// 4. DAOを使ってDBにINSERT
			CommentDAO commentDao = new CommentDAO();
			commentDao.insert(commentObj);

			// 5. 投稿完了後、元の詳細画面へリダイレクト（再読み込みによる二重投稿を防止）
			response.sendRedirect(request.getContextPath() + "/detail?itemId=" + itemId);
			return;

		} catch (Exception e) {
			e.printStackTrace();
			error = "コメントの投稿中にエラーが発生しました。";
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}