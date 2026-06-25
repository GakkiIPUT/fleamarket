<%--
 プロジェクト名：フリマシステム
 プログラム名：list.jsp
 プログラムの説明：商品一覧画面。
 作成日：2026年6月19日
 作成者：大瀬莉晏
 更新者：髙垣湧侑翔|画像機能、商品がZ字に並ぶように変更
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.util.ArrayList" %>
<%@ page import="bean.Item" %>

<%
ArrayList<Item> list = (ArrayList<Item>) request.getAttribute("item_list");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>フリマ商品一覧</title>
	    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	</head>
	<main>
	<body style="background-color: rgb(255, 255, 255); text-align: center;">
	
	    <%@ include file="../common/header.jsp" %>
	
		<h2 class="title">商品一覧</h2>
		<hr class="head_foot_hr">
	    <div style="margin: 20px auto;">
	        <form action="<%=request.getContextPath()%>/search" method="get" class="form-inline">
	            商品名:<input type="text" name="item" class="input-text-border-gray">
	            種類:<input type="text" name="type" class="input-text-border-gray">
	<input type="submit" value="検索">
	        </form>
	    </div>
	
	    <div style="display: flex; flex-wrap: wrap; justify-content: center; gap: 25px; max-width: 1000px; margin: 0 auto; padding: 20px;">
	        <%
	        if (list != null) {
	            for (int i = 0; i < list.size(); i++) {
	                Item item = (Item) list.get(i);
	                //画像名を取得
	                String imgName = item.getImage();
	                // もし画像名が null または空ならデフォルトに設定
	                if (imgName == null || imgName.isEmpty() || imgName.equals("null")) {
	                    imgName = "no_image.jpg";
	                }
	        %>
	            <div style="width: 200px; border: 1px solid #eee; padding: 10px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.05); background: #fff; text-align: center;">
	
	                <a href="<%= request.getContextPath() %>/detail?itemId=<%= item.getItemId() %>" style="text-decoration: none; color: inherit;">
	
	                    <div style="width: 180px; height: 180px; margin: 0 auto; overflow: hidden; border-radius: 4px;">
	                        <img src="<%= request.getContextPath() %>/image/<%= imgName %>" style="width: 100%; height: 100%; object-fit: cover;">
	                    </div>
	
	                    <div style="font-weight: bold; margin-top: 10px; font-size: 14px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
	                        <%= item.getItem() %>
	                    </div>
	
	                    <div style="color: #ff4d4d; font-weight: bold; margin-top: 5px; font-size: 16px;">
	                        &yen;<%= item.getPrice() %>
	                    </div>
	                </a>
	
	            </div>
	        <%
	            }
	        } else {
	        %>
	            <p>出品されている商品はありません。</p>
	        <%
	        }
	        %>
	    </div>
	</main>
    <%@ include file="../common/footer.jsp" %>

</body>
</html>