<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、ユーザーの出品物の詳細を表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.*, dao.*"%>
<%

%>
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
%>
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
<body style="text-align: center;">
	<%@include file="/common/header.jsp"%>
	<main>
		<h1>フリマシステム</h1>
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ" class="header-btn">
			</form>
			<form action="<%=request.getContextPath()%>/myItems" method="get"
				style="display: inline;">
				<input type="submit" value=出品物一覧 class="header-btn">
			</form>


		</div>
		<h2 class="title" style="text-align: center;">出品物詳細</h2>

		<hr class="head_foot_hr">
		<table style="margin: auto; border: 0px; border-spacing: 4px;">
			<tbody>
				<tr>
					<td><img src="../img/img1.png" alt="商品の写真"></td>
					<td><a
						href="<%=request.getContextPath()%>/view/updateItem.jsp?itemId=<%=itemObj.getItemId()%>">
							出品物情報変更</a> <a
						href="<%=request.getContextPath()%>/deleteItem?itemId=<%=itemObj.getItemId()%>">
							取消</a></td>
				</tr>

				<tr>
					<td>商品名：</td>
					<td><%=itemObj.getItem()%></td>
				</tr>
				<tr>
					<td>種類：</td>
					<td><%=itemObj.getType()%></td>
				</tr>
				<tr>
					<td>個数：</td>
					<td><%=itemObj.getQuantity()%></td>
				</tr>
				<tr>
					<td>値段：</td>
					<td><%=itemObj.getPrice()%></td>
				</tr>
				<tr>
					<td>取引状況：</td>
					<td style="font-weight: bold; color: #d9534f;">
						<%
						if (itemObj.getListStatus() == 0) {
						%>出品中 <%
						} else if (itemObj.getListStatus() == 3) {
						%>未発送（発送準備中）
						<%
						} else if (itemObj.getListStatus() == 4) {
						%>取引完了（発送済み） <%
						}
						%>
					</td>
				</tr>

				<%
				if (itemObj.getListStatus() == 3) {
				%>
				<%
				if (user.getUserId() == itemObj.getSellerId()) {
				%>
				<tr>
					<td colspan="2" style="padding-top: 15px;">
						<form action="<%=request.getContextPath()%>/updateStatus"
							method="post">
							<input type="hidden" name="itemId"
								value="<%=itemObj.getItemId()%>"> <input type="hidden"
								name="listStatus" value="4">
							<button type="submit"
								style="padding: 10px 20px; background-color: #007bff; color: white; border: none; cursor: pointer;">
								商品の発送完了を報告する</button>
						</form>
					</td>
				</tr>
				<%
				} else if (user.getUserId() == itemObj.getBuyerId()) {
				%>
				<tr>
					<td colspan="2" style="padding-top: 15px; color: #5bc0de;">
						出品者の発送をお待ちください。発送されるとメールで通知が届きます。</td>
				</tr>
				<%
				}
				%>
				<%
				}
				%>
				<tr>
					<td>備考欄：</td>
					<td><%=itemObj.getDescription()%></td>
				</tr>
				<tr>
					<td>コメント</td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>