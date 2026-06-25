<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.Inquiry"%>
<%@page import="bean.User"%>
<%
Inquiry inquiry = (Inquiry) request.getAttribute("inquiry");
%>
<html>
<head>
<title>お問い合わせ詳細</title>
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
					<input type="submit" value="管理者メニュー" class="header-btn">
				</form>
				<form action="<%=request.getContextPath()%>/admin/inquiries"
					method="get" style="display: inline;">
					<input type="submit" value="お問い合わせ一覧" class="header-btn">
				</form>
			</div>
			<h2 class="title" style="text-align: center;">お問い合わせ詳細</h2>
		</div>
		<hr class="head_foot_hr">

		<table align="center" border="1"
			style="margin-top: 20px; border-collapse: collapse;">
			<tr>
				<th width="150"
					style="background-color: #e0e0e0; text-align: right; padding: 5px;">ニックネーム：</th>
				<td width="350" style="padding: 5px;"><%=inquiry.getNickname()%></td>
			</tr>
			<tr>
				<th
					style="background-color: #e0e0e0; text-align: right; padding: 5px;">件名：</th>
				<td style="padding: 5px;"><%=inquiry.getSubject()%></td>
			</tr>
			<tr>
				<th
					style="background-color: #e0e0e0; text-align: right; vertical-align: top; padding: 5px;">内容：</th>
				<td style="padding: 5px; height: 100px; vertical-align: top;">
					<%=inquiry.getComments().replace("\n", "<br>")%>
				</td>
			</tr>
		</table>

		<h3 style="text-align: center; margin-top: 30px;">管理者からの返信</h3>

		<%
		if (inquiry.getCompatibilityStatus() == 0) {
		%>
		<!-- 未対応(status=0)の場合は、返信入力フォームを表示 -->
		<form action="<%=request.getContextPath()%>/admin/detailInquiry"
			method="post">
			<input type="hidden" name="inquiryId"
				value="<%=inquiry.getInquiryId()%>">
			<table
				style="border-collapse: collapse; 　text-align: center; border: 1;">
				<tr>
					<th width="150"
						style="background-color: #e0e0e0; text-align: right; vertical-align: top; padding: 5px;">返信内容：</th>
					<td width="350" style="padding: 5px;"><textarea
							name="replyComment" rows="5" style="width: 100%; resize: none;"></textarea>
					</td>
				</tr>
			</table>
			<div style="text-align: center; margin-top: 15px;">
				<input type="submit" value="返信を送信する"
					onclick="return confirm('この内容で返信を登録しますか？');">
			</div>
		</form>
		<%
		} else {
		%>
		<!-- 対応済み(status=1)の場合は、登録されている返信内容を表示 -->
		<table
			style="border-collapse: collapse; 　text-align: center; border: 1;">
			<tr>
				<th width="150"
					style="background-color: #e0e0e0; text-align: right; vertical-align: top; padding: 5px;">返信日時：</th>
				<td width="350" style="padding: 5px;"><%=inquiry.getUpdatedDateTime() != null ? inquiry.getUpdatedDateTime().toString().substring(0, 19) : "日時データなし"%></td>
			</tr>
			<tr>
				<th
					style="background-color: #e0e0e0; text-align: right; vertical-align: top; padding: 5px;">返信内容：</th>
				<td style="padding: 5px; height: 80px; vertical-align: top;"><%=inquiry.getAdministratorReply() != null ? inquiry.getAdministratorReply().replace("\n", "<br>") : ""%>
				</td>
			</tr>
		</table>
		<p style="text-align: center; font-weight: bold; margin-top: 10px;">このお問い合わせは対応済みです。</p>
		<%
		}
		%>

	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>