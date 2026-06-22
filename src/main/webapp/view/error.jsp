<%--
 プロジェクト名：書籍管理システムWeb版Ver3.0
 プログラム名：error.jsp
 プログラムの説明：エラー内容の表示画面。
 作成日：2026年6月3日
 作成者：髙垣湧侑翔
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
String error = (String) request.getAttribute("error");
String cmd = (String) request.getAttribute("cmd");
%>
<!DOCTYPE html>
<html>
<head>
<title>エラー</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>
		<h2 align="center" class="error-title">●●エラー●●</h2>
		<br>
		<p align="center" class="error-message"><%=error%></p>
		<br>

		<p align="center">
			<%
			if ("menu".equals(cmd)) {
			%>
			<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニューに戻る]</a>
			<%
			} else if ("logout".equals(cmd)) {
			%>
			<a href="<%=request.getContextPath()%>/logout">[ログイン画面へ]</a>
			<%
			} else if ("listUser".equals(cmd)) {
			%>
			<a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧に戻る]</a>
			<%
			} else {
			%>
			<a href="<%=request.getContextPath()%>/list">[一覧表示に戻る]</a>
			<%
			}
			%>
		</p>

	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>