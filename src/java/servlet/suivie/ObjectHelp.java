/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.suivie;

import connection.GConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.produit.Unity;

/**
 *
 * @author Chalman
 */
public class ObjectHelp {
    private Integer idArticle;
    private Integer idStore;
    
///Getters et setters

    public Integer getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public Integer getIdStore() {
        return idStore;
    }

    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }
    
///Constructors
    public ObjectHelp(Integer idArticle, Integer idStore) {
        this.idArticle = idArticle;
        this.idStore = idStore;
    }
    
///Fonctions
     public static List<ObjectHelp> getAll(Connection connection) {
        List<ObjectHelp> objetsHelp = new ArrayList<>();
        String query = "SELECT id_article, id_store FROM enter WHERE date_in <= '2023-11-05' GROUP BY id_store,id_article";

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
                objetsHelp.add(new ObjectHelp(resultset.getInt("id_article"), resultset.getInt("id_store")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return objetsHelp;
    }
}
