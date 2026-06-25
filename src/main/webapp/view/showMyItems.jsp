<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、ユーザー自身の出品物を一覧表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*, bean.*"%>

<%
//出品物情報取得
ArrayList<Item> itemList = (ArrayList<Item>) request.getAttribute("item_list");
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
<<<<<<< HEAD
=======
		<h1>フリマシステム</h1>

>>>>>>> f86b75ac123787f4448dacaef066ef5040f7814c
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ" class="header-btn">
			</form>


		</div>
		<h2 class="title" style="text-align: center;">出品物一覧</h2>

		<hr class="head_foot_hr">
		<table style="margin: auto; border: 0px; border-spacing: 4px;">
			<tr>
				<th>商品名</th>
				<th>編集</th>
				<th>取消</th>
			</tr>
			<%
			if (itemList != null) {
				for (int i = 0; i < itemList.size(); i++) {
			%>
			<tr>
				<%-- 商品名 --%>
				<td><a
					href="<%=request.getContextPath()%>/myItemsDetail?itemId=<%=itemList.get(i).getItemId()%>"><%=itemList.get(i).getItem()%></a>
				</td>

				<%-- 編集 --%>
				<td><a
					href="<%=request.getContextPath()%>/view/updateItem.jsp?itemId=<%=itemList.get(i).getItemId()%>">編集</a>
				</td>

				<%-- 取消 --%>
				<td><a
					href="<%=request.getContextPath()%>/deleteItem?itemId=<%=itemList.get(i).getItemId()%>">取消</a>
				</td>
			</tr>
			<%
			}
			}
			%>
		</table>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>