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
<<<<<<< HEAD
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

=======
List<User> userList = (List<User>) request.getAttribute("user_list");
String cmd = (String) request.getAttribute("cmd");
User user = (User) session.getAttribute("user");
%>
<html>
<head>
<title>ユーザー一覧/出品者一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>

		<div class="header-left">
			<form action="<%=request.getContextPath()%>/view/adminMenu.jsp"
	            method="get" style="display: inline;">
	            <input type="submit" value="メニュー画面" class="header-btn">
	      	</form>
	      
	     </div>

			<%
			if ("allUser".equals(cmd)) {
			%>
			<div class="nav-header-title">
				<h2 class="title">ユーザー一覧</h2>
			</div>
			<%
			}
			%>


			<%
			if ("allSeller".equals(cmd)) {
			%>
			<div class="nav-header-title">
				<h2 class="title">出品者一覧</h2>
			</div>
			<%
			}
			%>

	     <hr class="head_foot_hr">

		<form action="<%=request.getContextPath()%>/listUser" method="get"
			class="form-inline">
			<table align="center" style="margin-top: 15px;">
				<tr>
					<td>ユーザー <input type="text" name="searchUserid" size="20"></td>
					<td><input type="submit" value="検索"></td>
					<td>
						<form action="<%=request.getContextPath()%>/listUser" method="get"
							style="display: inline; margin: 0; padding: 0;">
							<input type="submit" value="全件表示">
						</form>
					</td>
				</tr>
			</table>
		</form>

		<table align="center" class="form-table-80" style="margin-top: 20px;">

			<tr>
				<%
				if ("allUser".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="150">ユーザー</th>
				<%
				}
				%>
				<%
				if ("allSeller".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="150">出品者</th>
				<%
				}
				%>

				<th class="header-color" align="center" width="150">名前</th>

				<th class="header-color" align="center" width="150">ニックネーム</th>

				<th class="header-color" align="center" width="150">メールアドレス</th>

				<%
				if ("allUser".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="100">退会</th>
				<%
				}
				%>
			</tr>
			<%
			if (userList != null && userList.size() > 0) {
				for (int i = 0; i < userList.size(); i++) {
			%>
			<%
			user = new User();
			user = (User) userList.get(i);
			%>
			<tr>
				<%--ユーザーリストのi番目？ --%>
				<td align="center"><a
					href="<%=request.getContextPath()%>/detailUser?targetUser=<%=user.getUserId()%>&cmd=detailUser"><%=user.getUserId()%></a></td>



				<td align="center"><%=user.getLastName()%> <%=user.getFirstName()%></td>


				<td align="center"><%=user.getNickname()%></td>

				<td align="center"><%=user.getMail()%></td>

				<%
				if ("allUser".equals(cmd)) {
				%>
				<td align="center"><a
					href="<%=request.getContextPath()%>/deleteUser?targetUser=<%=user.getUserId()%>">退会</a>
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="4" align="center">該当するユーザーが存在しません。</td>
			</tr>
			<%
			}
			%>
		</table>
	</main><%@ include file="/common/footer.jsp"%>
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
</body>
</html>