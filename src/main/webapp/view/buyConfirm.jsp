<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、購入情報を確認表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.*, dao.*"%>

<%
//リクエストスコープから購入物情報を取得
Item itemObj = (Item) request.getAttribute("item");
//商品ID
int itemId = itemObj.getItemId();
//商品名
String item = itemObj.getItem();
//値段
int price = itemObj.getPrice();
//個数
int quantity = itemObj.getQuantity();

//画像名を取得
String imgName = itemObj.getImage();
// もし画像名が null または空ならデフォルトに設定
if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
	imgName = "no_image.jpg";
}
//購入者情報
User userObj = (User) session.getAttribute("user");
//ユーザー姓
String lastName = userObj.getLastName();
//ユーザー名
String firstName = userObj.getFirstName();
//郵便番号
String postCode = userObj.getPostCode();
//都道府県
String prefectures = userObj.getPrefectures();
//市区町村
String city = userObj.getCity();
//番地
String streetAddress = userObj.getStreetAddress();
//建物名・部屋番号
String buildingRoom = userObj.getBuildingRoom();

//支払い方法
int paymentMethod = itemObj.getPayment();
%>

<html>
<head>
<meta charset="UTF-8">
<title>購入完了</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body style="text-align: center;">
	<%@include file="/common/header.jsp"%>


	<header>
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ" class="header-btn">
			</form>
		</div>

		<h2 class="title">購入完了</h2>
		<hr class="head_foot_hr">


	</header>
	<main>
		<div style="display: flex; justify-content: center; align-items: center; width: 100%; margin-bottom: 20px;">
			<img src="<%=request.getContextPath()%>/image/<%=imgName%>" width="200" height="200">
		</div>

		<table style="margin: 0 auto;">
			<tr>
				<td>商品ID：</td>
				<td><%=itemId%></td>
			</tr>
			<tr>
				<td>商品名：</td>
				<td><%=item%></td>
			</tr>
			<tr>
				<td>個数：</td>
				<td><%=quantity%></td>
			</tr>
			<tr>
				<th>お届け先：</th>
				<td><%=postCode%><br> <%=prefectures%> <%=city%><%=streetAddress%><br>
					<%=buildingRoom%><br> <%=lastName%> <%=firstName%>様</td>
			</tr>
			<tr>
				<td>お支払方法：</td>
				<%
				if (paymentMethod == 0) {
				%>
				<td>クレジットカード</td>
				<%
				} else if (paymentMethod == 1) {
				%>
				<td>電子マネー</td>
				<%
				} else {
				%>
				<td>後払い</td>
				<%}%>

			</tr>
		</table>
		<h5>購入が完了しました。</h5>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>