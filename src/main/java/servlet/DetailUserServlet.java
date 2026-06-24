/*
 * プロジェクト名：フリマシステム
 * プログラム名：DetailUserServlet.java
 * プログラムの説明：ユーザー詳細の表示、および変更画面への遷移を制御するクラス。
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

@WebServlet("/detailUser")
public class DetailUserServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String path = "/view/detailUser.jsp";
		String error = null;
		String cmd = "user";

		UserDAO dao = new UserDAO();
		User resultUser = null;
		String cmdParam = null;

		try {

			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				error = "セッション切れの為、ユーザー詳細画面が表示できませんでした。";
				cmd = "logout";
				path = "/view/error.jsp";
				return;
			}

			if (user.getAuthorityFlag() == 0) {
				int targetUser = Integer.parseInt(request.getParameter("user"));
				cmdParam = request.getParameter("cmd");
				resultUser = dao.selectById(targetUser);

			}
			if (user.getAuthorityFlag() == 1) {
				resultUser = dao.selectById(user.getUserId());
				cmdParam = request.getParameter("cmd");
			}

			if (resultUser.getBuildingRoom() == null) {
				resultUser.setBuildingRoom("");
			}

			if (resultUser == null || resultUser.getUserId() == 0) {
				if ("updateUser".equals(cmdParam)) {
					error = "更新対象のユーザーが存在しない為、変更画面は表示できませんでした。";
				} else {
					error = "表示対象のユーザーが存在しない為、詳細情報は表示できませんでした。";
				}
				path = "/view/error.jsp";
			} else {

				session.setAttribute("resultUser", resultUser);
				if ("updateUser".equals(cmdParam)) {
					path = "/view/updateUser.jsp";
				}
			}

		} catch (IllegalStateException e) {
			error = "DB接続エラーの為、画面表示は行えませんでした。";
			path = "/view/error.jsp";
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