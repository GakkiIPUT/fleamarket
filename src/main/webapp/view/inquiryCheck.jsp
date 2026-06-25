<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// 文字コード指定（フォームデータ受け取り用）
request.setCharacterEncoding("UTF-8");

//User user = (User) session.getAttribute("user");
//if (user == null) {
	//request.setAttribute("error", "セッション切れの為、画面が表示できませんでした。");
	//request.setAttribute("cmd", "logout");
	//request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	//return;
//}

//入力データはサーブレットからフォワードされてくるため、そのまま取得可能
String subject = (String) request.getAttribute("subject");
String comments = (String) request.getAttribute("comments");

// 【追加】万が一 null だった場合に備えて空文字に置き換える（エラー防止）
if (subject == null) {
	subject = "";
}
if (comments == null) {
	comments = "";
}
%>
<html>
<head>
<title>お問い合わせ確認</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
		<%@ include file="../common/header.jsp"%>
	<main>

		<div class="nav-header">
			<div class="header-left">
				<form action="<%=request.getContextPath()%>/list" method="get"
					style="display: inline;">
					<input type="submit" value="トップページ(商品一覧)" class="header-btn">
				</form>
			</div>
			
			<h2 class="title">お問い合わせ確認</h2>
		</div>
		<hr class="head_foot_hr">
		<div
			style="text-align: center; font-size: 16px; font-weight: bold; margin-bottom: 10px;">
		</div>
		<div style="text-align: center; margin-top: 10px;">
			<b>上記の内容でよろしければ、送信ボタンを押してください。</b>
		</div>

	<form action="<%=request.getContextPath()%>/inquiry" method="post">
			<input type="hidden" name="action" value="send">
			
			<input type="hidden" name="subject" value="<%=subject%>">
			<input type="hidden" name="comments" value="<%=comments%>">

			<table class="split-table-bg">
				<tr>
					<td>
						<table class="inner-table-bg">
							<tr><th>お問い合わせタイトル：</th><td><%=subject%></td></tr>
							<tr>
								<th>お問い合わせ内容：</th>
								<td><div class="disp-area"><%= comments.replace("\n", "<br>")%></div></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<div align="center" style="margin-top: 30px; margin-bottom: 20px;">
				<input type="button" value="変更" onclick="history.back()" style="font-size: 16px; padding: 5px 20px; margin-right: 20px;">
				<input type="submit" value="送信" style="font-size: 16px; padding: 5px 20px;">
			</div>
		</form>
	</main>

	<%@ include file="../common/footer.jsp"%>
</body>
</html>