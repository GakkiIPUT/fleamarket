<%@ page contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<title>mypage</title>
	</head>
	<body>
		<h1 style="text-align: center;">フリマシステム</h1>
		<h2 style="text-align: center;">マイページ</h2>
	
		<table style="margin:auto; border:0; text-align:center;">
			<tr><td><a href="<%= request.getContextPath() %>/detailUser">【ユーザー情報 確認/変更】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/history">【購入履歴】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/">【出品物一覧】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/sales">【売上確認】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/view/inquiry.jsp">【管理者問い合わせ】</a></td></tr>
		</table>
	</body>
</html>