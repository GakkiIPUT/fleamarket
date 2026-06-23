/*
 * プロジェクト名：フリマシステム
 * プログラム名：ListServlet.java
 * プログラムの説明：商品一覧の取得および画面表示を制御するサーブレットクラス。
 * 作成日：2026年6月19日
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
 * 書籍一覧画面（list.jsp）の表示処理を制御するサーブレットクラスです。
 * データベースから全書籍情報を取得し、JSPへフォワードします。
 */
@WebServlet("/list")
public class ListServlet extends HttpServlet {

	/**
	 * 出品一覧を取得して一覧画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 制御用の変数を初期化
		String path = "/view/list.jsp";
		String error = null;
		String cmd = "menu";

		try {
			ItemDAO dao = new ItemDAO();
			ArrayList<Item> list = dao.selectAll();
			request.setAttribute("item_list", list);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、一覧表示は行えませんでした。"+e;
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
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
	/**
	 * POSTリクエストをdoGetに委譲します。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// POSTで送られてきても、上で書いたdoGetと同じ処理を実行させる
		doGet(request, response);
	}
}