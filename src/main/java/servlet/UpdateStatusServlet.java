/*
 * プログラム名：フリマシステム
 * プログラムの説明：取引のステータス（入金、発送など）を更新し、必要な場合はメールを送信するプログラムです。
 * 作成者：髙垣湧侑翔
 * 作成日：2026/06/24
 * */

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
import dao.UserDAO;
import util.SendMail;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// POSTからの呼び出しにも対応できるよう共通化
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String error = null;
		String cmd = null;

		try {
			request.setCharacterEncoding("UTF-8");

			// セッションからログイン中のユーザー情報を取得
			HttpSession session = request.getSession();
			User userObj = (User) session.getAttribute("user");

			if (userObj == null) {
				error = "セッションがタイムアウトしました。もう一度ログインしてください。";
				cmd = "logout";
				return;
			}

			// パラメーターから 対象のitemId と 変更後のステータス を受け取る
			int itemId = Integer.parseInt(request.getParameter("itemId"));
			int nextStatus = Integer.parseInt(request.getParameter("transactionStatus"));
			
			// 最新の商品情報を取得
			ItemDAO itemDao = new ItemDAO();
			Item itemObj = itemDao.selectByItem(itemId);

			// 取引ステータスを更新してDBに保存
			itemObj.setTransactionStatus(nextStatus);
			itemDao.update(itemObj);

			// -----------------------------------------------------------
			// 出品者が商品を発送した場合、購入者へ通知メールを送信
			// -----------------------------------------------------------
			if (nextStatus == 4) {
				try {
					UserDAO userDao = new UserDAO();
					User buyer = userDao.selectById(itemObj.getBuyerId());

					// 購入者の情報が存在し、メールアドレスが登録されている場合のみ送信
					if (buyer != null && buyer.getMail() != null && !buyer.getMail().isEmpty()) {
						String to = buyer.getMail();
						String subject = "【神田雑貨店 フリマシステム】商品の発送が完了しました";
						String body = buyer.getNickname() + " 様\n\n"
								+ "ご購入いただいた以下の商品が、出品者より発送されました。\n\n"
								+ "【商品情報】\n"
								+ "商品名：" + itemObj.getItem() + "\n\n"
								+ "商品が到着しましたら、内容をご確認いただき、受取完了の手続きをお願いいたします。\n"
								+ "現在の取引状況はマイページよりご確認いただけます。\n\n"
								+ "--------------------------------------------------\n"
								+ "神田雑貨店 フリマシステム\n"
								+ "--------------------------------------------------";

						SendMail sendMail = new SendMail();
						sendMail.sendMail(to, subject, body);
					}
				} catch (Exception e) {
					// メール送信エラーが発生してもステータス更新自体は完了させるため、ログのみ出力
					System.out.println("購入者への通知メール送信に失敗しました：" + e.getMessage());
				}
			}
			// -----------------------------------------------------------

			// エラーがない場合、詳細画面へフォワード
			request.setAttribute("item", itemObj);
			response.sendRedirect(request.getContextPath() + "/myItemsDetail?itemID=" + itemId);
		} catch (Exception e) {
			error = "DB接続エラーの為、ステータスの更新は出来ません。";
			cmd = "logout";
			System.out.print(e.getMessage());
			e.printStackTrace();
		} finally {
			// エラーがある場合、error.jspへフォワード
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}
		}
	}
}