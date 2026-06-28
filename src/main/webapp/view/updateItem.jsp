<!--
 ・プログラム名：G組チーム3フリマシステム（updateItem.jsp）
 ・プログラムの説明：商品情報更新フォームの表示を行う。

 ・作成者：林 佑実
 ・作成日：2026年6月23日
-->

<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="dao.ItemDAO, bean.Item"%>

<%
//画面の遷移元を取得
String cmd = request.getParameter("cmd");

//商品IDを取得する
String strItemId = request.getParameter("itemId");
int itemId = Integer.parseInt(strItemId);

//商品IDに合致する商品情報を取得する
ItemDAO itemDaoObj = new ItemDAO();
Item itemObj = itemDaoObj.selectByMyItem(itemId);

//画像名を取得
String imgName = itemObj.getImage();
//もし画像名が null または空ならデフォルトに設定
if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
	imgName = "no_image.jpg";
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品情報更新</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body style="text-align: center;">
	<%@include file="/common/header.jsp"%>
	<main>
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get"
				style="display: inline;">
				<input type="submit" value="トップページ" class="header-btn">
			</form>
			<%
			if ("list".equals(cmd)) {
			%>
			<form action="<%=request.getContextPath()%>/myItems" method="get"
				style="display: inline;">
				<input type="submit" value=出品物一覧 class="header-btn">
			</form>
			<%
			} else if ("detail".equals(cmd)) {
			%>
			<form action="<%=request.getContextPath()%>/myItemsDetail"
				method="get" style="display: inline;">
				<input type="hidden" name="itemID" value=<%=itemObj.getItemId()%>>
				<input type="submit" value=出品物詳細 class="header-btn">
			</form>
			<%
			}
			%>
		</div>
		<h2 class="title" style="text-align: center;">商品情報更新</h2>
		<hr class="head_foot_hr">
		<div>
			<br> <br>
			<form action="<%=request.getContextPath()%>/updateItem" method="post"
				enctype="multipart/form-data">
				<!-- 行タイトル、変更前情報、変更情報入力フォームを表示 -->

				<table style="margin: 0 auto">
					<!-- 列名表示 -->

					<tr>
						<td style="width: 100"></td>
						<td style="width: 200; text-align: center">&lt;&lt;変更前情報&gt;&gt;</td>
						<td style="width: 200; text-align: center">&lt;&lt;変更後情報&gt;&gt;</td>
					</tr>
					<tr>
						<td style="background-color: #f2f2f2; padding: 10px;">商品画像</td>
						<td style="padding: 10px;"><%=itemObj.getImage()%></td>
						<td style="padding: 10px;"><input type="file" name="image"
							accept="image/*"> <input type="hidden" name="oldImage"
							value="<%=itemObj.getImage()%>"></td>
					</tr>
					<tr>
						<td style="width: 100">商品名</td>
						<td style="width: 200"><%=itemObj.getItem()%></td>
						<td><input type="text" name="item" size="30"
							value="<%=itemObj.getItem()%>"></td>
					</tr>
					<tr>
						<td style="width: 100">種類</td>
						<td style="width: 200"><%=itemObj.getType()%></td>
						<td><input type="text" name="type" size="30"
							value="<%=itemObj.getType()%>"></td>
					</tr>
					<tr>
						<td style="width: 100">個数</td>
						<td style="width: 200"><%=itemObj.getQuantity()%></td>
						<td><input type="text" name="quantity" size="30"
							value="<%=itemObj.getQuantity()%>"></td>
					</tr>

					<tr>
						<td style="width: 100">価格</td>
						<td style="width: 200"><%=itemObj.getPrice()%></td>
						<td><input type="text" name="price" size="30"
							value="<%=itemObj.getPrice()%>"></td>
					</tr>
					<tr>
						<td style="width: 100">備考欄</td>
						<td style="width: 200"><%=itemObj.getDescription()%></td>
						<td><input type="text" name="description" size="30"
							value="<%=itemObj.getDescription()%>"></td>
					</tr>
				</table>
				<br> <br> <input type="hidden" name="itemId"
					value="<%=itemObj.getItemId()%>"> <input type="submit"
					value="更新">
			</form>
		</div>
	</main>
	<%@include file="/common/footer.jsp"%>
</body>
</html>