<!-- 更新者：髙垣|画像処理の追加 -->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="bean.Item"%>
<%
// セッションから入力情報を取得
Item itemObj = (Item) session.getAttribute("item");
%>
<html>
<head>
<title>商品登録</title>
</head>
<body style="text-align: center">
	<h1>フリマシステム</h1>
	<h2>出品物登録</h2>
	<form action="<%=request.getContextPath()%>/insertItem" method="POST">
		<table style="margin: 0 auto">
			<tr>
				<td>種類：</td>
				<td><%=request.getParameter("type")%></td>
			</tr>
			<tr>
				<td>商品名：</td>
				<td><%=request.getParameter("item")%></td>
			</tr>
			<tr>
				<td>商品画像：</td>
				<td><img
					src="<%=request.getContextPath()%>/image/<%=itemObj.getImage()%>"
					width="200"></td>
			</tr>
			<tr>
				<td>価格：</td>
				<td><%=request.getParameter("price")%></td>
			</tr>
			<tr>
				<td>説明欄：</td>
				<td><%=request.getParameter("description")%></td>
			</tr>
		</table>
		<br> <br>
		<table style="margin: 0 auto">
			<tr>
				<td><button type="submit" name="action" value="update">編集</button></td>
				<td><button type="submit" name="action" value="insert">登録</button></td>
			</tr>
		</table>
		<br> <input type="hidden" name="type"
			value="<%=request.getParameter("type")%>"> <input
			type="hidden" name="item" value="<%=request.getParameter("item")%>">
		<input type="hidden" name="image"
			value="<%=request.getParameter("image")%>"> <input
			type="hidden" name="price"
			value="<%=request.getParameter("price")%>"> <input
			type="hidden" name="description"
			value="<%=request.getParameter("description")%>">
	</form>
</body>
</html>