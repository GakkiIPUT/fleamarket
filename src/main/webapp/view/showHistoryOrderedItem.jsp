<%--
 プロジェクト名：フリマシステム
 プログラム名：showHistoryOrderedItem.jsp
 プログラムの説明：購入履歴の一覧表示画面。
 作成日：2026年6月3日
 作成者：大瀬莉晏
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Item"%>
<%@page import="bean.User"%>
<%--<%
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、購入履歴画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}--%>

<%
ArrayList<Item> list = (ArrayList<Item>) request.getAttribute("order_list");
%>

<html>
<head>
<title>購入履歴</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ(商品一覧)" class="header-btn">
			</form>
		</div>
		<form action="/detail" method="POST">
			<table align="center" width="800">
				<tr>
					<td width="80" align="center"></td>
					<td width="640" align="center"><h2 class="title">購入履歴</h2></td>
					<td width="80" align="center"></td>
				</tr>
			</table>
			<hr class="head_foot_hr">

			<table align="center">
				<tr>
					<td>
						<%--@ include file="/common/userInfo.jsp" --%>
					</td>
				</tr>
			</table>

			<table align="center" width="600" style="margin-top: 20px;">
				<tr>
					<td class="header-color" width="250" align="center">商品名</td>
					<td class="header-color" width="100" align="center">数量</td>
					<td class="header-color" width="150" align="center">価格</td>
					<td class="header-color" width="150" align="center">詳細</td>

				</tr>
				<%
				if (list != null && list.size() > 0) {
					for (Item item : list) {
				%>
				<tr>
					<td align="center"><%=item.getItem()%></td>
					<td align="center"><%=item.getQuantity()%></td>
					<td align="center"><%=item.getPrice()%></td>
					<td><a
						href="<%=request.getContextPath()%>/detail?itemId=<%=item.getItemId()%>">詳細を見る</a></td>
				</tr>

				<%
				}
				} else {
				%>
				<tr>
					<td colspan="3" align="center">購入履歴がありません。</td>
				</tr>
				<%
				}
				%>
			</table>


		</form>
	</main>
	<%@ include file="/common/footer.jsp"%>
</body>
</html>