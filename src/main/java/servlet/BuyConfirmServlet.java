/*
 * プログラム名：フリマシステム
 * プログラムの説明：フリマシステムで、購入物情報確認のためのプログラムです。
 * 作成者：田中杏佳
 * 作成日：2026/06/22
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
import dao.ItemDAO;
import dao.OrderDAO;
import dao.UserDAO;
import util.SendMail;

@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//エラーメッセージを表示するための変数
		String error = null;
		//遷移先を指定するための変数
		String cmd = null;
		boolean forwarded = false;

		try {
			//jsp画面(Web)から受け取るデータの文字コードを設定する
			request.setCharacterEncoding("UTF-8");

			//取引情報を受け取る
			HttpSession session = request.getSession();
			if (session == null || session.getAttribute("user") == null) {
				response.sendRedirect("/view/login.jsp");
				return;
			}
			User userObj = (User) session.getAttribute("user");
			if (userObj == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				forwarded = true;
				return;
			}

			String itemIdParam = request.getParameter("itemId");
			String priceParam = request.getParameter("price");
			String paymentMethodParam = request.getParameter("paymentMethod");
			if (itemIdParam == null || itemIdParam.trim().isEmpty() || priceParam == null || priceParam.trim().isEmpty()
					|| paymentMethodParam == null || paymentMethodParam.trim().isEmpty()) {
				error = "購入情報が不正です。";
				cmd = "top";
			} else {
				//パラメーターから itemId を受け取り、DAOで再取得する
				int itemId = Integer.parseInt(itemIdParam);
				ItemDAO itemDao = new ItemDAO();
				Item itemObj = itemDao.selectByItem(itemId);

				if (itemObj == null) {
					error = "対象商品が存在しません。";
					cmd = "top";
				} else {
					//購入者ユーザーID
					int buyerId = userObj.getUserId();

					//値段
					int price = Integer.parseInt(priceParam);
					//システム手数料
					int commission = price * 1 / 10;
					//売上
					int proceed = price - commission;
					//支払い方法
					int paymentMethod = Integer.parseInt(paymentMethodParam);

					//取引ステータスが「0：出品済み」以外の場合
					if (itemObj.getListStatus() != 0) {
						error = "売り切れ、もしくは公開停止されているため購入できません。";
						cmd = "/list";
						
					} else {
						//取引ステータス更新（「1：完売」へ）
						itemObj.setListStatus(1);

						itemObj.setTransactionStatus(4);
						
						//Itemオブジェクトに取引情報を格納
						//購入者ユーザーID
						itemObj.setBuyerId(buyerId);
						//システム手数料
						itemObj.setCommission(commission);
						//売上
						itemObj.setProceeds(proceed);
						//支払い方法
						itemObj.setPayment(paymentMethod);
						//商品ID
						itemObj.setItemId(itemId);

						//DBに取引情報登録
						OrderDAO orderDaoObj = new OrderDAO();
						orderDaoObj.insert(itemObj);

						// -----出品者への購入通知メール送信処理-----
						try {
							UserDAO userDao = new UserDAO();
							User seller = userDao.selectById(itemObj.getSellerId());

							// 出品者の情報が存在し、メールアドレスが登録されている場合のみ送信
							if (seller != null && seller.getMail() != null && !seller.getMail().isEmpty()) {
								String to = seller.getMail();
								String subject = "【神田雑貨店 フリマシステム】出品した商品が購入されました";
								String body = seller.getNickname() + " 様\n\n"
										+ "出品された以下の商品が購入されました。\n\n"
										+ "【商品情報】\n"
										+ "商品名：" + itemObj.getItem() + "\n"
										+ "価格 ：" + price + "円\n\n"
										+ "ご購入者様からの入金をお待ちいただき、入金確認後に商品の発送をお願いいたします。\n"
										+ "現在の取引状況はマイページよりご確認いただけます。\n\n"
										+ "--------------------------------------------------\n"
										+ "神田雑貨店 フリマシステム\n"
										+ "--------------------------------------------------";

								SendMail sendMail = new SendMail();
								sendMail.sendMail(to, subject, body);
							}
						} catch (Exception e) {
							// メール送信エラーが発生しても購入処理自体は完了させるため、ログのみ出力
							System.out.println("出品者への通知メール送信に失敗しました：" + e.getMessage());
							e.printStackTrace();
						}
						// -----出品者への購入通知メール送信処理終わり-----

						//リクエストに"item"という名前でItemオブジェクトを格納
						request.setAttribute("item", itemObj);
						//エラーがない場合、buyConfirm.jspへフォワード
						request.getRequestDispatcher("/view/buyConfirm.jsp").forward(request, response);
						forwarded = true;
						return;
					}
				}
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、購入は出来ません。";
			cmd = "logout";
			System.out.print(e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			error = "購入情報の値が不正です。";
			cmd = "top";
			e.printStackTrace();
		} catch (NullPointerException e) {
			error = "購入処理中に予期しない状態が発生しました。";
			cmd = "top";
			e.printStackTrace();
		} catch (Exception e) {
			error = "予期せぬエラーが発生しました。";
			cmd = "logout";
			e.printStackTrace();
		} finally {
			//エラーがある場合、error.jspへフォワード
			if (!forwarded && error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

			}
		}
	}
}
