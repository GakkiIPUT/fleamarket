<%@ page contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<meta charset="UTF-8">
		<title>mypage</title>
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">	
	</head>
	<body style="text-align: center">
	<jsp:include page="/common/header.jsp" />
	<header>
	<div class = "header-left">
		<form action = "<%= request.getContextPath() %>/list" method="get" style="display: inline;">
			<input type="submit" value="トップページ" class="header-btn">
		</form>
	</div>
		<h2 class="title">マイページ</h2>
		<hr class="head_foot_hr">
	</header>
	<main>
		
	
		<table style="margin:auto; border:0; text-align:center;">
			<tr><td><a href="<%= request.getContextPath() %>/detailUser">【ユーザー情報 確認/変更】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/history">【購入履歴】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/myItems">【出品物一覧】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/sales">【売上確認】</a></td></tr>
			<tr><td><a href="<%= request.getContextPath() %>/view/inquiry.jsp">【管理者問い合わせ】</a></td></tr>
		</table>
	</main>
	<jsp:include page="/common/footer.jsp" />	</body>
</html>