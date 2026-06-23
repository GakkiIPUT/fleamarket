/* 
 * プログラム名：G組チーム3フリマシステム（UpdateUserServlet.java）
 * プログラムの説明：ユーザー情報更新処理を制御するサーブレット
 * 
 * 作成者：林 佑実
 * 作成日：2026年6月22日
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

@WebServlet("/updateUser")
public class UpdateUserServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException{		
		
		//UserDAOクラスのオブジェクトを生成する
		UserDAO userDaoObj = new UserDAO();
		
		//文字コードを設定する
		request.setCharacterEncoding("UTF-8");
		
		//更新後のユーザー情報を取得し、変数に代入する
		String nickname = request.getParameter("nickname");
		String lastName = request.getParameter("lastName");
		String firstName = request.getParameter("firstName");
		String lastNameRubi = request.getParameter("lastNameRubi");
		String firstNameRubi = request.getParameter("firstNameRubi");
		String postCode = request.getParameter("postCode");
		String prefectures = request.getParameter("prefectures");
		String city = request.getParameter("city");
		String streetAddress = request.getParameter("streetAddress");
		String buildingRoom = request.getParameter("buildingRoom");
		String telephoneNumber = request.getParameter("telephoneNumber");
		String mail = request.getParameter("mail");
		
		//入力チェック
		//エラーメッセージと遷移先を変数に代入する
		
		//セッションからUserオブジェクトを取得
		HttpSession session = request.getSession();
		User userObj = (User)session.getAttribute("user");

		//Userオブジェクトに更新する情報を格納する
		userObj.setNickname(nickname);
		userObj.setLastName(lastName);
		userObj.setFirstName(firstName);
		userObj.setLastNameRubi(lastNameRubi);
		userObj.setFirstNameRubi(firstNameRubi);
		userObj.setPostCode(postCode);
		userObj.setPrefectures(prefectures);
		userObj.setCity(city);
		userObj.setStreetAddress(streetAddress);
		userObj.setBuildingRoom(buildingRoom);
		userObj.setTelephoneNumber(telephoneNumber);
		userObj.setMail(mail);
		
		//BookDAOクラスのupdateメソッドを呼び出し、更新処理を実行する
		userDaoObj.update(userObj);

		//DetailUserServletへフォワードする
		request.getRequestDispatcher("/detailUser").forward(request, response);
	}
}