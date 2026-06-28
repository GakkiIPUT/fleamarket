<%--
 プロジェクト名：フリマシステム
 プログラム名：showSales.jsp
 プログラムの説明：売上の一覧表示画面。
 作成日：2026年6月22日
 作成者：栗原紫苑
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.*"%>


<%
ArrayList<Sales> list = (ArrayList<Sales>) request.getAttribute("salesList");

String image = null;
%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">

<html>
<head>
<meta charset="UTF-8">
<title>売上確認</title>
</head>
<body style="text-align: center">
<%@include file="/common/header.jsp"%>
	<header>
	<div class="header-left">
		<form action="<%=request.getContextPath()%>/list" method="get" style="display: inline;">
            <input type="submit" value="トップページ" class="header-btn">
        </form>
	</div>
		<h2 class="title">売上一覧</h2>
		<hr class="head_foot_hr">





		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ" class="header-btn">
			</form>
			
		</div>
	</header>

	<main>
	<%--後で治す --%>
	<div>
	<br>
	<br>
	<br>
	<br>
		<table style="border-collapse: collapse; width: 100%;" border="1">
			<%
			if (list != null && list.size() > 0) {
				for (Sales item : list) {
			%>
			<tr>
				<td>画像</td>
				<td>商品ID</td>
				<td>商品名</td>
				<td>値段</td>
				<td>システム手数料</td>
				<td>売上日</td>
			</tr>
			<tr>
				<%
				image = item.getImage();
				%>
				<td align="center"><img src=<%=image%>></td>
				<td align="center"><%=item.getItemId()%></td>
				<td align="center"><%=item.getItem()%></td>
				<td align="center"><%=item.getPrice()%></td>
				<td align="center"><%=item.getCommission()%></td>
				<td align="center"><%=item.getBuyDateTime()%></td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="6" align="center">売上履歴はありません。</td>
			</tr>
			<%
			}
			%>
		</table>
		<%@ include file="/common/footer.jsp"%>
		</div>
	</main>
</body>

</html>