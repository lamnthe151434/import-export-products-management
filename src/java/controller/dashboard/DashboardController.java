/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.dashboard;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class DashboardController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("view/dashboard.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> periods = new ArrayList<>();
        periods.add("Hôm nay");
        periods.add("Hôm qua");
        periods.add("7 ngày trước");
        periods.add("Tháng này");
        periods.add("Tháng trước");
        periods.add("Năm nay");
        request.setAttribute("periods", periods);
        request.setAttribute("selectedPeriod", "Hôm qua");
        ArrayList<String> baseOn = new ArrayList<>();
        baseOn.add("Theo số lượng");
        baseOn.add("Theo doanh thu");

        String selectedPeriod1 = request.getParameter("period1");
        String selectedPeriod2 = request.getParameter("period2");
        String selectedBaseOn = request.getParameter("baseOn");

        if (selectedPeriod1 == null) {
            selectedPeriod1 = "Hôm nay";
        }

        if (selectedPeriod2 == null) {
            selectedPeriod2 = "Hôm nay";
        }
        if (selectedBaseOn == null) {
            selectedBaseOn = "Theo doanh thu";
        }

        String xValues = "1, 2, 3, 4, 5, 6, 7, 8, 9, 10";
        String yValues = "100, 200, 300, 400, 10, 600, 70, 800, 90, 10000";

        request.setAttribute("periods", periods);
        request.setAttribute("baseOn", baseOn);
        request.setAttribute("selectedPeriod", "Hôm qua");
        request.setAttribute("xValues", xValues);
        request.setAttribute("yValues", yValues);
        request.setAttribute("selectedBaseOn", "Số lượng");
        request.getRequestDispatcher("view/dashboard.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
