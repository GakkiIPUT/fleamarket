<%--
プログラム名：フリマシステム
プログラムの説明：フリマシステムの、ユーザー自身の出品物を一覧表示するプログラムです。
作成者：田中杏佳
作成日：2026/06/22
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import ="java.util.*, bean.*"%>

<%
//出品物情報取得
ArrayList<Item> itemList = (ArrayList<Item>)request.getAttribute("item_list");
%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>フリマ</title>
</head>

<body style="text-align:center;">
	<h1>フリマシステム</h1>

	<h3>出品物一覧</h3>
	<table style="margin:auto; border:0px; border-spacing:4px;">
		<tr>
			<th>
				商品ID
			</th>
			<th>
				商品名
			</th>
			<th>
				編集
			</th>
			<th>
				取消
			</th>
		</tr>
		<%
		if(itemList != null){
			for(int i = 0; i < itemList.size(); i++){
		%>
		<tr>
			<%-- 商品ID --%>
			<td>
				<a href="<%=request.getContextPath()%>/showMyItemsDetai?itemId=<%=itemList.get(i).getItemId() %>"></a>
			</td>
			
			<%-- 商品名 --%>
			<td>
				<%=itemList.get(i).getItem() %>
			</td>
			
			<%-- 編集 --%>
			<td>
				<a href="<%=request.getContextPath()%>/updateItem">編集</a>
				<form action="<%=request.getContextPath()%>/updateItem">
				<input type="hidden" name="itemId" value="<%=itemList.get(i).getItemId() %>">
				</form>
			</td>
			
			<%-- 取消 --%>
			<td>
				<a href="<%=request.getContextPath()%>/deleteItem?itemId=<%=itemList.get(i).getItemId() %>">取消</a>
			</td>
		</tr>
		<%
			}
		}
		%>
	</table>
	<hr>
</body>
</html>