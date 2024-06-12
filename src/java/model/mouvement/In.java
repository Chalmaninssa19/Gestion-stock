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
public class In {
    private Integer idIn;
    private LocalDate dateIn;
    private Article article;
    private Double quantity;
    private Double unitary_cost;
    private Store store;
    private Double quantityRest;
    private Integer status;
    private UnityEquivalent unityEquivalent;
    private Double quantityReel;

///Getters et setters
    public Integer getIdIn() {
        return idIn;
    }
    public void setIdIn(Integer idIn) {
        this.idIn = idIn;
    }
    
    public LocalDate getDateIn() {
        return dateIn;
    }
    public void setDateIn(LocalDate dateIn) {
        this.dateIn = dateIn;
    }
    public void setDateIn(String dateIn) throws Exception {
        if(dateIn.trim().equals("") || dateIn == null) {
            throw new Exception("La date entree ne doit pas etre null"); 
        }
        LocalDate dateInParsed = LocalDate.parse(dateIn);
        if(dateInParsed.isBefore(this.getStore().getDateCreation())) {
            throw new Exception("La date entree doit etre apres la date creation du magasin selectionne, soit apres "+this.getStore().getDateCreation());
        }
        this.setDateIn(dateInParsed);
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
        this.setQuantity(quantite);
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

    public Double getUnitary_cost() {
        return unitary_cost;
    }
    public void setUnitary_cost(Double unitary_cost) {
        this.unitary_cost = unitary_cost;
    }
    public void setUnitary_cost(String unitaryCost) throws Exception {
        if(unitaryCost.trim().equals("") || unitaryCost == null) {
            throw new Exception("Le cout unitaire ne doit pas etre null");
        }
        Double unitaryCostCasted = Double.valueOf(unitaryCost);
        if(unitaryCostCasted < 0) {
            throw new Exception("Le cout unitaire doit etre positive");
        }
        this.setUnitary_cost(unitaryCostCasted);
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

    public Double getQuantityRest() {
        return quantityRest;
    }
    public void setQuantityRest(Double quantiteRestant) {
        this.quantityRest = quantiteRestant;
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
        Double unitary_cost = this.getUnitary_cost();
        this.setUnitary_cost(unitary_cost/this.getArticle().getQuantityEquivalent());
    }
    
    public Double getQuantityReel() {
        return this.getQuantity() / this.getArticle().getQuantityEquivalent();
    }
    public Double getPrixReel() {
        return this.getUnitary_cost() * this.getArticle().getQuantityEquivalent();
    } 
///Constructors
    public In() {
    }

    public In(Integer idIn, LocalDate dateIn, Article article, Double quantity, Double unitary_cost, Store store, Double quantityRest, Integer status, UnityEquivalent unityEquivalent) {
        this.idIn = idIn;
        this.dateIn = dateIn;
        this.article = article;
        this.quantity = quantity;
        this.unitary_cost = unitary_cost;
        this.store = store;
        this.quantityRest = quantityRest;
        this.status = status;
        this.unityEquivalent = unityEquivalent;
    }

    public In(String dateIn, String article, String quantity, String unitaryCost, String store, Integer status, String unityEquivalent) throws Exception {
        try {
            this.setStore(null, store);
            this.setDateIn(dateIn);
            this.setArticle(null, article);
            this.setQuantity(quantity);
            this.setUnitary_cost(unitaryCost);
            this.quantityRest = this.getQuantity();
            this.status = status;
            this.setUnityEquivalent(unityEquivalent);
            this.updateValue();
        } catch(Exception e) {
            throw e;
        }
    }
///Fonctions
    //Sauvegarder une entree
    public void save(Connection connection) {
        String query = "INSERT INTO enter ( date_in, id_article, quantity, unitary_cost, id_store, quantity_rest, status, id_unity_equivalent) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
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
                statement.setDate(1, Date.valueOf(this.getDateIn()));
                statement.setInt(2, this.getArticle().getIdArticle());
                statement.setDouble(3, this.getQuantity());
                statement.setDouble(4, this.getUnitary_cost());
                statement.setInt(5, this.getStore().getIdStore());
                statement.setDouble(6, this.getQuantityRest());
                statement.setInt(7, this.getStatus());
                statement.setInt(8, this.getUnityEquivalent().getIdUnityEquivalent());

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

    //Mettre a jour une entree
    public void update(Connection connection) {
        String query = "UPDATE enter SET date_in=?, id_article=?, quantity=?, unitary_cost=?, id_store=?, quantity_rest=?, status=?, id_unity_equivalent=? WHERE id_in=?";
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
                statement.setDate(1, Date.valueOf(this.getDateIn()));
                statement.setInt(2, this.getArticle().getIdArticle());
                statement.setDouble(3, this.getQuantity());
                statement.setDouble(4, this.getUnitary_cost());
                statement.setInt(5, this.getStore().getIdStore());
                statement.setDouble(6, this.getQuantityRest());
                statement.setInt(7, this.getStatus());
                statement.setInt(8, this.getUnityEquivalent().getIdUnityEquivalent());
                statement.setInt(9, this.getIdIn());

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
        
    //Reecuperer une entree par la date entree
    public static In getByDate(Connection connection, Date dateIn) {
        In in = new In();
        try {
        String query = "SELECT * FROM enter WHERE date_in= '%s'";
        
        query = String.format(query, dateIn);

        
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
                in.setIdIn(resultset.getInt("id_in"));
                in.setDateIn(DateManagement.dateToLocalDate(resultset.getDate("date_in")));
                in.setArticle(Article.getById(null, resultset.getInt("id_article")));
                in.setQuantity(resultset.getDouble("quantity"));
                in.setUnitary_cost(resultset.getDouble("unitary_cost"));
                in.setStore(Store.getById(null, resultset.getInt("id_store")));
                in.setQuantityRest(resultset.getDouble("quantity_rest"));
                in.setStatus(resultset.getInt("status"));
                in.setUnityEquivalent(UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")));
            }

            if (closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return in;
    }

    //Recuperer une entree par son id
    public static In getById(Connection connection, int id) {
        In in = new In();
        try {
        String query = "SELECT * FROM enter WHERE id_in= %d";
        
        query = String.format(query, id);
        
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
                in.setIdIn(resultset.getInt("id_in"));
                in.setDateIn(DateManagement.dateToLocalDate(resultset.getDate("date_in")));
                in.setArticle(Article.getById(null, resultset.getInt("id_article")));
                in.setQuantity(resultset.getDouble("quantity"));
                in.setUnitary_cost(resultset.getDouble("unitary_cost"));
                in.setStore(Store.getById(null, resultset.getInt("id_store")));
                in.setQuantityRest(resultset.getDouble("quantity_rest"));
                in.setStatus(resultset.getInt("status"));
                in.setUnityEquivalent(UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return in;
    }
     
    //Avoir toutes les entrees
    public static List<In> getAll(Connection connection) {
        List<In> ins = new ArrayList<>();
        String query = "SELECT * FROM enter ORDER BY date_in DESC";

        Statement statement = null;
        ResultSet resultset = null;

        try {
            boolean closeable = false;
            if (connection == null) {
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
                closeable = true;
            }
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            while (resultset.next()) {
              ins.add(new In(resultset.getInt("id_in"),DateManagement.dateToLocalDate(resultset.getDate("date_in")), Article.getById(null, resultset.getInt("id_article")), resultset.getDouble("quantity"), resultset.getDouble("unitary_cost"),Store.getById(null, resultset.getInt("id_store")), resultset.getDouble("quantity_rest"), resultset.getInt("status"), UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent"))));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ins;
    }
    
    //Ordonner une entree par date d'un article dans un magasin 
    public static List<In> orderByDate(Connection connection, String action, int idArticle, int idMagasin) {
        List<In> ins = new ArrayList<>();
        String query = "SELECT * FROM enter WHERE id_article=%d AND id_store=%d AND status >= 10 ORDER BY date_in %s";
        query = String.format(query, idArticle, idMagasin, action);

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
              ins.add(new In(resultset.getInt("id_in"),DateManagement.dateToLocalDate(resultset.getDate("date_in")), Article.getById(null, resultset.getInt("id_article")), resultset.getDouble("quantity"), resultset.getDouble("unitary_cost"),Store.getById(null, resultset.getInt("id_store")), resultset.getDouble("quantity_rest"), resultset.getInt("status"), UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent"))));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ins;
    }
    
    //Filtrer une entree d'un article dans un magasin par date entree
    public static List<In> getFilterByDate(Connection connection, int idArticle, int idStore, Date date, String comparaison) {
        List<In> ins = new ArrayList<>();
        String query = "SELECT * FROM enter WHERE id_article=%d AND id_store=%d And date_in %s '%s' AND status>=10";
        query = String.format(query, idArticle, idStore, comparaison, date);
        System.out.println("Query : "+query);

        Statement statement = null;
        ResultSet resultset = null;
        boolean closeable = false;

        try {
            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            while (resultset.next()) {
              ins.add(new In(resultset.getInt("id_in"),DateManagement.dateToLocalDate(resultset.getDate("date_in")), Article.getById(null, resultset.getInt("id_article")), resultset.getDouble("quantity"), resultset.getDouble("unitary_cost"),Store.getById(null, resultset.getInt("id_store")), resultset.getDouble("quantity_rest"), resultset.getInt("status"), UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent"))));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ins;
    }
     
    //Somme des quantites entrees selon la liste donnee en argument
    public static Double calculSumQuantiteIn(List<In> listIn) throws Exception {
        Double sum = 0.0;
        for(int i = 0; i < listIn.size(); i++) {
            sum += listIn.get(i).getQuantity();
        }
        
        return sum;
    }
    
    //Somme des montants des entrees selon la liste donnee en parametre
    public static Double calculSumMontantIn(List<In> listIn) throws Exception {
        Double sum = 0.0;
        for(int i = 0; i < listIn.size(); i++) {
            sum += listIn.get(i).getQuantity() * listIn.get(i).getUnitary_cost();
        }
        
        return sum;
    }
    
    //Somme des entrees d'un article dans un magasin a une date entree
    public static Double getSumIn(Connection connection, Date dateOut, int idArticle, int idStore) {
        Double sumOut = 0.0;
        try {
        String query = "select sum(quantity) as sum_quantity from enter where date_in <= '%s' and id_article=%d and id_store=%d and status >= 10";
        
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
}
