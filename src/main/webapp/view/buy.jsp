<%@page contentType="text/html; charset=UTF-8"%>
<<<<<<< HEAD
<%@page import="bean.*, dao.*"%>

<%
//出品物情報をdetail.jspから受け取ったIDをもとに詳細情報を取得
//商品ID
int itemId = Integer.parseInt(request.getParameter("itemId"));
ItemDAO itemDaoObj = new ItemDAO();
Item itemObj = itemDaoObj.selectByItem(itemId);

=======
<%@page import ="bean.*, dao.*"%>

<%
//テスト用





//出品物情報
Item itemObj = (Item)request.getAttribute("item");
//商品ID
int itemId = itemObj.getItemId();
>>>>>>> cfa5003995151f296674392fc5f257d8c0a496be
//商品名
String item = itemObj.getItem();
//個数
int quantity = itemObj.getQuantity();
//値段
int price = itemObj.getPrice();

//購入者情報
<<<<<<< HEAD

//★テスト用
//UserDAO userDaoObj = new UserDAO();
//User userObj = userDaoObj.selectByUser("taro.yamada@example.com","abcd" );

//★本番用:
User userObj = (User) session.getAttribute("user");

if (userObj == null) {
	// ログインしていない場合はエラー画面へ飛ばすか、ログイン画面へリダイレクト
	request.setAttribute("error", "セッション切れ、または未ログインのため購入手続きができません。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
=======
User userObj = (User)session.getAttribute("user");
>>>>>>> cfa5003995151f296674392fc5f257d8c0a496be
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
<<<<<<< HEAD
=======

>>>>>>> cfa5003995151f296674392fc5f257d8c0a496be
%>

<html>
<head>
<meta charset="UTF-8">
<title>購入手続き画面</title>
</head>
<<<<<<< HEAD
<body style="text-align: center;">
	<%@include file="/common/header.jsp"%>
	<main>
		<h1>フリマシステム</h1>
		<a href="<%=request.getContextPath()%>/list">トップページ</a> <a
			href="<%=request.getContextPath()%>/view/myPage.jsp">マイページ</a> <a
			href="<%=request.getContextPath()%>/detail">商品詳細</a>
		<h3 style="color: rgb(255, 128, 128);">購入手続き</h3>

		<img src="../image/no_image.jpg" alt="商品の写真">
		<p></p>
		<p></p>

		<form action="<%=request.getContextPath()%>/buyConfirm" method="get">
			<input type="hidden" name="itemId" value="<%=itemId%>"> <input
				type="hidden" name="price" value="<%=price%>">
			<table style="margin: 0 auto">
				<tr>
					<th>商品ID：</th>
					<td><%=itemId%></td>
				</tr>
				<tr>
					<th>商品名：</th>
					<td><%=item%></td>
				</tr>
				<tr>
					<th>個数：</th>
					<td><%=quantity%></td>
				</tr>
				<tr>
					<th>値段：</th>
					<td><%=price%>円</td>
				</tr>
				<tr>
					<th>お届け先：</th>
					<td><%=postCode%><br> <%=prefectures%><%=city%><%=streetAddress%><br>
						<%=buildingRoom%><br> <%=lastName%> <%=firstName%>様</td>
				</tr>
				<tr>
					<th>お支払方法：</th>
					<td><input type="radio" name="paymentMethod" value="0">クレジットカード
						<input type="radio" name="paymentMethod" value="1">電子マネー <input
						type="radio" name="paymentMethod" value="2">後払い</td>
				</tr>

			</table>
			<div style="text-align: center">
				<input type="submit" value="購入を確定する">
			</div>
		</form>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>
=======
<body style="text-align:center;">
	<%@include file= "/common/header.jsp" %>
	<main>
	<h1>フリマシステム</h1>
	<a href="<%=request.getContextPath()%>/list">トップページ</a>
	<a href="<%=request.getContextPath()%>/view/myPage.jsp">マイページ</a>
	<a href="<%=request.getContextPath()%>/detail">商品詳細</a>
	<h3 style="color:rgb(255, 128, 128);">購入手続き</h3>

	<img src="../image/no_image.jpg" alt="商品の写真">
	<p></p>
	<p></p>
	
	<form action="<%=request.getContextPath()%>/buyConfirm" method="get">	
		<table style="margin:0 auto">
			<tr>
				<th>商品ID：</th>
				<td><%=itemId %></td>
			</tr>
			<tr>
				<th>商品名：</th>
				<td><%=item %></td>
			</tr>
			<tr>
				<th>個数：</th>
				<td><%=quantity %></td>
			</tr>
			<tr>
				<th>値段：</th>
				<td><%=price %>円</td>
			</tr>
			<tr>
				<th>お届け先：</th>
				<td>
					<%=postCode %><br>
					<%=prefectures %><%=city %><%=streetAddress %><br>
					<%=buildingRoom %><br>
					<%=lastName %> <%=firstName %>様
				</td>
			</tr>
			<tr>
				<th>お支払方法：</th>
				<td><input type="radio" name="paymentMethod" 
					value="0">クレジットカード
					<input type="radio" name="paymentMethod" 
					value="1">電子マネー
					<input type="radio" name="paymentMethod" 
					value="2">後払い
				</td>
			</tr>
			
		</table>
		<div style="text-align:center">
			<input type="hidden" name="price" value="<%=price %>"> 
			<input type="submit" value="確定">
		</div>
	</form>
	</main>
	<%@include file= "/common/footer.jsp" %>
</body>
</html>
>>>>>>> cfa5003995151f296674392fc5f257d8c0a496be
