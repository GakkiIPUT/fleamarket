<!-- 商品画像もテキスト入力になっています。（6/19 林） 
更新者：髙垣|画像処理の追加
-->
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="bean.Item"%>
<%
//変数宣言
String type;
String item;
int quantity;
String image;
int price;
String description;

//セッションから入力情報を取得
Item itemObj = (Item) session.getAttribute("item");

if (itemObj == null) { //セッションに入力情報がない場合
	type = "";
	item = "";
	quantity = 0;
	image = "";
	price = 0;
	description = "";

} else {
	type = itemObj.getType();
	item = itemObj.getItem();
	quantity = itemObj.getQuantity();
	image = itemObj.getImage();
	price = itemObj.getPrice();
	description = itemObj.getDescription();
}
%>
<html>
<head>
<title>商品登録</title>
</head>
<body style="text-align: center">
<%@include file="/common/header.jsp"%>
	<header>
	<div class="header-left">
		<form action="<%=request.getContextPath()%>/list" method="get" style="display: inline;">
            <input type="submit" value="トップページ" class="header-btn">
        </form>
	</div>
		<h2 class="title">出品物登録</h2>
		<hr class="head_foot_hr">

	</header>
	<main>
	<form action="<%=request.getContextPath()%>/insertItem" method="POST"
		enctype="multipart/form-data">
		<input type="hidden" name="action" value="confirm">
		<table style="margin: 0 auto">
			<tr>
				<td>種類</td>
				<td><input type="text" name="type" value=<%=type%>></td>
			</tr>
			<tr>
				<td>商品名</td>
				<td><input type="text" name="item" value=<%=item%>></td>
			</tr>
			<tr>
				<td>個数</td>
				<td><input type="text" name="quantity" value=<%=quantity%>></td>
			</tr>
			<tr>
				<td>商品画像</td>
				<td><input type="file" name="image" accept="image/*"></td>
			</tr>
			<tr>
				<td>価格</td>
				<td><input type="text" name="price" value=<%=price%>></td>
			</tr>
			<tr>
				<td>説明欄</td>
				<td><input type="text" name="description"
					value=<%=description%>></td>
			</tr>
		</table>
		<br> <br> <input type="submit" value="確認">
	</form>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>