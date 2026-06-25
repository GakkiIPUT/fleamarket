<%--
 プロジェクト名：フリマシステム
 プログラム名：detail.jsp
 プログラムの説明：商品詳細の表示画面。
 作成日：2026年6月22日
 作成者：大瀬莉晏
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="bean.Item"%>
<%@ page import="java.util.List"%>
<%@ page import="bean.Comment"%>
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
	<header>
		<div class="header-left">
			<form action="<%=request.getContextPath()%>/list" method="get" style="display: inline;">
	            <input type="submit" value="トップページ" class="header-btn">
	        </form>
		</div>
		<h2 class="title">商品詳細情報</h2>
		<hr class="head_foot_hr">
		</header>
	
		<main>
		<div style="text-align: center; margin: 20px;">
			<form action="<%=request.getContextPath()%>/view/buy.jsp" method="get"
				class="form-inline">
				<input type="hidden" name="itemId" value="<%=item.getItemId() %>">
				
				<table align="center" class="form-table-30">
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/image/<%=imgName%>"
							width="200" height="200"></td>
						<td></td>
						<td><input type="submit" value="購入"
							style="width: 150px; height: 50px;"></td>
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
		<%-- ▼▼ チャット形式のコメント表示エリア ▼▼ --%>
		<div
			style="width: 80%; margin: 0 auto; background-color: #f0f8ff; padding: 20px; border-radius: 10px;">
			<%
			// Servletから渡されたコメント一覧を取得
			List<Comment> commentList = (List<Comment>) request.getAttribute("commentList");

			if (commentList != null && !commentList.isEmpty()) {
				for (Comment c : commentList) {
					// 出品者（商品の管理者）を「左」、購入希望の人たちを「右」に配置
					boolean isSeller = (c.getSellerFlag() == 1);
					String align = isSeller ? "left" : "right";

					// 吹き出しの色：左側(出品者)を白、右側(購入希望者)を薄い緑に設定
					String bgColor = isSeller ? "#ffffff" : "#ccffcc";
			%>
			<div style="text-align: <%=align%>; margin-bottom: 15px;">
				<span style="font-size: 0.9em; color: #555;"> <%=c.getNickname()%>
					<%=isSeller ? "<span style='color: red; font-weight: bold;'>(出品者)</span>" : ""%>
				</span><br>
				<div
					style="display: inline-block; background-color: <%=bgColor%>; padding: 10px; border-radius: 8px; border: 1px solid #ccc; max-width: 70%; text-align: left;">
					<%=c.getComment().replace("\n", "<br>")%>
				</div>
				<br> <span style="font-size: 0.8em; color: #999;"><%=c.getCreateDateTime()%></span>
			</div>
			<%
			}
			} else {
			%>
			<p align="center">コメントはまだありません。質問してみましょう！</p>
			<%
			}
			%>
		</div>

		<br>

		<%-- ▼▼ コメント投稿フォームエリア ▼▼ --%>
		<div align="center">
			<%
			// セッションからログインユーザー情報を取得
			User loginUser = (User) session.getAttribute("user");

			if (loginUser != null) {
				int sellerFlag = (loginUser.getUserId() == item.getSellerId()) ? 1 : 0;
			%>
			<form action="<%=request.getContextPath()%>/insertComment"
				method="post">
				<textarea name="comment" rows="4" cols="60" required
					placeholder="ここにコメントを入力してください..."></textarea>
				<br> <input type="hidden" name="itemId"
					value="<%=item.getItemId()%>"> <input type="hidden"
					name="sellerFlag" value="<%=sellerFlag%>"> <input
					type="submit" value="コメントを送信する"
					style="margin-top: 10px; padding: 5px 20px;">
			</form>
			<%
			} else {
			%>
			<p style="color: red;">
				コメントを投稿するには<a href="<%=request.getContextPath()%>/view/login.jsp">ログイン</a>してください。
			</p>
			<%
			}
			%>
		</div>
	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>