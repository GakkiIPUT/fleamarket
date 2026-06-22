<%--
 プロジェクト名：フリマシステム
 プログラム名：detail.jsp
 プログラムの説明：商品詳細の表示画面。
 作成日：2026年6月22日
 作成者：大瀬莉晏
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="bean.Item"%>
<%@page import="bean.User"%>
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
Item item = (Item) request.getAttribute("item");

//画像名を取得
String imgName = item.getImage();
// もし画像名が null または空ならデフォルトに設定
if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
	imgName = "no_image.jpg";
}
%>
<!DOCTYPE html>
<html>
<head>
<title>商品詳細情報</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body style="background-color: rgb(255, 255, 255); text-align: center;">
	<%@ include file="/common/header.jsp"%>
	<main>

		<div class="nav-header">
			<div class="nav-header-links">
				<table>
					<tr>
						<td style="text-align: center; width: 80px">[<a
							href="<%=request.getContextPath()%>/view/myPage.jsp">マイページ</a>]
						</td>
						<td style="text-align: center; width: 80px">[<a
							href="<%=request.getContextPath()%>/list">トップページ</a>]
						</td>
					</tr>
				</table>
			</div>
			<div class="nav-header-title">
				<h2 class="title">商品詳細情報</h2>
			</div>
		</div>
		<hr class="head_foot_hr">
		<br>
		<div style="text-align: center; margin: 20px;">
		<form action="<%=request.getContextPath()%>/buy" method="get"
			class="form-inline">
		<table align="center" class="form-table-30">
		<tr>
		<td>
			<img src="<%=request.getContextPath()%>/image/<%=imgName%>"
				width="200" height="200">
				</td>
				<td>
				</td>
				<td><input type="submit" value="購入" style="width: 150px; height: 50px;"></td>
				</tr>
				</table>
				</form>
		</div>
		<table align="center" class="form-table-30">

			<tr>
				<td><br></td>
				<td><br></td>

			</tr>

			<tr>
				<td class="header-color">商品名：</td>
				<td class="column-color"><%=item.getItem()%></td>
			</tr>
			<tr>
				<td class="header-color">種類：</td>
				<td class="column-color"><%=item.getType()%></td>
			</tr>
			<tr>
				<td class="header-color">個数：</td>
				<td class="column-color"><%=item.getQuantity()%></td>
			</tr>
			<tr>
				<td class="header-color">価格：</td>
				<td class="column-color"><%=item.getPrice()%>円</td>
			</tr>
			<tr>
				<td class="header-color">備考欄：</td>
				<td class="column-color"><%=item.getDescription()%></td>
			</tr>



		</table>
		<%--コメント機能 --%>
		<table align="center" class="form-table-30">
			<tr>
				<td style="color: green; font-size: 20px">コメント</td>

			</tr>
		</table>
		<input type="text" name="SendMessage"
			style="width: 500px; height: 500px; font-size: 16px;">
	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>