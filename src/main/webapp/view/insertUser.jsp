<%--
 プロジェクト名：フリマシステム
 プログラム名：insertUser.jsp
 プログラムの説明：ユーザー登録画面。
 作成日：2026年6月22日
 作成者：中田佳葉
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// 仕様書通りのセッションチェック記述例
//User user = (User) session.getAttribute("user");
//if (user == null) {
//request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
//request.setAttribute("cmd", "logout");
//request.getRequestDispatcher("/view/error.jsp").forward(request, response);
//return;
//}
%>
<html>
<head>
<title>ユーザー登録</title>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="nav-header-links" style="text-align: center;">

				<h2 class="title" style="text-align: center;">ユーザー登録</h2>
			</div>
			<hr class="head_foot_hr">

			<form action="<%=request.getContextPath()%>/insertUser" method="post">
				<input type="hidden" name="authorityFlag" value="0">
				<table align="center">
					<tr>
						<th class="header-color">姓</th>
						<td><input type="TEXT" name="lastName"></td>
					</tr>
					<tr>
						<th class="header-color">名</th>
						<td><input type="TEXT" name="firstName"></td>
					</tr>
					<tr>
						<th class="header-color">姓カナ</th>
						<td><input type="TEXT" name="lastNameRubi"></td>
					</tr>
					<tr>
						<th class="header-color">名カナ</th>
						<td><input type="TEXT" name="firstNameRubi"></td>
					</tr>
					<tr>
						<th class="header-color">ニックネーム</th>
						<td><input type="TEXT"name="nickname"></td>
					</tr>
					<tr>
						<th class="header-color">メールアドレス</th>
						<td><input type="TEXT" name="mail"></td>
					</tr>
					<tr>
						<th class="header-color">パスワード</th>
						<td><input type="TEXT"  name="password"></td>
					</tr>
					<tr>
						<th class="header-color">パスワード(確認用)</th>
						<td><input type="TEXT" name="passwordConfirm"></td>
					</tr>
					<tr>
						<th class="header-color">郵便番号</th>
						<td><input type="TEXT" name="postCode"></td>
					</tr>
					<tr>
						<th class="header-color">都道府県</th>
						<td><input type="TEXT" name="prefectures"></td>
					</tr>
					<tr>
						<th class="header-color">市区町村</th>
						<td><input type="TEXT"name="city"></td>
					</tr>
					<tr>
						<th class="header-color">番地</th>
						<td><input type="TEXT" name="streetAddress"></td>
					</tr>
					<tr>
						<th class="header-color">建物名・号室</th>
						<td><input type="TEXT" name="buildingRoom"></td>
					</tr>
					<tr>
						<th class="header-color">電話番号</th>
						<td><input type="TEXT" name="telephoneNumber"></td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="submit" value="登録">

				</div>
			</form>
		</div>
	</main>
	<%@ include file="../common/footer.jsp"%>

</body>
</html>