<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, bean.Sales, util.MyFormat" %>
<% MyFormat mf = new MyFormat(); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>月別売上確認</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <jsp:include page="/common/header.jsp" />

    <div class="main-container" style="padding: 20px; max-width: 1000px; margin: 0 auto;">
        <%
            // サーブレットから指定年月と抽出リストを取得
            String year = request.getParameter("year");
            String month = request.getParameter("month");
            ArrayList<Sales> salesList = (ArrayList<Sales>) request.getAttribute("salesList");

            int monthlyTotalSales = 0;
            int monthlyTotalProfit = 0;

            if (salesList != null) {
                for (Sales sale : salesList) {
                    monthlyTotalSales += sale.getPrice();
                    monthlyTotalProfit += sale.getCommission();
                }
            }
        %>

        <h2><%= year %>年<%= month %>月度 売上集計結果</h2>

        <div style="margin-bottom: 20px;">
            <a href="<%= request.getContextPath() %>/admin/sales" class="btn" style="background-color: #7f8c8d; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-size: 0.9rem;">← 全体売上概要に戻る</a>
        </div>

        <div style="display: flex; gap: 20px; margin-bottom: 30px;">
            <div style="flex: 1; background-color: #f1f2f6; border-left: 5px solid #ff6b6b; padding: 15px; border-radius: 4px;">
                <span style="color: #747d8c; font-size: 0.9rem;">当月総流通額（商品売上総額）</span>
                <h2 style="margin: 5px 0 0 0; color: #ff4757;"><%= mf.moneyFormat(monthlyTotalSales) %></h2>
            </div>
            <div style="flex: 1; background-color: #f1f2f6; border-left: 5px solid #2ed573; padding: 15px; border-radius: 4px;">
                <span style="color: #747d8c; font-size: 0.9rem;">当月運営利益（システム利用料総額）</span>
                <h2 style="margin: 5px 0 0 0; color: #2ed573;"><%= mf.moneyFormat(monthlyTotalProfit) %></h2>
            </div>
            <div style="flex: 1; background-color: #f1f2f6; border-left: 5px solid #1e90ff; padding: 15px; border-radius: 4px;">
                <span style="color: #747d8c; font-size: 0.9rem;">当月取引成立件数</span>
                <h2 style="margin: 5px 0 0 0; color: #1e90ff;"><%= salesList != null ? salesList.size() : 0 %> 件</h2>
            </div>
        </div>

        <h3>◆ <%= month %>月の取引明細</h3>
        <table class="list-table" style="width: 100%; border-collapse: collapse; margin-top: 10px;">
            <thead>
                <tr style="background-color: #7f8c8d; color: white; text-align: left;">
                    <th style="padding: 10px; border: 1px solid #ddd;">商品ID</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">商品名</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">売上金額</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">システム手数料</th>
                    <th style="padding: 10px; border: 1px solid #ddd;">取引完了日</th>
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
                        <td style="padding: 10px; border: 1px solid #ddd; font-weight: bold;"><%= mf.moneyFormat(sale.getPrice()) %></td>
                        <td style="padding: 10px; border: 1px solid #ddd; color: #2ed573; font-weight: bold;"><%= mf.moneyFormat(sale.getCommission()) %></td>
                        <td style="padding: 10px; border: 1px solid #ddd;"><%= sale.getBuyDateTime() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="5" style="padding: 20px; text-align: center; color: #777;"><%= year %>年<%= month %>月度の取引完了データは存在しません。</td>
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