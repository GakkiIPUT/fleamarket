<!--
 ・プログラム名：G組チーム3フリマシステム（updateItem.jsp）
 ・プログラムの説明：商品情報更新フォームの表示を行う。

 ・作成者：林 佑実
 ・作成日：2026年6月23日
-->

<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="dao.ItemDAO, bean.Item" %>

<%
//商品IDを取得する
String strItemId = request.getParameter("itemId");
int itemId = Integer.parseInt(strItemId);

//商品IDに合致する商品情報を取得する
ItemDAO itemDaoObj = new ItemDAO();
Item itemObj = itemDaoObj.selectByMyItem(itemId);

%>

<html>
	<head>
		<title>商品情報更新</title>
	</head>
	<body>
		<div style="text-align:center">
			<h1>フリマシステム</h1>
			<h2>商品情報更新</h2>
			<br>
			<br>
			<form action="<%= request.getContextPath() %>/updateItem" method="get">
				<table style="margin:0 auto">
					<!-- 列名表示 -->
					<tr>
						<td style="width:100"></td>
						<td style="width:200; text-align:center">&lt;&lt;変更前情報&gt;&gt;</td>
						<td style="width:200; text-align:center">&lt;&lt;変更後情報&gt;&gt;</td>
					</tr>
					<!-- 行タイトル、変更前情報、変更情報入力フォームを表示 -->
					<tr>
						<td style="background-color:6666ff; width:100">商品画像</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getImage() %></td>
						<td ><input type="text" name="image" value="<%= itemObj.getImage() %>"></td>
					</tr>
					<tr>
						<td style="background-color:6666ff; width:100">商品名</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getItem() %></td>
						<td ><input type="text" name="item" value="<%= itemObj.getItem() %>"></td>
					</tr>
					<tr>
						<td style="background-color:6666ff; width:100">種類</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getType() %></td>
						<td ><input type="text" name="type" value="<%= itemObj.getType() %>"></td>
					</tr>
					<tr>
						<td style="background-color:6666ff; width:100">個数</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getQuantity() %></td>
						<td ><input type="text" name="quantity" value="<%= itemObj.getQuantity() %>"></td>
					</tr>
					<tr>
						<td style="background-color:6666ff; width:100">価格</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getPrice() %></td>
						<td ><input type="text" name="price" value="<%= itemObj.getPrice() %>"></td>
					</tr>
					<tr>
						<td style="background-color:6666ff; width:100">備考欄</td>
						<td style="background-color:00ffff; width:300"><%= itemObj.getDescription() %></td>
						<td ><input type="text" name="description" value="<%= itemObj.getDescription() %>"></td>
					</tr>
				</table>
				<br>
				<br>
				<input type="hidden" name="itemId" value="<%= itemObj.getItemId() %>">
				<input type="submit" value="更新">
			</form>
		</div>
	</body>
</html>