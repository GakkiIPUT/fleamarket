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
import jakarta.servlet.http.HttpSession;

import bean.User;
import dao.UserDAO;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String path = "/listUser";
		String error = "";
		String cmd = "";

		try {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				error = "セッション切れの為、ユーザー詳細画面が表示できませんでした。";
				cmd = "logout";
				path = "/view/error.jsp";
				return;
			}
			
			int userid = Integer.parseInt(request.getParameter("targetUser"));
			UserDAO userDao = new UserDAO();
			
			//会員なら退会した後にリストに
			if(user.getAuthorityFlag()==0) {
				userDao.delete(userid);
<<<<<<< HEAD
			 path = "/list";
			  if (session != null) {
	                // セッション情報を完全に破棄
	                session.invalidate();
	            }
=======
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
				
				return;
			}
			//管理者ならユーザー一覧
			if(user.getAuthorityFlag()==1) {
				
				userDao.delete(userid);
<<<<<<< HEAD
			   path = "/listUser";
=======
				
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
				return;
			}
			

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
			request.setAttribute("cmd", cmd);
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}