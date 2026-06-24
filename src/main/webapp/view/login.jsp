<%--
 プロジェクト名：フリマシステム
 プログラム名：login.jsp
 プログラムの説明：ログイン画面。
 作成日：2026年6月3日
 作成者：中田佳葉
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// クッキーから過去に保存されたメールアドレスのみを取得
String cookieEmail = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie c : cookies) {
		if ("savedEmail".equals(c.getName())) {
			cookieEmail = c.getValue();
			break;
		}
	}
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>
		<h2 align="center">ログイン</h2>
		<hr class="head_foot_hr">

		<%-- エラーメッセージの表示 --%>
		<%
		String msg = (String) request.getAttribute("message");
		%>
		<%
		if (msg != null) {
		%>
		<p align="center" style="color: red;"><%=msg%></p>
		<%
		}
		%>

		<form action="<%=request.getContextPath()%>/login" method="post">
			<table align="center">
				<tr>
					<th class="header-color">メールアドレス</th>
					<td><input type="text" name="mail" class="border-gray"
						value="<%=cookieEmail%>" required></td>
				</tr>
				<tr>
					<th class="header-color">パスワード</th>
					<td><input type="password" name="password" class="border-gray"
						required></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="ログイン"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button"
						value="新規登録"
						onclick="location.href='<%=request.getContextPath()%>/view/insertUser.jsp'">
					</td>
				</tr>
			</table>
		</form>
	</main>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>