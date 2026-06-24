package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Sales;
import dao.SalesDAO;

@WebServlet("/admin/salesByMonth")
public class ShowSalesByMonthServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String year = request.getParameter("year");
        String month = request.getParameter("month");

        ArrayList<Sales> list = new ArrayList<>();
        SalesDAO dao = new SalesDAO();

        if (year != null && month != null) {
            // 検索パラメータがある場合は抽出
            list = dao.selectBySales(year, month);
        }

        request.setAttribute("salesList", list);
        request.getRequestDispatcher("/view/showSaleByMonth.jsp").forward(request, response);
    }
}