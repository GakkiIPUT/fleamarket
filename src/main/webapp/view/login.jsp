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
// ★追加：クッキーから過去のユーザーIDとパスワードを取得
String cookieUser = "";
String cookiePass = "";
Cookie[] cookies = request.getCookies();
if (cookies != null) {
	for (Cookie c : cookies) {
		if ("user".equals(c.getName()))
	cookieUser = c.getValue();
		if ("password".equals(c.getName()))
	cookiePass = c.getValue();
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
	<%@ include file="/common/header.jsp"%><main>

		<h2 align="center">ログイン</h2>
		<hr class="head_foot_hr">


		<form action="<%=request.getContextPath()%>/login" method="post">
			<table align="center">
				<tr>
					<th class="header-color">ユーザー</th>
					<td><input type="text" name="user" class="border-gray"
						value="<%=cookieUser%>"></td>
				</tr>
				<tr>
					<th class="header-color">パスワード</th>
					<td><input type="password" name="password" class="border-gray"
						value="<%=cookiePass%>"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="ログイン"></td>
				</tr>
			</table>
		</form>

	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>