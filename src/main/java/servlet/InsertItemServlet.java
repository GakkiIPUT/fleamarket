/*
 * プロジェクト名：フリマアプリG組チーム3
 * プログラム名：InsertItemServlet.java
 * プログラムの説明：商品の登録処理を制御するサーブレットクラス。
 * 作成日：2026年6月19日
 * 作成者：林佑実
 * 更新者：髙垣|画像処理の追加
 */
package servlet;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import bean.Item;
import dao.ItemDAO;

//追加：ファイルアップロードを受け付けるための設定（最大5MB）
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
@WebServlet("/insertItem")
public class InsertItemServlet extends HttpServlet {

	// GETリクエスト（直接URLアクセスなど）が来た場合はdoPostに流す
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 商品情報の登録処理を実行し、トップ画面へフォワードします。
	 *
	 * @param request HTTPリクエスト
	 * @param response HTTPレスポンス
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//入力データ取得
		request.setCharacterEncoding("UTF-8");

		//「確認画面へ進む」か「登録」か「編集」かの判定
		String action = request.getParameter("action");
		HttpSession session = request.getSession();

		// ① JSPから画像を含むデータが送られてきた時（確認画面へ進む前）
		if ("confirm".equals(action)) {
			String type = request.getParameter("type");
			String item = request.getParameter("item");
			String strquantity = request.getParameter("quantity");
			String strprice = request.getParameter("price");
			String description = request.getParameter("description");

			int quantity = 0;
			if (strquantity != null && !strquantity.isEmpty())
				quantity = Integer.parseInt(strquantity);
			int price = 0;
			if (strprice != null && !strprice.isEmpty())
				price = Integer.parseInt(strprice);
			
			Part part = request.getPart("image");
			String filename = part.getSubmittedFileName();

			if (filename != null && !filename.isEmpty()) {
				// フォルダのパスを取得
				String path = getServletContext().getRealPath("/image");
				File uploadDir = new File(path);
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				// --- 画像のアップロード処理 ---
				
				// ファイル名から拡張子の位置（最後のドット）を探す
				int dotIndex = filename.lastIndexOf(".");
				String nameWithoutExt = (dotIndex != -1) ? filename.substring(0, dotIndex) : filename; // 拡張子なしの名前
				String ext = (dotIndex != -1) ? filename.substring(dotIndex) : ""; // 拡張子（.jpg など）

				// 現在の日時を「yyyyMMdd_HHmmss」の形式で取得 (例: 20260624_153000)
				String datetime = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

				// 新しいファイル名を組み立てる (例: sample_20260624153000.jpg)
				filename = nameWithoutExt + "" + datetime + ext;
				part.write(path + File.separator + filename);
			} else {
				// 画像が選択されなかった場合のデフォルト画像
				filename = "no_image.jpg";
			}

			// Itemオブジェクトに商品データを登録
			Item itemObj = new Item();
			itemObj.setType(type);
			itemObj.setItem(item);
			itemObj.setQuantity(quantity);
			itemObj.setPrice(price);
			itemObj.setDescription(description);
			itemObj.setImage(filename); // 保存したファイル名をセット

			// セッションに保存して確認画面へフォワード
			session.setAttribute("item", itemObj);
			request.getRequestDispatcher("/view/insertItemConfirm.jsp").forward(request, response);

		}
		// ② 確認画面から「編集」ボタンが押された時
		else if ("update".equals(action)) {
			//insertItem.jspにフォワードする（セッションのitemObjをそのまま使う）
			request.getRequestDispatcher("/view/insertItem.jsp").forward(request, response);

		}
		// ③ 確認画面から「登録」ボタンが押された時
		else if ("insert".equals(action)) {

			Item itemObj = (Item) session.getAttribute("item");

			if (itemObj != null) {

				bean.User userObj = (bean.User) session.getAttribute("user");
				if (userObj != null) {
					itemObj.setSellerId(userObj.getUserId());
				}

				//item_infoテーブルへ商品情報を登録
				ItemDAO itemDaoObj = new ItemDAO();
				itemDaoObj.insert(itemObj);

				//セッション上のItemオブジェクトを削除
				session.removeAttribute("item");
			}

			//商品一覧サーブレットにフォワード
			request.getRequestDispatcher("/list").forward(request, response);
		}
	}
}