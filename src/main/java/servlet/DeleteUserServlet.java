/*
 * プロジェクト名：フリマシステム
 * プログラム名：DeleteUserServlet.java
 * プログラムの説明：ユーザーの退会機能。
 * 作成者：大瀬莉晏
*/
package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.UserDAO;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/listUser";
		String error = "";
		String cmd = "logout";

		try {
			int userid = Integer.parseInt(request.getParameter("user"));
			UserDAO userDao = new UserDAO();
			userDao.delete(userid);

		} catch (IllegalStateException e) {
			path = "/view/error.jsp";
			error = "DB接続エラーの為、購入は出来ません。";
			cmd = "logout";
		} catch (RuntimeException e) {
			path = "/view/error.jsp";
			error = "クエリ発行に失敗しました。";
			cmd = "logout";
		} catch (Exception e) {
			e.printStackTrace();
			error = "予期せぬエラーが発生しました。" + e.getMessage();
		} finally {
			if (error != null) {
				request.setAttribute("error", error);
				request.setAttribute("cmd", cmd);
			}
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}