/* 
 * プログラム名：G組チーム3フリマシステム（DeleteItemServlet.java）
 * プログラムの説明：商品削除処理を制御するサーブレット
 * 
 * 作成者：林 佑実
 * 作成日：2026年6月5日
 */

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Item;
import dao.ItemDAO;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{

		//削除する商品の商品IDを取得し、変数に代入
		request.setCharacterEncoding("UTF-8");
		String strItemId = request.getParameter("itemId");
		int itemId = Integer.parseInt(strItemId);
			
		//削除する商品の商品IDに合致する商品のItemオブジェクトを取得
		ItemDAO itemDaoObj = new ItemDAO();
		Item itemObj = itemDaoObj.selectByMyItem(itemId);
		
		//商品削除処理を実行する
		itemDaoObj.delete(itemObj);
		
		//ListServletにフォワードする
		request.getRequestDispatcher("/myItems").forward(request, response);

	}
}