<%--
プロジェクト名：フリマシステム
 プログラム名：header.jsp
 プログラムの説明：全画面共通のヘッダー部分。
 作成日：2026年6月22日
 作成者：髙垣湧侑翔
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="bean.User"%>
<%
// セッションからユーザー情報を取得
User userHeader = (User) session.getAttribute("user");
%>
<link rel="stylesheet"
    href="<%=request.getContextPath()%>/css/style.css">

<header>
    <h1>神田雑貨</h1>
    <hr>
    
    <div class="header-right">
        <%
        // ①ログインしている場合
        if (userHeader != null) {
            
            // ②さらに一般ユーザーの場合のみ「マイページ」「出品」を表示する
            if (userHeader.getAuthorityFlag() == 0) {
        %>
        <form action="<%=request.getContextPath()%>/view/myPage.jsp"
            method="get" style="display: inline;">
            <input type="submit" value="マイページ" class="header-btn">
        </form>
        <form action="<%=request.getContextPath()%>/view/insertItem.jsp"
            method="get" style="display: inline;">
            <input type="submit" value="出品" class="header-btn">
        </form>
        <%
            }
        %>
        
        <form action="<%=request.getContextPath()%>/logout"
            method="post" style="display: inline;">
            <input type="submit" value="ログアウト" class="header-btn">
        </form>
        
        <%
        // ③ログインしていない場合
        } else {
        %>
        <form action="<%=request.getContextPath()%>/login" method="get"
            style="display: inline;">
            <input type="submit" value="ログイン" class="header-btn">
        </form>
        <%
        }
        %>
    </div>
</header>