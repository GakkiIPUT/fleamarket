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
		String path = "/view/insertItem.jsp";
		String error = null;
		boolean forwarded = false;

		try {
			// ① JSPから画像を含むデータが送られてきた時（確認画面へ進む前）
			if ("confirm".equals(action)) {
				String type = request.getParameter("type");
				String item = request.getParameter("item");
				String strquantity = request.getParameter("quantity");
				String strprice = request.getParameter("price");
				String description = request.getParameter("description");

				if (type == null || type.trim().isEmpty() || item == null || item.trim().isEmpty()
						|| description == null || description.trim().isEmpty()) {
					error = "カテゴリ・商品名・説明は必須です。";
				} else if (strquantity == null || strquantity.trim().isEmpty()) {
					error = "数量を入力してください。";
				} else if (strprice == null || strprice.trim().isEmpty()) {
					error = "価格を入力してください。";
				} else {
					int quantity;
					try {
						quantity = Integer.parseInt(strquantity);
					} catch (NumberFormatException e) {
						error = "数量は半角数字で入力してください。";
						quantity = 0;
					}

					if (error == null) {
						int price;
						try {
							price = Integer.parseInt(strprice);
						} catch (NumberFormatException e) {
							error = "価格は半角数字で入力してください。";
							price = 0;
						}

						if (error == null) {
							Part part = request.getPart("image");
							String filename = "";
							if (part != null) {
								filename = part.getSubmittedFileName();
							}

							if (filename != null && !filename.isEmpty()) {
								// フォルダのパスを取得
								String uploadPath = getServletContext().getRealPath("/image");
								File uploadDir = new File(uploadPath);
								if (!uploadDir.exists()) {
									uploadDir.mkdirs();
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
								part.write(uploadPath + File.separator + filename);
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
							path = "/view/insertItemConfirm.jsp";
							request.getRequestDispatcher(path).forward(request, response);
							forwarded = true;
							return;
						}
					}
				}
			}
			// ② 確認画面から「編集」ボタンが押された時
			else if ("update".equals(action)) {
				//insertItem.jspにフォワードする（セッションのitemObjをそのまま使う）
				path = "/view/insertItem.jsp";
				request.getRequestDispatcher(path).forward(request, response);
				forwarded = true;
				return;
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
				path = "/list";
				request.getRequestDispatcher(path).forward(request, response);
				forwarded = true;
				return;
			}
		} catch (IllegalStateException e) {
			error = "ファイルサイズが上限を超えています。";
			path = "/view/insertItem.jsp";
		} catch (NumberFormatException e) {
			error = "数量または価格の形式が不正です。";
			path = "/view/insertItem.jsp";
		} catch (Exception e) {
			error = "商品の登録処理中にエラーが発生しました。";
			path = "/view/insertItem.jsp";
			e.printStackTrace();
		} finally {
			if (!forwarded) {
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher(path).forward(request, response);
			}
		}
	}
}
