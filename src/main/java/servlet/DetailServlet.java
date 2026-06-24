/*
 * プロジェクト名：フリマシステム
 * プログラム名：DetailServlet.java
 * プログラムの説明：商品のの詳細情報表示、および購入画面への遷移を制御する。
 * 作成日：2026年6月22日
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

import bean.Comment;
import bean.Item;
import dao.CommentDAO;
import dao.ItemDAO;

/**
 * 商品の詳細情報表示するサーブレットクラスです。
 * 一覧画面から渡された商品番号をもとにデータベースを検索し、結果をJSPへ渡します。
 *  存在しなければエラーページへフォワードします。
 */
@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String path = "/view/detail.jsp";
		String error = null;
		String cmd = "list";

		int itemId = Integer.parseInt(request.getParameter("itemId"));
		String cmdParam = request.getParameter("cmd");

		try {
			ItemDAO dao = new ItemDAO();
			Item item = dao.selectByItem(itemId);

			request.setAttribute("item", item);

			// 該当商品(itemId)のコメント一覧を取得する
			CommentDAO commentDao = new CommentDAO();
			List<Comment> commentList = commentDao.selectByItemId(itemId);

			// 取得したコメント一覧をリクエストスコープにセット
			request.setAttribute("commentList", commentList);

		} catch (IllegalStateException e) {
			if ("itemID".equals(cmdParam)) {
				error = "DB接続エラーの為、詳細画面は表示できませんでした。";
				path = "/view/error.jsp";
				cmd = "logout";
			}
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