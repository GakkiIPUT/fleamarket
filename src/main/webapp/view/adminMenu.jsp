<%--
 プロジェクト名：フリマシステム
 プログラム名：adminMenu.jsp
 プログラムの説明：管理者メニュー画面。
 作成日：2026年6月23日
 作成者：中田佳葉
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%--
User user = (User) session.getAttribute("user");
if (user == null) {
	request.setAttribute("error", "セッション切れの為、メニュー画面が表示できませんでした。");
	request.setAttribute("cmd", "logout");
	request.getRequestDispatcher("/view/error.jsp").forward(request, response);
	return;
}
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<%@ include file="/common/header.jsp"%><main>

		<%-- ユーザー情報表示 兼 セッション切れチェック --%>
		<%@ include file="/common/userInfo.jsp"%>
		<hr class="head_foot_hr">
		<h2 align="center" class="title">管理者メニュー</h2>
		<hr class="head_foot_hr">

		<table align="center">
			<tr>
				<td align="center"><a href="<%=request.getContextPath()%>/list">【出品物一覧】</a></td>
			</tr>

			<tr>
				<td align="center"><a
					href="<%=request.getContextPath()%>/admin/sales">【売上確認】</a></td>
			</tr>
			<tr>
				<td align="center"><a
					href="<%=request.getContextPath()%>/listUser">【ユーザー一覧】</a></td>
			</tr>
			<tr>
				<td align="center"><a
					href="<%=request.getContextPath()%>/exhibitionUserList">【出品者一覧】</a></td>
			</tr>

			<tr>
				<td align="center"><a
					href="<%=request.getContextPath()%>/messageList">【お問い合わせ一覧】</a></td>
			</tr>

			<tr>
				<td align="center"><a
					href="<%=request.getContextPath()%>/login">【ログアウト】</a></td>
			</tr>
		</table>

	</main><%@ include file="/common/footer.jsp"%>
</body>
</html>