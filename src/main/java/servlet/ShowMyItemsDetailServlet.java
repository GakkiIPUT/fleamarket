<<<<<<< HEAD
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Item;
import dao.ItemDAO;

/**
 * Servlet implementation class ShowMyItemsDetailServlet
 */
@WebServlet("/showMyItemsDetail")
public class ShowMyItemsDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//エラーメッセージを表示するための変数
		String error = null;
		//遷移先を指定するための変数
		String cmd = null;

		try {
			//jspから受け取った出品物IDをもとに詳細情報を取得
			request.setCharacterEncoding("UTF-8");
			int itemID = Integer.parseInt(request.getParameter("itemID"));
			ItemDAO itemDaoObj = new ItemDAO();
			Item itemObj = itemDaoObj.selectByItem(itemID);
			
			//詳細情報をリクエストスコープに登録
			request.setAttribute("item", itemObj);

		} catch (IllegalStateException e) {
			throw new IllegalStateException("DBエラー", e);

		} finally {
			//エラーがある場合、error.jspへフォワード
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);

				//エラーがない場合、buyConfirm.jspへフォワード
			} else {
				request.getRequestDispatcher("/view/showMyItemsDetail.jsp").forward(request, response);
			}
		}
	}

}
=======
package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ShowMyItemsDetailServlet
 */
@WebServlet("/showMyItemsDetailServlet")
public class ShowMyItemsDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowMyItemsDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
>>>>>>> cfa5003995151f296674392fc5f257d8c0a496be
