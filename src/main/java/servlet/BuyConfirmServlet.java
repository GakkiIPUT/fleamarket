/*
 * プログラム名：フリマシステム
 * プログラムの説明：フリマシステムで、購入物情報確認のためのプログラムです。
 * 作成者：田中杏佳
 * 作成日：2026/06/19
 * 
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
import bean.User;
import dao.OrderDAO;

@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//エラーメッセージを表示するための変数
		String error = null;
		//遷移先を指定するための変数
		String cmd = null;
		
		try {
			//jsp画面(Web)から受け取るデータの文字コードを設定する
			request.setCharacterEncoding("UTF-8");
			
			//取引情報を受け取る
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("user");
			Item itemObj = (Item) request.getAttribute("item");
			//購入者ユーザーID
			int buyerId = userObj.getUserId(); 
			//値段
			int price = Integer.parseInt(request.getParameter("price"));
			//システム手数料
			int commission = price * 1/10; 
			//売上
			int proceed = price - commission;
			//支払い方法
			int payment = Integer.parseInt(request.getParameter("payment"));
			//商品ID
			int itemId = itemObj.getItemId();

			//Itemオブジェクトに取引情報を格納
			//購入者ユーザーID
			itemObj.setBuyerId(buyerId);
			//システム手数料
			itemObj.setCommission(commission);
			//売上
			itemObj.setProceed(proceed);
			//支払い方法
			itemObj.setPayment(payment);
			//商品ID
			itemObj.setItemId(itemId);
			
			//DBに取引情報登録
			OrderDAO orderDaoObj = new OrderDAO();
			orderDaoObj.insert(itemObj);
				
			//リクエストに"item"という名前でItemオブジェクトを格納
			request.setAttribute("item", itemObj);
			
		}catch(IllegalStateException e) {
			throw new IllegalStateException("DBエラー", e);
			
		}finally {
			//エラーがある場合、error.jspへフォワード
			if(error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);	
			
			//エラーがない場合、buyConfirm.jspへフォワード
			}else {
				request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);
			}
		}
	}
}
