/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.suivie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.local.Store;
import model.mouvement.In;
import model.mouvement.Out;
import model.mouvement.RequestOut;
import model.produit.Article;
import model.produit.UnityEquivalent;

/**
 *
 * @author Chalman
 */
@WebServlet(name = "DetailsMvtServlet", urlPatterns = {"/DetailsMvt"})
public class DetailsMvtServlet extends HttpServlet {

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
            out.println("<title>Servlet DetailsMvtServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailsMvtServlet at " + request.getContextPath() + "</h1>");
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
            String idHelp = request.getParameter("idHelp");
            List<Article> articles = Article.getAll(null);
            List<Store> stores = Store.getAll(null);
            List<UnityEquivalent> unitysEquivalent = UnityEquivalent.getAll(null);
            request.setAttribute("articles", articles);
            request.setAttribute("stores", stores);
            request.setAttribute("unityEquivalents", unitysEquivalent);
            if(Integer.valueOf(idHelp) == 1) {  //Entree
                String idIn = request.getParameter("idIn");
                In inFinded = In.getById(null, Integer.valueOf(idIn));
                request.setAttribute("enter", inFinded);
                RequestDispatcher dispat = request.getRequestDispatcher("./pages/detailsIn.jsp");
                dispat.forward(request, response);
            } else {    //Sortie
                String idRequestOut = request.getParameter("idRequestOut");
                RequestOut requestOutFinded = RequestOut.getById(null, Integer.valueOf(idRequestOut));
                request.setAttribute("requestOut", requestOutFinded);
                RequestDispatcher dispat = request.getRequestDispatcher("./pages/detailsOut.jsp");
                dispat.forward(request, response);
            }
        } catch(Exception e) {
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
