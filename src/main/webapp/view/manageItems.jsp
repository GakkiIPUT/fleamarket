<%--
 プロジェクト名：フリマシステム
 プログラム名：manageItems.jsp
 プログラムの説明：出品物一覧画面。
 作成日：2026年6月24日
 作成者：栗原紫苑
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.ArrayList"%>
<%@ page import="bean.*"%>

<%
ArrayList<Item> list = (ArrayList<Item>) request.getAttribute("item_list");
%>
<html>

<head>
<title>出品物一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="header-left">
				<form action="<%=request.getContextPath()%>/view/adminMenu.jsp"
					method="get" style="display: inline;">
					<input type="submit" value="メニュー画面" class="header-btn">
				</form>
			</div>
			<h2 class="title" style="text-align: center;">出品物一覧</h2>
		</div>

		<table style="text-align: center;">
			<tr>
				<td><%@ include file="../common/userInfo.jsp"%></td>
			</tr>
		</table>
		<hr class="head_foot_hr">

		<%
		if (list == null || list.isEmpty()) {
		%>
		<p style="text-align: center; font-weight: bold; margin-top: 20px;">現在、出品物はありません。</p>
		<%
		} else {
		%>
		<table border="1"
			style="margin-top: 20px; margin-left: auto; margin-right: auto; text-align: center; border-collapse: collapse;">
			<tr style="background-color: #e0e0e0;">
				<th width="100">画像</th>
				<th width="50">No.</th>
				<th width="200">商品名</th>
				<th width="100">種類</th>
				<th width="100">価格</th>
				<th width="100">手数料</th>
				<th width="150">出品者名</th>
				<th width="150">出品状況</th>
				<th width="150">取引状況</th>
				<th width="150">登録日時</th>
				<th width="150">最終更新日時</th>
			</tr>
			<%
			for (int i = 0; i < list.size(); i++) {
				Item item = list.get(i);

				// 画像名の判定とデフォルト値の設定
				String imgName = item.getImage();
				if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
					imgName = "no_image.jpg";
				}

				// 出品ステータス(int)のテキスト変換
				String listStatusStr = "不明";
				if (item.getListStatus() == 0) {
					listStatusStr = "出品中";
				} else if (item.getListStatus() == 1) {
					listStatusStr = "完売";
				} else if (item.getListStatus() == 2) {
					listStatusStr = "公開停止";
				}

				// 取引ステータス(int)のテキスト変換
				String transStatusStr = "未取引";
				if (item.getTransactionStatus() == 1) {
					transStatusStr = "未入金";
				} else if (item.getTransactionStatus() == 2) {
					transStatusStr = "支払済み";
				} else if (item.getTransactionStatus() == 3) {
					transStatusStr = "未発送";
				} else if (item.getTransactionStatus() == 4) {
					transStatusStr = "発送済み";
				}
			%>
			<tr>
				<!-- 1. 画像 -->
				<td>
					<%
					String image = item.getImage();
					// 画像がない、またはnull文字が入っている場合のデフォルト設定
					if (image == null || image.isEmpty() || image.equals("null")) {
						image = "no_image.jpg";
					}
					%> <img src="<%=request.getContextPath()%>/image/<%=image%>"
					width="60" height="60" alt="商品画像">
				</td>
				<!-- 2. No. -->
				<td><%=item.getItemId()%></td>
				<!-- 3. 商品名 -->
				<td><%=item.getItem()%></td>
				<!-- 4. 種類 -->
				<td><%=item.getType()%></td>
				<!-- 5. 価格 -->
				<td><%=item.getPrice()%>円</td>
				<!-- 6. 手数料 -->
				<td><%=item.getCommission()%>円</td>
				<!-- 7. 出品者名 -->
				<td><%=item.getSellerNickname() != null ? item.getSellerNickname() : "なし"%></td>
				<!-- 8. 出品状況 -->
				<td><%=listStatusStr%></td>
				<!-- 9. 取引状況 -->
				<td><%=transStatusStr%></td>
				<!-- 10. 登録日時（java.sql.Dateに対応するため、そのままtoString()を出力します） -->
				<td><%=item.getCreateDateTime() != null ? item.getCreateDateTime().toString() : "-"%></td>
				<!-- 11. 最終更新日時 -->
				<td><%=item.getUpdateDateTime() != null ? item.getUpdateDateTime().toString() : "-"%></td>
			</tr>
			<%
			}
			%>
		</table>
		<%
		}
		%>

	</main>
	<%@ include file="../common/footer.jsp"%>
</body>

</html>