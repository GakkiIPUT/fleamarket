<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bean.Inquiry"%>
<%@page import="bean.User"%>
<%
//User user = (User) session.getAttribute("user");
//if (user == null || !"2".equals(user.getAuthority())) {
//	request.setAttribute("error", "管理者権限がない為、アクセスできません。");
//	request.setAttribute("cmd", "menu");
//	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
//	return;
//}
// Servletからリストを受け取る
ArrayList<Inquiry> list = (ArrayList<Inquiry>) request.getAttribute("inquiry_list");
%>

<html>
<head>
<title>お問い合わせ一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="header-left">

			<form action="<%=request.getContextPath()%>/view/adminMenu.jsp"
            method="get" style="display: inline;">
            <input type="submit" value="メニュー画面" class="header-btn">
      			</form>

		</div>
			<h2 class="title" style="text-align: center;">お問い合わせ一覧</h2>
		<hr class="head_foot_hr">

		<table style = "text-align:center;">
			<tr>
				<td><%@ include file="../common/userInfo.jsp"%></td>
			</tr>
		</table>

		<%
		if (list == null || list.isEmpty()) {
		%>
		<p style="text-align: center; font-weight: bold; margin-top: 20px;">現在、お問い合わせはありません。</p>
		<%
		} else {
		%>
		<!-- style属性の重複を解消し、枠線(border)の指定を綺麗に直しました -->
		<table border="1" style="margin-top: 20px; margin-left: auto; margin-right: auto; text-align: center; border-collapse: collapse;">
			<tr style="background-color: #e0e0e0;">
				<th width="50">No.</th>
				<th width="150">日付</th>
				<th width="200">件名</th>
				<th width="150">名前</th>
				<th width="150">未読/既読</th>
				<th width="150">対応</th>
			</tr>
			<%
			for (Inquiry inquiry : list) {
			%>
			<tr>
				<td><%=inquiry.getInquiryId()%></td>
				<td><%=inquiry.getCreateDateTime().toString().substring(0, 19)%></td>
				<td><a
					href="<%=request.getContextPath()%>/admin/detailInquiry?id=<%=inquiry.getInquiryId()%>">
						<%=inquiry.getSubject()%>
				</a></td>
				<td><%=inquiry.getNickname()%></td>
				<% 
				if (inquiry.isReadingFlag()){
				%>
					<td>既読</td>
				<%
				} else{%>
					<td>未読</td>
				<%} 
				int CompatibilityStatus = inquiry.getCompatibilityStatus();
				if (CompatibilityStatus == 1){
				%>
					<td>対応済</td>
				<%
				} else{%>
					<td>未対応</td>
				<%} %>
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