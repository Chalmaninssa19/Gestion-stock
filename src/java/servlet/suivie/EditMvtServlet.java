/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.suivie;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.mouvement.In;
import model.mouvement.Out;
import model.mouvement.RequestOut;

/**
 *
 * @author Chalman
 */
@WebServlet(name = "EditMvtServlet", urlPatterns = {"/EditMvt"})
public class EditMvtServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditMvtServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditMvtServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
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
        String idType = request.getParameter("idType");
        try {
            String article = request.getParameter("article");
            String store = request.getParameter("store");
            String quantity = request.getParameter("quantity");
            String date = request.getParameter("date");
            if(Integer.valueOf(idType) == 1) {  //Entree
                Integer idIn = Integer.valueOf(request.getParameter("idIn"));
                In enter = In.getById(null, idIn);
                String coutUnitaire = request.getParameter("coutUnitaire");
                enter.setArticle(null, article);
                enter.setStore(null, store);
                enter.setQuantity(quantity);
                enter.setUnitary_cost(coutUnitaire);
                enter.setDateIn(date);
                enter.update(null);
                response.sendRedirect("DetailsMvt?idIn="+enter.getIdIn()+"&idHelp=1");
                //RequestDispatcher dispat = request.getRequestDispatcher("DetailsMvt?idIn="+enter.getIdIn()+"&idHelp=1");
                //dispat.forward(request, response);
            } else {    //sortie
                Integer idOut = Integer.valueOf(request.getParameter("idRequestOut"));
                RequestOut requestOut = RequestOut.getById(null, idOut);
                requestOut.setArticle(null, article);
                requestOut.setStore(null, store);
                requestOut.setQuantity(quantity);
                requestOut.setDateRequestOut(date);
                requestOut.update(null);
                response.sendRedirect("DetailsMvt?idRequestOut="+requestOut.getIdRequestOut()+"&idHelp=2");
            }
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
             if(Integer.valueOf(idType) == 1) {  //Entree
                Integer idIn = Integer.valueOf(request.getParameter("idIn"));
                In enter = In.getById(null, idIn);
                response.sendRedirect("DetailsMvt?idIn="+enter.getIdIn()+"&idHelp=1");
            } else {    //sortie
                Integer idOut = Integer.valueOf(request.getParameter("idRequestOut"));
                RequestOut requestOut = RequestOut.getById(null, idOut);
                response.sendRedirect("DetailsMvt?idRequestOut="+requestOut.getIdRequestOut()+"&idHelp=2");
        }
    }
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
