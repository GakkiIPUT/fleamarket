package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Comment;
import bean.Item; // 商品情報Beanを想定
import bean.User;
import dao.CommentDAO;
import dao.ItemDAO; // 商品DAOを想定

@WebServlet("/insertComment")
public class InsertCommentServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String itemIdStr = request.getParameter("itemId");
		String content = request.getParameter("content");

		String error = null;
		String path = "/detail?itemId=" + itemIdStr; // 元の商品詳細画面に戻る想定

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			if (user == null) {
				error = "コメントを投稿するにはログインが必要です。";
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
				return;
			}

			int itemId = Integer.parseInt(itemIdStr);
			// ログイン中ユーザーのIDを取得（メソッド名はUser Beanに合わせてください）
			int userId = user.getUserId();

			// ★出品者フラグの判定ロジック
			int isSeller = 0;
			ItemDAO itemDao = new ItemDAO();
			Item item = itemDao.selectByItemId(itemId);
			// ログインユーザーIDと、商品の出品者IDが一致するかチェック
			if (item != null && item.getUserId() == userId) {
				isSeller = 1; // 出品者ならフラグを立てる
			}

			// コメント情報のセット
			Comment comment = new Comment();
			comment.setItemId(itemId);
			comment.setUserId(userId);
			comment.setContent(content);
			comment.setIsSeller(isSeller);

			// DBに保存
			CommentDAO commentDao = new CommentDAO();
			commentDao.insert(comment);

		} catch (Exception e) {
			e.printStackTrace();
			error = "コメントの登録中にエラーが発生しました。";
			request.setAttribute("error", error);
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			return;
		}

		// 保存後は元の詳細画面にリダイレクト（再読み込みによる二重投稿防止）
		response.sendRedirect(request.getContextPath() + path);
	}
}