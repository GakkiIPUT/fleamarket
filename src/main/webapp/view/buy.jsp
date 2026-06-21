<%@page contentType="text/html; charset=UTF-8"%>
<%@page import =""%>

<html>
<head>
<meta charset="UTF-8">
<title>購入手続き画面</title>
</head>
<body style="background-color:rgb(255, 255, 255); text-align:center;">
	<h1 style="color:rgb(255, 128, 128);">フリマシステム</h1>
	<a href="showGoodsList.html">トップページ</a>
	<a href="mypage.html">マイページ</a>
	<a href="goodDetail.html">商品詳細</a>
	<h3 style="color:rgb(255, 128, 128);">購入手続き</h3>

	<img src="../img/img1.png" alt="商品の写真">
	<p></p>
	<p></p>
	
	<form action="<%=request.getContextPath()%>/buyConfirm" method="get">	
		<table style="margin:0 auto">
			<tr>
				<td>商品名：</td>
				<td>A</td>
			</tr>
			<tr>
				<td>値段：</td>
				<td>〇〇円</td>
			</tr>
			<tr>
				<td>個数：</td>
				<td><input type="text" name="itemCount"></td>
			</tr>
			<tr>
				<td>お届け先：</td>
				<td>東京都〇〇区〇〇</td>
			</tr>
			<tr>
				<td>お支払方法：</td>
				<td><input type="radio" name="paymentMethod" 
					value="credit">クレジットカード
				</td>
			</tr>
			<tr>
				<td>合計金額：〇〇円</td>
				<td>〇〇円</td>
			</tr>
		</table>
		<div style="text-align:center">
			<input type="submit" value="確定">
		</div>
	</form>
</body>
</html>

