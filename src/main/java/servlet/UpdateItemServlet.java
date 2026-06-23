/* 
 * プログラム名：G組チーム3フリマシステム（UpdateItemServlet.java）
 * プログラムの説明：商品情報更新処理を制御するサーブレット
 * 
 * 作成者：林 佑実
 * 作成日：2026年6月22日
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

@WebServlet("/updateItem")
public class UpdateItemServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{				
		
		//文字コードを設定する
		request.setCharacterEncoding("UTF-8");
		
		//更新後の商品情報を取得し、変数に代入する
		String strItemId = request.getParameter("itemId");
		String image = request.getParameter("image");
		String item = request.getParameter("item");
		String type = request.getParameter("type");
		String strQuantity = request.getParameter("quantity");
		String strPrice = request.getParameter("price");
		String description = request.getParameter("description");
		
		//商品ID、個数、価格をint型に変更
		int itemId = Integer.parseInt(strItemId);
		int quantity = Integer.parseInt(strQuantity);
		int price = Integer.parseInt(strPrice);
		
		//価格に合わせて手数料、売上を変更する
		int commission = (int)(price * 0.1);
		int proceeds = price - commission;
;		
		//入力チェック
		//エラーメッセージと遷移先を変数に代入する
		
		//更新する商品の情報が格納されたItemオブジェクトを取得する
		ItemDAO itemDaoObj = new ItemDAO();
		Item itemObj = itemDaoObj.selectByMyItem(itemId);

		//Itemオブジェクトに更新する情報を格納する
		itemObj.setImage(image);
		itemObj.setItem(item);
		itemObj.setType(type);
		itemObj.setQuantity(quantity);
		itemObj.setPrice(price);
		itemObj.setCommission(commission);
		itemObj.setProceeds(proceeds);
		itemObj.setDescription(description);
		
		//ItemDAOクラスのupdateメソッドを呼び出し、更新処理を実行する
		itemDaoObj.update(itemObj);

		//ShowMyItemsServletへフォワードする
		request.getRequestDispatcher("/myItems").forward(request, response);
	}
}