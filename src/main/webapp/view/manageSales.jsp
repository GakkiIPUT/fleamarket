<%@page import="util.MyFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.Sales, util.MyFormat" %>
<% MyFormat mf = new MyFormat(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者用売上確認</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/common/header.jsp" />

    <div class="main-container" style="padding: 20px; max-width: 1000px; margin: 0 auto;">
        <h2>【管理者用】売上管理システム</h2>
        
        <div style="background-color: #f4f4f4; padding: 15px; margin-bottom: 20px; border-radius: 5px; display: flex; justify-content: space-between; align-items: center;">
            <form action="<%= request.getContextPath() %>/admin/salesByMonth" method="get" style="margin: 0;">
                <strong>月別売上確認：</strong>
                <select name="year" required>
                    <option value="2026">2026年</option>
                    <option value="2025">2025年</option>
                </select>
                <select name="month" required>
                    <% for(int i=1; i<=12; i++) { %>
                        <option value="<%= i %>"><%= i %>月</option>
                    <% } %>
                </select>
                <input type="submit" value="月別集計を表示">
            </form>
            <div>
                <a href="<%= request.getContextPath() %>/list" class="btn">商品トップ一覧へ</a>
            </div>
        </div>

        <%
            // サーブレットから受け取った合計運営利益と売上リスト
            Integer totalCommission = (Integer) request.getAttribute("totalCommission");
            ArrayList<Sales> salesList = (ArrayList<Sales>) request.getAttribute("salesList");
            
            if (totalCommission == null) totalCommission = 0;
        %>

        <div style="background: linear-gradient(135deg, #2c3e50, #3498db); color: white; padding: 20px; border-radius: 8px; margin-bottom: 30px; text-align: center;">
            <p style="margin: 0; font-size: 1.2rem; letter-spacing: 1px;">運営累計総利益（システム利用料10%の合計）</p>
            <h1 style="margin: 10px 0 0 0; font-size: 3rem;"><%= mf.moneyFormat(totalCommission) %></h1>
        </div>

        <h3>◆ 全取引明細一覧</h3>
        <table class="list-table" style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            <thead>
                <tr style="background-color: #2c3e50; color: white; text-align: left;">
                    <th style="padding: 10px; border: 1px solid #ddd;">商品ID</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">商品名</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">出品者</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">購入者</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">商品価格</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">運営利益 (10%)</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">確定日時</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (salesList != null && !salesList.isEmpty()) {
                        for (Sales sale : salesList) {
                %>
                    <tr style="border-bottom: 1px solid #ddd;">
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= sale.getItemId() %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><strong><%= sale.getItem() %></strong></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= sale.getSellerName() != null ? sale.getSellerName() : "---" %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= sale.getBuyerName() != null ? sale.getBuyerName() : "---" %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; color: #e74c3c; font-weight: bold;"><%= mf.moneyFormat(sale.getPrice()) %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; color: #27ae60; font-weight: bold;"><%= mf.moneyFormat(sale.getCommission()) %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= sale.getBuyDateTime() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="7" style="padding: 20px; text-align: center; color: #777;">現在、成立している取引（売上実績）はありません。</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <jsp:include page="/common/footer.jsp" />

</body>
</html>