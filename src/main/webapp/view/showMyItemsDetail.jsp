<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、ユーザーの出品物の詳細を表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.*, dao.*"%>
<<<<<<< HEAD
=======
<%

%>
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
<<<<<<< HEAD

Integer itemIdAttr = (Integer) request.getAttribute("itemId");
int itemId = (itemIdAttr != null) ? itemIdAttr : Integer.parseInt(request.getParameter("itemId"));
=======
%>
<%
int itemId = Integer.parseInt(request.getParameter("itemId"));
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
ItemDAO itemDao = new ItemDAO();
Item itemObj = itemDao.selectByItem(itemId);

// ★追加：画像名の取得とデフォルト設定（detail.jspと同じ処理）
String imgName = itemObj.getImage();
if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
	imgName = "no_image.jpg";
}

// 取引ステータスを変数に保持して見やすくする
int tStatus = itemObj.getTransactionStatus();
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
<<<<<<< HEAD
				<input type="submit" value="出品物一覧" class="header-btn">
			</form>
=======
				<input type="submit" value=出品物一覧 class="header-btn">
			</form>


>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
		</div>
		<h2 class="title" style="text-align: center;">出品物詳細</h2>

		<hr class="head_foot_hr">
		<table style="margin: auto; border: 0px; border-spacing: 4px;">
			<tbody>
				<tr>
<<<<<<< HEAD
					<%-- ★修正：登録された商品画像を表示するように変更 --%>
					<td><img
						src="<%=request.getContextPath()%>/image/<%=imgName%>" width="150"
						height="150" alt="商品の写真"></td>
					<td>
						<%-- ★修正：「出品中(0)」かつ「出品者本人」の時だけ変更・取消リンクを表示する --%> <%
 if (itemObj.getListStatus() == 0 && user.getUserId() == itemObj.getSellerId()) {
 %>
						<a
						href="<%=request.getContextPath()%>/view/updateItem.jsp?itemId=<%=itemObj.getItemId()%>">出品物情報変更</a><br>
					<br> <a
						href="<%=request.getContextPath()%>/deleteItem?itemId=<%=itemObj.getItemId()%>">取消</a>
						<%
						}
						%>
					</td>
=======
					<td><img src="../img/img1.png" alt="商品の写真"></td>
					<td><a
						href="<%=request.getContextPath()%>/view/updateItem.jsp?itemId=<%=itemObj.getItemId()%>">
							出品物情報変更</a> <a
						href="<%=request.getContextPath()%>/deleteItem?itemId=<%=itemObj.getItemId()%>">
							取消</a></td>
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
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
<<<<<<< HEAD
					<td><%=itemObj.getPrice()%>円</td>
=======
					<td><%=itemObj.getPrice()%></td>
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
				</tr>

				<tr>
					<td>取引状況：</td>
					<td style="font-weight: bold; color: #d9534f;">
<<<<<<< HEAD
						<%-- ★修正：TransactionStatus の全パターンに対応 --%> <%
 if (tStatus == 0) {
 %>未取引<%
 }
 %>
						<%
						if (tStatus == 1) {
						%>未入金<%
						}
						%> <%
 if (tStatus == 2) {
 %>支払い済み<%
 }
 %>
						<%
						if (tStatus == 3) {
						%>未発送（発送準備中）<%
						}
						%> <%
 if (tStatus == 4) {
 %>取引完了（発送済み）<%
 }
 %>
					</td>
=======
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
>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
				</tr>

				<%-- ========================================================= --%>
				<%-- ▼▼ ステータス更新ボタンエリア（取引中のみ表示） ▼▼ --%>
				<%-- ========================================================= --%>

				<%-- ①【未入金 (1)】の場合：購入者に入金ボタンを表示 --%>
				<%
				if (tStatus == 1) {
				%>
				<%
				if (user.getUserId() == itemObj.getBuyerId()) {
				%>
				<tr>
					<td colspan="2" style="padding-top: 15px;">
						<form action="<%=request.getContextPath()%>/updateStatus"
							method="post">
							<input type="hidden" name="itemId"
								value="<%=itemObj.getItemId()%>">
							<%-- 入金報告後は「支払い済み(2)」または「未発送(3)」に進める --%>
							<input type="hidden" name="transactionStatus" value="3">
							<button type="submit"
								style="padding: 10px 20px; background-color: #28a745; color: white; border: none; cursor: pointer;">
								入金完了を報告する</button>
						</form>
					</td>
				</tr>
				<%
				} else if (user.getUserId() == itemObj.getSellerId()) {
				%>
				<tr>
					<td colspan="2" style="padding-top: 15px; color: #f0ad4e;">
						購入者の入金をお待ちください。入金されると通知が届きます。</td>
				</tr>
				<%
				}
				%>
				<%
				}
				%>

				<%-- ②【支払い済み (2) または 未発送 (3)】の場合：出品者に発送ボタンを表示 --%>
				<%
				if (tStatus == 2 || tStatus == 3) {
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
								name="transactionStatus" value="4">
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

				<%-- ========================================================= --%>

				<tr>
					<td style="padding-top: 20px;">備考欄：</td>
					<td style="padding-top: 20px;"><%=itemObj.getDescription()%></td>
				</tr>
			</tbody>
		</table>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>