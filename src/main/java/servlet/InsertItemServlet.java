/*
 * プロジェクト名：フリマアプリG組チーム3
 * プログラム名：InsertItemServlet.java
 * プログラムの説明：商品の登録処理を制御するサーブレットクラス。
 * 作成日：2026年6月19日
 * 作成者：林佑実
 */
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Item;
import dao.ItemDAO;

@WebServlet("/insertItem")
public class InsertItemServlet extends HttpServlet {
	/**
	 * 商品情報の登録処理を実行し、トップ画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException{
		
		//入力データ取得
		request.setCharacterEncoding("UTF-8");
		
		String type = request.getParameter("type");
		String item = request.getParameter("item");
		String image = request.getParameter("image");
		String strprice = request.getParameter("price");
		String description = request.getParameter("description");
		
		int price = Integer.parseInt(strprice);
		
		//Itemオブジェクトに商品データを登録
		Item itemObj = new Item();
		
		itemObj.setType(type);
		itemObj.setItem(item);
		itemObj.setImage(image);
		itemObj.setPrice(price);
		itemObj.setDescription(description);
		
		//「登録」か「編集」かの判定
		String action = request.getParameter("action");
		
		if("update".equals(action)) {
			
			//Itemオブジェクトをセッションに登録する
			HttpSession session = request.getSession();
			session.setAttribute("item", itemObj);
			
			//insertItem.jspにフォワードする
			request.getRequestDispatcher("/view/insertItem.jsp").forward(request, response);
			
		} else if("insert".equals(action)){	//「登録」の場合
			
			//item_infoテーブルへ商品情報を登録
			ItemDAO itemDaoObj = new ItemDAO();
			itemDaoObj.insert(itemObj);
			
			//セッション上のItemオブジェクトをnullに戻す
			itemObj = null;
			HttpSession session = request.getSession();
			session.setAttribute("item", itemObj);
			
			//商品一覧サーブレットにフォワード
			request.getRequestDispatcher("/list").forward(request, response);
		}
	}
}