/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mouvement;

import connection.GConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.local.Store;
import model.produit.Article;
import model.produit.UnityEquivalent;
import utilitaire.DateManagement;

/**
 *
 * @author Chalman
 */
public class RequestOut {
    private Integer idRequestOut;
    private LocalDate dateRequestOut;
    private Article article;
    private Double quantity;
    private Store store;
    private Integer status;
    private UnityEquivalent unityEquivalent;
    
///Getters et setters
    public Integer getIdRequestOut() {
        return idRequestOut;
    }
    public void setIdRequestOut(Integer idRequestOut) {
        this.idRequestOut = idRequestOut;
    }
    
    public LocalDate getDateRequestOut() {
        return dateRequestOut;
    }
    public void setDateRequestOut(LocalDate dateRequestOut) {
        this.dateRequestOut = dateRequestOut;
    }
    public void setDateRequestOut(String dateRequestOut) throws Exception {
        if(dateRequestOut.trim().equals("") || dateRequestOut == null) {
            throw new Exception("La date sortie ne doit pas etre null"); 
        }
        LocalDate dateOutParsed = LocalDate.parse(dateRequestOut);
       
        this.setDateRequestOut(dateOutParsed);
    }

    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
    public void setQuantity(String quantity) throws  Exception {
        if(quantity.trim().equals("") || quantity == null) {
            throw new Exception("La quantite ne doit pas etre null");
        }
        Double quantite = Double.valueOf(quantity);
        if(quantite < 0) {
            throw new Exception("La quantite doit etre un nombre positive");
        }
      
        Double totalQuantityRequestOut = RequestOut.getSumRequestOut(null, Date.valueOf(this.getDateRequestOut()), this.getArticle().getIdArticle(), this.getStore().getIdStore(), this.getIdRequestOut());
        Double totalQuantityIn = In.getSumIn(null, Date.valueOf(this.getDateRequestOut()), this.getArticle().getIdArticle(), this.getStore().getIdStore());
        
        Double quantiteReel = this.getArticle().getQuantityEquivalent()*quantite;
        System.out.println("totalQuantite sortie = "+totalQuantityRequestOut);
        System.out.println("Total quantite Entree = "+totalQuantityIn);
        System.out.println("Quantite reel = "+quantiteReel);
        
        if((totalQuantityIn - totalQuantityRequestOut) - quantiteReel < 0) {
            throw new Exception("Votre stock est insuffisant, penser a entrer");
        }
        
        this.setQuantity(quantiteReel);
    }
    
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public void setArticle(Connection connection, String article) throws Exception {
        if(article.trim().equals("") || article == null) {
            throw new Exception("L'article ne doit pas etre null");
        }
        Integer idArticle = Integer.valueOf(article);
        this.setArticle(Article.getById(connection, idArticle));
    }

    public Store getStore() {
        return store;
    }
    public void setStore(Store store) {
        this.store = store;
    }
    public void setStore(Connection connection, String store) throws Exception {
        if(store.trim().equals("") || store == null) {
            throw new Exception("Le store ne doit pas etre null");
        }
        Integer idStore = Integer.valueOf(store);
        this.setStore(Store.getById(connection, idStore));
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public UnityEquivalent getUnityEquivalent() {
        return unityEquivalent;
    }
    public void setUnityEquivalent(UnityEquivalent unityEquivalent) {
        this.unityEquivalent = unityEquivalent;
    }
     public void setUnityEquivalent(String unityEquivalent) throws Exception {
        try {
            Integer idUnityEquivalent = Integer.valueOf(unityEquivalent);
            Article article = Article.getByArticleUnityEquivalent(null, this.getArticle().getIdArticle(), idUnityEquivalent);
            if(article == null) {
                throw new Exception("Cette unite d'equivalence ne correspond pas a celle d'article, veuillez ressayer");
            }
            this.setUnityEquivalent(UnityEquivalent.getById(null, idUnityEquivalent));
        } catch(Exception e) {
            throw e;
        }
    }
    
    public void updateValue() {
        Double quantityIn = this.getQuantity();
        this.setQuantity(this.getArticle().getQuantityEquivalent()*quantityIn);
    }
    
    public Double getQuantityReel() {
        return this.getQuantity() / this.getArticle().getQuantityEquivalent();
    }

///Constructors
    public RequestOut() {
    }

    public RequestOut(Integer idRequestOut, LocalDate dateRequestOut, Article article, Double quantity, Store store, Integer status, UnityEquivalent unityEquivalent) {
        this.dateRequestOut = dateRequestOut;
        this.idRequestOut = idRequestOut;
        this.article = article;
        this.quantity = quantity;
        this.store = store;
        this.status = status;
        this.unityEquivalent = unityEquivalent;
    }

    public RequestOut(String date, String article, String quantity, String store, Integer status, String unityEquivalent) throws Exception {
        try {
            this.setArticle(null, article);
            this.setStore(null, store);
            this.setDateRequestOut(date);
            this.setQuantity(quantity);
            this.status = status;
            this.setUnityEquivalent(unityEquivalent);
        } catch(Exception e) {
            throw e;
        }
    }
///Fonctions
     public void save(Connection connection) {
        String query = "INSERT INTO request_out ( date_request_out, id_article, quantity, id_store, status, id_unity_equivalent) VALUES ( ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            boolean closeable = false;
            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }

                statement = connection.prepareStatement(query);
                statement.setDate(1, Date.valueOf(this.getDateRequestOut()));
                statement.setInt(2, this.getArticle().getIdArticle());
                statement.setDouble(3, this.getQuantity());
                statement.setInt(4, this.getStore().getIdStore());
                statement.setInt(5, this.getStatus());
                 statement.setInt(6, this.getUnityEquivalent().getIdUnityEquivalent());

                statement.executeUpdate();

              
                
            if (closeable) {
                statement.close();
                connection.commit();
                connection.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RequestOut getById(Connection connection, int id) {
        RequestOut requestOut = new RequestOut();
        try {
        String query = "SELECT * FROM request_out WHERE id_request_out= %d";
        
        query = String.format(query, id);
        
        Statement statement = null;
        ResultSet resultset = null;
        boolean closeable = false;

            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
            }
            
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                requestOut.setIdRequestOut(resultset.getInt("id_request_out"));
                requestOut.setDateRequestOut(DateManagement.dateToLocalDate(resultset.getDate("date_request_out")));
                requestOut.setArticle(Article.getById(null, resultset.getInt("id_article")));
                requestOut.setQuantity(resultset.getDouble("quantity"));
                requestOut.setStore(Store.getById(null, resultset.getInt("id_store")));
                requestOut.setStatus(resultset.getInt("status"));
                requestOut.setUnityEquivalent(UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return requestOut;
    }
     
   /* public static Out getLast(Connection connection, int idArticle, int idStore) {
        Out out = new Out();
        System.out.println("Tafa aminy last");
        try {
        String query = "SELECT * FROM out WHERE id_article=%d AND id_store=%d ORDER BY date_out DESC LIMIT 1";
        
        query = String.format(query, idArticle, idStore);
        System.out.println("Query : "+query);
        
        Statement statement = null;
        ResultSet resultset = null;
        boolean closeable = false;

            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
            }
            
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                out.setIdOut(resultset.getInt("id_out"));
                out.setDateOut(DateManagement.dateToLocalDate(resultset.getDate("date_out")));
                out.setArticle(Article.getById(null, resultset.getInt("id_article")));
                out.setQuantity(resultset.getDouble("quantity"));
                out.setStore(Store.getById(null, resultset.getInt("id_store")));
                out.setIn(In.getById(null, resultset.getInt("id_in")));
                out.setStatus(resultset.getInt("status"));
                
                return out;
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return null;
    }
    */
    public static List<RequestOut> getAll(Connection connection) {
        List<RequestOut> requestOuts = new ArrayList<>();
        String query = "SELECT * FROM request_out ORDER BY date_request_out DESC";

        Statement statement = null;
        ResultSet resultset = null;

        try {
            boolean closeable = false;
            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }
            
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            while (resultset.next()) {
              requestOuts.add(new RequestOut(resultset.getInt("id_request_out"),DateManagement.dateToLocalDate(resultset.getDate("date_request_out")), Article.getById(null, resultset.getInt("id_article")), resultset.getDouble("quantity"), Store.getById(null, resultset.getInt("id_store")), resultset.getInt("status"), UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent"))));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return requestOuts;
    }
    
    //Mise a jour du demande
     public void update(Connection connection) {
        String query = "UPDATE request_out SET date_request_out=?, id_article=?, quantity=?, id_store=?, status=?, id_unity_equivalent=? WHERE id_request_out=?";
        PreparedStatement statement = null;
        ResultSet resultset = null;

        try {
            boolean closeable = false;
            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }
   
                statement = connection.prepareStatement(query);
                statement.setDate(1, Date.valueOf(this.getDateRequestOut()));
                statement.setInt(2, this.getArticle().getIdArticle());
                statement.setDouble(3, this.getQuantity());
                statement.setInt(4, this.getStore().getIdStore());
                statement.setInt(5, this.getStatus());
                statement.setInt(6, this.getUnityEquivalent().getIdUnityEquivalent());
                statement.setInt(7, this.getIdRequestOut());

                statement.executeUpdate();

            if (closeable) {
                statement.close();
                connection.commit();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    //Avoir la lettre de l'etat
    public String getStatusLetter() {
        if(this.getStatus() == 1) {
        return "en cours";
        } else if(this.getStatus() == 10) {
            return "Valider";
        } 
        
        return "Refuser";
    }
    
    //Avoir le badge d'un status
    public String getStatusBadge() {
        if(this.getStatus() == 1) {
            return "warning";
        } else if(this.getStatus() == 10) {
            return "success";
        }
        
        return "danger";
    }
    
     public static Double getSumRequestOut(Connection connection, Date dateOut, int idArticle, int idStore, Integer idRequestOut) {
        Double sumOut = 0.0;
        try {
            String query = "";
            if(idRequestOut == null) {
                query = "select sum(quantity) as sum_quantity from request_out where date_request_out <= '%s' and id_article=%d and id_store=%d AND status!=0";
            } else {
                query = "select sum(quantity) as sum_quantity from request_out where date_request_out <= '%s' and id_article=%d and id_store=%d AND status!=0 AND id_request_out!="+idRequestOut;
            }
        
        query = String.format(query, dateOut, idArticle, idStore);
        Statement statement = null;
        ResultSet resultset = null;
        boolean closeable = false;

            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }

            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                sumOut = resultset.getDouble("sum_quantity");
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return sumOut;
    }
     
   /* public static void main(String [] args) throws Exception {
        try {
           
    //public RequestOut(String date, String article, String quantity, String store, Integer status) throws Exception {
    RequestOut rq = new RequestOut("2023-11-03", "1", "20", "1", 1);
    rq.save(null);
    
        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/
}
