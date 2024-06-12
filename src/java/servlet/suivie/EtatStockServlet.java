/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.suivie;

import connection.GConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.local.Store;
import model.produit.Article;
import model.suiviStock.EtatStock;
import model.suiviStock.Stock;

/**
 *
 * @author Chalman
 */
public class EtatStockServlet extends HttpServlet {

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
            out.println("<title>Servlet EtatStockServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EtatStockServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            out.print("Tonga");
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
            List<Article> articles = Article.getAll(null);
            List<Store> stores = Store.getAll(null);
            request.setAttribute("articles", articles);
            request.setAttribute("stores", stores);
        } catch(Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/formEtatStock.jsp");
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
            String dateDebut = request.getParameter("date_debut");
            String dateFin = request.getParameter("date_fin");
            List<Stock> stocks = new ArrayList<>();
            Double montantTotal = 0.0;
            
            //Recuperation d'information selon les requetes
            if(request.getParameter("article").equals("%") && !request.getParameter("store").equals("%")) { //All article dans un magasin
                Store store = Store.getById(null, Integer.valueOf(request.getParameter("store")));
                List<Integer> allArticleGrouped = EtatStock.groupByArticleIn(null, Date.valueOf(dateFin), store.getIdStore(), "<=");
                for(int i = 0; i < allArticleGrouped.size(); i++) {
                    Article article = Article.getById(null, allArticleGrouped.get(i));
                    stocks.add(Stock.getStock(null, dateDebut, dateFin, article, store));
                }
            }
            else if(!request.getParameter("article").equals("%") && request.getParameter("store").equals("%")) { //Un article dans un magasin
                Article article = Article.getById(null, Integer.valueOf(request.getParameter("article")));
                List<Integer> allStoreGrouped = EtatStock.groupByStoreIn(null, Date.valueOf(dateFin), article.getIdArticle(), "<=");
                for(int i = 0; i < allStoreGrouped.size(); i++) {
                    Store store = Store.getById(null, allStoreGrouped.get(i));
                    stocks.add(Stock.getStock(null, dateDebut, dateFin, article, store));
                }
            }
            else if(request.getParameter("article").equals("%") && request.getParameter("store").equals("%")) { //All article dans all magasin
                List<ObjectHelp> objetsHelps = ObjectHelp.getAll(null);
                for(int i = 0; i < objetsHelps.size(); i++) {
                    for(int j = 0; j < objetsHelps.size(); j++) {
                        stocks.add(Stock.getStock(null, dateDebut, dateFin, Article.getById(null, objetsHelps.get(i).getIdArticle()), Store.getById(null, objetsHelps.get(i).getIdStore())));
                    }
                }
            }
            else {  //Un article dans un magasin
                Article article = Article.getById(null, Integer.valueOf(request.getParameter("article")));
                Store store = Store.getById(null, Integer.valueOf(request.getParameter("store")));
                stocks.add(Stock.getStock(null, dateDebut, dateFin, article, store));
            }
           
            EtatStock etatStock = new EtatStock(LocalDate.parse(dateDebut), LocalDate.parse(dateFin), stocks);
            request.setAttribute("etatStock", etatStock);
            
        } catch(Exception e) {
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        RequestDispatcher dispat = request.getRequestDispatcher("./pages/etatStock.jsp");
        dispat.forward(request, response);
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
