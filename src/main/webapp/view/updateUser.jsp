<!--
 ・プログラム名：G組チーム3フリマシステム（updateUser.jsp）
 ・プログラムの説明：ユーザー情報更新フォームの表示を行う。

 ・作成者：林 佑実
 ・作成日：2026年6月22日
-->

<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="bean.User, dao.UserDAO, java.sql.Timestamp" %>

<%
//更新するユーザーの情報を格納したUserオブジェクトを取得する
String strUserId = request.getParameter("user");
int userId = Integer.parseInt(strUserId);
UserDAO userDaoObj = new UserDAO();
User userObj = userDaoObj.selectById(userId);

//更新日時取得
Timestamp timestamp = new Timestamp(System.currentTimeMillis());

%>

<html>
	<head>
		<meta charset="UTF-8">
		<title>ユーザー情報更新</title>
	</head>
	<body style="text-align: center">
	<jsp:include page="/common/header.jsp" />
	<header>
	<div class = "header-left">
		<form action = "<%= request.getContextPath() %>/list" method="get" style="display: inline;">
			<input type="submit" value="トップページ" class="header-btn">
		</form>
		<form action = "<%= request.getContextPath() %>/detailUser" method="get" style="display: inline;">
			<input type="submit" value="ユーザー詳細情報" class="header-btn">
		</form>
	</div>
		<h2 class="title">ユーザー情報更新</h2>
		<hr class="head_foot_hr">
	</header>
	<main>
			<form action="<%= request.getContextPath() %>/updateUser" method="get">
				<table style="margin:0 auto">
					<!-- 列名表示 -->
					<tr>
						<td style="width:100"></td>
						<td style="width:200; text-align:center">&lt;&lt;変更前情報&gt;&gt;</td>
						<td style="width:200; text-align:center">&lt;&lt;変更後情報&gt;&gt;</td>
					</tr>
					<!-- 行タイトル、変更前情報、変更情報入力フォームを表示 -->
					<tr>
						<td style= "width:200">ニックネーム</td>
						<td style= "width:200"><%= userObj.getNickname() %></td>
						<td ><input type="text" name="nickname" value="<%= userObj.getNickname() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">姓</td>
						<td style= "width:200"><%= userObj.getLastName() %></td>
						<td ><input type="text" name="lastName" value="<%= userObj.getLastName() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">姓カナ</td>
						<td style= "width:200"><%= userObj.getLastNameRubi() %></td>
						<td ><input type="text" name="lastNameRubi" value="<%= userObj.getLastNameRubi()%>"></td>
					</tr>
					<tr>
						<td style= "width:200">名</td>
						<td style= "width:200"><%= userObj.getFirstName() %></td>
						<td ><input type="text" name="firstName" value="<%= userObj.getFirstName() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">名カナ</td>
						<td style= "width:200"><%= userObj.getFirstNameRubi() %></td>
						<td ><input type="text" name="firstNameRubi" value="<%= userObj.getFirstNameRubi() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">郵便番号</td>
						<td style= "width:200"><%= userObj.getPostCode() %></td>
						<td ><input type="text" name="postCode" value="<%= userObj.getPostCode() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">都道府県</td>
						<td style= "width:200"><%= userObj.getPrefectures() %></td>
						<td ><input type="text" name="prefectures" value="<%= userObj.getPrefectures() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">市区町村</td>
						<td style= "width:200"><%= userObj.getCity() %></td>
						<td ><input type="text" name="city" value="<%= userObj.getCity() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">番地</td>
						<td style= "width:200"><%= userObj.getStreetAddress() %></td>
						<td ><input type="text" name="streetAddress" value="<%= userObj.getStreetAddress() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">建物名・号室</td>
						<td style= "width:200"><%= userObj.getBuildingRoom() %></td>
						<td ><input type="text" name="buildingRoom" value="<%= userObj.getBuildingRoom() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">電話番号</td>
						<td style= "width:200"><%= userObj.getTelephoneNumber() %></td>
						<td ><input type="text" name="telephoneNumber" value="<%= userObj.getTelephoneNumber() %>"></td>
					</tr>
					<tr>
						<td style= "width:200">メールアドレス</td>
						<td style= "width:200"><%= userObj.getMail() %></td>
						<td ><input type="text" name="mail" value="<%= userObj.getMail() %>"></td>
					</tr>
				</table>
				<br>
				<br>
				<input type="submit" value="更新">
			</form>
		</main>
	<%@ include file="../common/footer.jsp"%>
	</body>
</html>