<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%

//サーブレットからエラーで突き返された際の入力値を取得
String subjectVal = (String) request.getAttribute("subjectVal");
String commentsVal = (String) request.getAttribute("commentsVal");

//サーブレットから渡されたエラーメッセージの取得
String commentsErr = (String) request.getAttribute("commentsErr");
String subjectErr = (String) request.getAttribute("subjectErr");

//nullチェックと初期値

commentsVal = (commentsVal == null) ? "" : commentsVal;
subjectVal = (subjectVal == null) ? "" : subjectVal;
%>
<html>
<head>
<title>お問い合わせ入力</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
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
			<h2 class="title">お問い合わせ</h2>
		</div>
		<hr class="head_foot_hr">

		<div
			style="text-align: center; font-size: 16px; font-weight: bold; margin-bottom: 20px;">
		</div>

 		<form action="<%=request.getContextPath()%>/inquiry" method="post">
			<input type="hidden" name="action" value="confirm">
				<table class="inner-table">
					<tr>
						<th>お問い合わせの件名：<%
						if (subjectErr != null) {
						%><span class="error-msg"><%=subjectErr%></span><%
						} else {
						%><span class="req-msg">※必須 30文字以内</span><%
						}
						%>
						</th>
						<td><input type="text" name="subject" style="<%=subjectErr != null ? "background-color:#ffcccc;" : ""%>" value="<%=subjectVal%>"></td>
					</tr>
					<tr>
						<th>お問い合わせ内容：<%
						if (commentsErr != null) {
						%><span class="error-msg"><%=commentsErr%></span><%
						} else {
						%><span class="req-msg">※必須 1000文字以内</span><%
						}
						%>
						</th>
						<td><textarea name="comments" rows="16" cols="45" style="<%=commentsErr != null ? "background-color:#ffcccc;" : ""%>"><%=commentsVal%></textarea></td>
					</tr>
				</table>

			<div align="center" style="margin-top: 30px; margin-bottom: 20px;">
				<input type="submit" value="確認" class="header-btn"
					style="font-size: 16px; padding: 5px 30px;">
			</div>
		</form>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>