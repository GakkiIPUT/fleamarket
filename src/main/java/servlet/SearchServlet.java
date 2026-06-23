/*
 * プロジェクト名：フリマシステム
 * プログラム名：SearchServlet.java
 * プログラムの説明：商品情報の検索処理を制御するサーブレットクラス。
 * 作成日：2026年6月22日
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

/**
 * 書籍の絞り込み検索処理を制御するサーブレットクラスです。
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 制御用の変数を初期化
		String path = "/view/list.jsp";
		String error = null;
		String cmd = "menu";

		String itemName = request.getParameter("item");
		String type = request.getParameter("type");
		
		try {
			ItemDAO itemDao = new ItemDAO();
			ArrayList<Item> list = itemDao.search(itemName, type);
			request.setAttribute("item_list", list);

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、一覧表示は行えませんでした。";
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
		}  finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}