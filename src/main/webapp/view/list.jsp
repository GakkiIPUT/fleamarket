<%--
 プロジェクト名：フリマシステム
 プログラム名：list.jsp
 プログラムの説明：商品一覧画面。
 作成日：2026年6月19日
 作成者：大瀬莉晏
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.ArrayList"%>
<%@ page import="bean.Item"%>

<%
ArrayList<Item> list = (ArrayList<Item>) request.getAttribute("item_list");
%>
<!DOCTYPE html>

<html>

<head>

<title>フリマ</title>
</head>

<body style="background-color: rgb(255, 255, 255); text-align: center;">
	<h1 style="color: rgb(255, 128, 128);">フリマシステム</h1>

	<table style="margin: auto; width: 850px">
		<tr>
			<td style="text-align: center; width: 80px">[<a
				href="<%=request.getContextPath()%>/view/myPage.jsp">マイページ</a>]
			</td>
			<td style="text-align: center; width: 80px">[<a
				href="<%=request.getContextPath()%>/view/insertItemConfirm.jsp">出品</a>]
			</td>
			<td style="text-align: center; width: 80px">[<a
				href="<%=request.getContextPath()%>/view/login.jsp">ログイン</a>]
			</td>
		</tr>
	</table>


	<h3 style="color: rgb(255, 128, 128);">商品一覧</h3>
	<table style="margin: auto; border: 0px; border-spacing: 4px;">
		<form action="<%=request.getContextPath()%>/search" method="get"
			class="form-inline">
			商品名:<input type="text" name="item" class="input-text-border-gray">
			種類:<input type="text" name="type" class="input-text-border-gray">

			<input type="submit" value="検索">
		</form>

		<%
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {%>
				<%Item item = (Item) list.get(i);
				//画像名を取得
				String imgName = item.getImage();
				// もし画像名が null または空ならデフォルトに設定
				if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
			imgName = "no_image.jpg";
				}
		%>
		<tr>
			<td>
				<%--画像を表示：imageフォルダ内のファイルを参照--%> <img
				src="<%=request.getContextPath()%>/image/<%=imgName%>" width="50"
				height="50">
				
			</td>
			</tr>
			<tr>
			<td style="text-align: center; width: 200px"><a
				href="<%=request.getContextPath()%>/detail?itemId=<%=item.getItemId()%>&cmd=detail"><%=item.getImage()%></a></td>
			</tr>
			<tr>
			<td><a
				href="<%=request.getContextPath()%>/detail?itemId=<%=item.getItemId()%>&cmd=detail"></a><%=item.getItem()%></td>
			 
			<tr>
			
			
				
	<%}} %>
				

	</table>
	<hr>

</body>

</html>