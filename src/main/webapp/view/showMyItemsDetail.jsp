<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、ユーザーの出品物の詳細を表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="bean.*, dao.*"%>
<%-- 
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
--%>
<%
int itemId = Integer.parseInt(request.getParameter("itemId"));
ItemDAO itemDao = new ItemDAO();
Item itemObj = itemDao.selectByItem(itemId);
%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>フリマ</title>
	<link rel="stylesheet"
		href="<%=request.getContextPath()%>/css/style.css">
</head>
<body style="text-align:center;">
	<%@include file= "/common/header.jsp" %>
	<main>
		<h1>フリマシステム</h1>
		<a href="<%=request.getContextPath()%>/list">トップページ</a>
		<a href="<%=request.getContextPath()%>/view/myPage.jsp">マイページ</a>
		<a href="<%=request.getContextPath()%>/myItems">出品物一覧</a>
		<h3>出品物詳細</h3>
		<table style="margin:auto; border:0px; border-spacing:4px;">
			<tbody>
				<tr>
					<td>
						<img src="../img/img1.png" alt="商品の写真">
					</td>
					<td>
						<a href="<%=request.getContextPath()%>/view/updateItem.jsp?itemId=<%= itemObj.getItemId()%>">
						出品物情報変更</a>
						
						<a href="<%=request.getContextPath()%>/deleteItem?itemId=<%= itemObj.getItemId()%>">
						取消</a>
					</td>
				</tr>
				
				<tr>
					<td>商品名：</td>
					<td><%= itemObj.getItem()%></td>
				</tr>
				<tr>
					<td>種類：</td>
					<td><%= itemObj.getType()%></td>
				</tr>
				<tr>
					<td>個数：</td>
					<td><%= itemObj.getQuantity()%></td>
				</tr>
				<tr>
					<td>値段：</td>
					<td><%= itemObj.getPrice()%></td>
				</tr>
				<tr>
					<td>備考欄：</td>
					<td><%= itemObj.getDescription()%></td>
				</tr>
				<tr>
					<td>コメント</td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</main>
	<%@include file= "/common/footer.jsp" %>
</body>
</html>