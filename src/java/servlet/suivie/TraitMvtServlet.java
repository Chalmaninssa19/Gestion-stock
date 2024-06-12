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
import javax.servlet.http.HttpSession;
import model.mouvement.In;
import model.mouvement.Out;
import model.mouvement.RequestOut;

/**
 *
 * @author Chalman
 */
@WebServlet(name = "TraitMvtServlet", urlPatterns = {"/TraitMvt"})
public class TraitMvtServlet extends HttpServlet {

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
            out.println("<title>Servlet TraitMvtServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TraitMvtServlet at " + request.getContextPath() + "</h1>");
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
        try {
            String idType = request.getParameter("idType");
            String status = request.getParameter("status");
            if(Integer.valueOf(idType) == 1) {  //Entree
                In enter = In.getById(null, Integer.valueOf(request.getParameter("idEnter")));
                enter.setStatus(Integer.valueOf(status));
                enter.update(null);
                RequestDispatcher dispat = request.getRequestDispatcher("DetailsMvt?idIn="+enter.getIdIn()+"&idHelp=1");
                dispat.forward(request, response);
            } else {    //Sortie
                RequestOut requestOut = RequestOut.getById(null, Integer.valueOf(request.getParameter("idOut")));
              
                if(Integer.valueOf(status) == 10) {
                    HttpSession session = request.getSession();
                    System.out.println("idrequestOut : "+request.getParameter("idOut"));
                    Integer idRequestOut = Integer.valueOf(request.getParameter("idOut"));
                    session.setAttribute("requestOut", RequestOut.getById(null, idRequestOut));
                    RequestDispatcher dispat = request.getRequestDispatcher("./pages/dateValidation.jsp");
                    dispat.forward(request, response);
                } else {
                    requestOut.setStatus(Integer.valueOf(status));
                    requestOut.update(null);
                }
               
                RequestDispatcher dispat = request.getRequestDispatcher("DetailsMvt?idRequestOut="+requestOut.getIdRequestOut()+"&idHelp=2");
                dispat.forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
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
