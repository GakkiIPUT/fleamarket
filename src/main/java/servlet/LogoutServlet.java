/*
 * プロジェクト名：フリマシステム
 * プログラム名：LogoutServlet.java
 * プログラムの説明：セッション情報を無効化し、安全にログアウトしてログイン画面へ遷移する。
 * 作成日：2026年6月22日
 * 作成者：中田佳葉
*/

package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログアウト処理を制御するサーブレットクラスです。
 * セッションを破棄し、ログイン画面（login.jsp）へフォワードします。
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 制御用変数の初期化
        String path = "/view/login.jsp";
        String error = null;
        String cmd = "logout";
        try {
            // セッションの取得（既存のセッションがなければnullを返す）
            HttpSession session = request.getSession(false);
            
            if (session != null) {
                // セッション情報を完全に破棄
                session.invalidate();
            }
            
            // 画面に表示するメッセージをリクエストスコープに設定
            request.setAttribute("message", "ログアウトしました。");

        } catch (Exception e) {
            error = "ログアウト処理中に予期せぬエラーが発生しました。";
			error = "予期せぬエラーが発生しました。" + e.getMessage();
            path = "/view/error.jsp";
        }finally {
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("cmd", cmd);
            }
            request.getRequestDispatcher(path).forward(request, response);
        }
    }
}