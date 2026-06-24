<%--
 プロジェクト名：フリマシステム
 プログラム名：detailUser.jsp
 プログラムの説明：ユーザー詳細の表示画面。
 作成日：2026年6月23日
 作成者：大瀬莉晏
--%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>

<%User resultUser = (User) session.getAttribute("resultUser"); %>
<html>
<head>
<title>ユーザー詳細情報</title>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<main>
		<div class="nav-header">
			<div class="nav-header-links">
				<a href="<%=request.getContextPath()%>/view/menu.jsp">[メニュー]</a> 
				<a href="<%=request.getContextPath()%>/listUser">[ユーザー一覧]</a>
			</div>
			<div class="nav-header-title">
				<h2 class="title">ユーザー詳細情報</h2>
			</div>
		</div>
		<hr class="head_foot_hr">
		<table align="center" class="form-table-30">
			<tr>
				<td colspan="2" align="center">
					<form action="<%=request.getContextPath()%>/detailUser"
						method="get" class="form-inline">
						<input type="hidden" name="user" value="<%=resultUser.getUserId()%>">
						<input type="hidden" name="cmd" value="updateUser"> <input
							type="submit" value="変更">
					</form> <a>&emsp;&emsp;</a>
					<form action="<%=request.getContextPath()%>/deleteUser"
						method="get" class="form-inline">
						<input type="hidden" name="user" value="<%=resultUser.getUserId()%>">
						<input type="submit" value="削除">
					</form>
				</td>
			</tr>
			<tr>
				<td><br></td>
				<td><br></td>
			</tr>
			<tr>
				<td class="header-color">ユーザーID</td>
				<td>	</td>
				<td class="column-color"><%=resultUser.getUserId()%></td>
			</tr>
			
			<tr>
				<td class="header-color">メールアドレス</td>
				<td></td>
				<td class="column-color"><%=resultUser.getMail()%></td>
			</tr>
			<tr>
				<td class="header-color">ニックネーム</td>
				<td></td>
				<td class="column-color"><%=resultUser.getNickname()%></td>
			</tr>
			<tr>
				<td class="header-color">名前</td>
				<td></td>
				<td class="column-color"><%=resultUser.getLastName()%> <%=resultUser.getFirstName()%></td>
			</tr>
			
			<tr>
				<td class="header-color">ナマエ</td>
				<td></td>
				<td class="column-color"><%=resultUser.getLastNameRubi()%> <%=resultUser.getFirstNameRubi()%></td>
			</tr>
			<tr>
				<td class="header-color">郵便番号</td>
				<td></td>
				<td class="column-color"><%=resultUser.getPostCode()%></td>
			</tr>
			<tr>
				<td class="header-color">都道府県</td>
				<td></td>
				<td class="column-color"><%=resultUser.getPrefectures()%></td>
			</tr>
			<tr>
				<td class="header-color">市区町村</td>
				<td></td>
				<td class="column-color"><%=resultUser.getCity()%></td>
			</tr>
			<tr>
				<td class="header-color">番地/建物名/号室</td>
				<td></td>
				<td class="column-color"><%=resultUser.getStreetAddress()%> <%=resultUser.getBuildingRoom()%></td>
			</tr>
			<tr>
				<td class="header-color">電話番号</td>
				<td></td>
				<td class="column-color"><%=resultUser.getTelephoneNumber()%></td>
			</tr>
			<tr>
				<td class="header-color">登録日時</td>
				<td></td>
				<td class="column-color"><%=resultUser.getCreateDateTime() %></td>
			</tr>
			<tr>
				<td class="header-color">更新日時</td>
				<td></td>
				<td class="column-color"><%=resultUser.getUpdateDateTime() %></td>
			</tr>
			<tr>
				<td class="header-color">退会チェック</td>
				<td></td>
				<%if(resultUser.getWithdrawalFlag()==0){ %>
				<td class="column-color">有効</td>
				<%}else{ %>
				<td class="column-color">退会済み</td>
				<%} %>
			</tr>
			
			
		</table>
	</main>
	<%@ include file="../common/footer.jsp"%>
</body>
</html>