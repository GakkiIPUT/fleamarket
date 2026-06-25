<%--
 プロジェクト名：フリマシステム
 プログラム名：listUser.jsp
 プログラムの説明：ユーザー一覧の表示画面。
 作成日：2026年6月23日
 作成者：大瀬莉晏
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="bean.User"%>


<%
List<User> userList = (List<User>) request.getAttribute("user_list");
String cmd = (String) request.getAttribute("cmd");
User user = (User) session.getAttribute("user");
%>
<html>
<head>
<title>ユーザー一覧/出品者一覧</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<main>

		<div class="header-left">
			<form action="<%=request.getContextPath()%>/view/adminMenu.jsp"
	            method="get" style="display: inline;">
	            <input type="submit" value="メニュー画面" class="header-btn">
	      	</form>
	      
	     </div>

			<%
			if ("allUser".equals(cmd)) {
			%>
			<div class="nav-header-title">
				<h2 class="title">ユーザー一覧</h2>
			</div>
			<%
			}
			%>


			<%
			if ("allSeller".equals(cmd)) {
			%>
			<div class="nav-header-title">
				<h2 class="title">出品者一覧</h2>
			</div>
			<%
			}
			%>

	     <hr class="head_foot_hr">

		<form action="<%=request.getContextPath()%>/listUser" method="get"
			class="form-inline">
			<table align="center" style="margin-top: 15px;">
				<tr>
					<td>ユーザー <input type="text" name="searchUserid" size="20"></td>
					<td><input type="submit" value="検索"></td>
					<td>
						<form action="<%=request.getContextPath()%>/listUser" method="get"
							style="display: inline; margin: 0; padding: 0;">
							<input type="submit" value="全件表示">
						</form>
					</td>
				</tr>
			</table>
		</form>

		<table align="center" class="form-table-80" style="margin-top: 20px;">

			<tr>
				<%
				if ("allUser".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="150">ユーザー</th>
				<%
				}
				%>
				<%
				if ("allSeller".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="150">出品者</th>
				<%
				}
				%>

				<th class="header-color" align="center" width="150">名前</th>

				<th class="header-color" align="center" width="150">ニックネーム</th>

				<th class="header-color" align="center" width="150">メールアドレス</th>

				<%
				if ("allUser".equals(cmd)) {
				%>
				<th class="header-color" align="center" width="100">退会</th>
				<%
				}
				%>
			</tr>
			<%
			if (userList != null && userList.size() > 0) {
				for (int i = 0; i < userList.size(); i++) {
			%>
			<%
			user = new User();
			user = (User) userList.get(i);
			%>
			<tr>
				<%--ユーザーリストのi番目？ --%>
				<td align="center"><a
					href="<%=request.getContextPath()%>/detailUser?targetUser=<%=user.getUserId()%>&cmd=detailUser"><%=user.getUserId()%></a></td>



				<td align="center"><%=user.getLastName()%> <%=user.getFirstName()%></td>


				<td align="center"><%=user.getNickname()%></td>

				<td align="center"><%=user.getMail()%></td>

				<%
				if ("allUser".equals(cmd)) {
				%>
				<td align="center"><a
					href="<%=request.getContextPath()%>/deleteUser?targetUser=<%=user.getUserId()%>">退会</a>
				</td>
				<%
				}
				%>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="4" align="center">該当するユーザーが存在しません。</td>
			</tr>
			<%
			}
			%>
		</table>
	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>