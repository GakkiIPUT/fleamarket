package servlet;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Sales;
import bean.User;
import dao.SalesDAO;

@WebServlet("/admin/sales")
public class ManageSalesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // ログインチェックと権限チェック
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getAuthorityFlag() != 1) { 
            response.sendRedirect("login");
            return;
        }

        SalesDAO dao = new SalesDAO();
        ArrayList<Sales> list = dao.selectAllSales();

        // 合計利益（システム利用料）の計算
        int totalCommission = 0;
        for (Sales s : list) {
            totalCommission += s.getCommission();
        }

        request.setAttribute("salesList", list);
        request.setAttribute("totalCommission", totalCommission);

        request.getRequestDispatcher("/view/manageSales.jsp").forward(request, response);
    }
}