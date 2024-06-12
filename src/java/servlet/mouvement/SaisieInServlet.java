/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.mouvement;

import connection.GConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.local.Store;
import model.mouvement.In;
import model.produit.Article;
import model.produit.UnityEquivalent;

/**
 *
 * @author Chalman
 */
public class SaisieInServlet extends HttpServlet {

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
            out.println("<title>Servlet SaisieInServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaisieInServlet at " + request.getContextPath() + "</h1>");
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
            //Recuperatoin d'information
            List<Article> articles = Article.getAll(null);
            List<Store> stores = Store.getAll(null);
            List<UnityEquivalent> unityEquivalents = UnityEquivalent.getAll(null);
            request.setAttribute("articles", articles);
            request.setAttribute("stores", stores);
            request.setAttribute("unityEquivalents", unityEquivalents);
            
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/entree.jsp");
        dispat.forward(request, response);
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
        try {
            //Recuperation des requetes
            String article = request.getParameter("article");
            String store = request.getParameter("store");
            String date = request.getParameter("date");
            String quantity = request.getParameter("quantity");
            String coutUnitaire = request.getParameter("coutUnitaire");
            String unityEquivalent = request.getParameter("unityEquivalent");
            
            //Traitement des requetes
            In newIn = new In(date, article, quantity, coutUnitaire, store, 1, unityEquivalent);
            newIn.save(null);
            request.setAttribute("message", "Succes");
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        doGet(request, response);
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
