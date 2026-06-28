/* 
 * プログラム名：G組チーム3フリマシステム（UpdateItemServlet.java）
 * プログラムの説明：商品情報更新処理を制御するサーブレット
 * 
 * 作成者：林 佑実
 * 作成日：2026年6月22日
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
import jakarta.servlet.http.Part;

import bean.Item;
import dao.ItemDAO;

@WebServlet("/updateItem")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)

public class UpdateItemServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// パラメータ取得
		String strItemId = request.getParameter("itemId");
		String item = request.getParameter("item");
		String type = request.getParameter("type");
		String strQuantity = request.getParameter("quantity");
		String strPrice = request.getParameter("price");
		String description = request.getParameter("description");

		// 商品情報の取得
		int itemId = Integer.parseInt(strItemId);
		int quantity = Integer.parseInt(strQuantity);
		int price = Integer.parseInt(strPrice);

		ItemDAO itemDaoObj = new ItemDAO();
		Item itemObj = itemDaoObj.selectByMyItem(itemId);

		// --- 画像処理 ---
		Part part = request.getPart("image");
		String filename = part.getSubmittedFileName();

		if (filename != null && !filename.isEmpty()) {
			String uploadPath = getServletContext().getRealPath("/image");
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			int dotIndex = filename.lastIndexOf(".");
			String nameWithoutExt = (dotIndex != -1) ? filename.substring(0, dotIndex) : filename;
			String ext = (dotIndex != -1) ? filename.substring(dotIndex) : "";
			String datetime = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());

			filename = nameWithoutExt + "" + datetime + ext;
			part.write(uploadPath + File.separator + filename);
			itemObj.setImage(filename); // 新しい画像名をセット
		}
		// 画像が選択されない場合は、元の画像名を維持するため何もしない

		// データ更新
		itemObj.setItem(item);
		itemObj.setType(type);
		itemObj.setQuantity(quantity);
		itemObj.setPrice(price);
		itemObj.setCommission((int) (price * 0.1));
		itemObj.setProceeds(price - (int) (price * 0.1));
		itemObj.setDescription(description);

		itemDaoObj.update(itemObj);

		request.getRequestDispatcher("/myItems").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}