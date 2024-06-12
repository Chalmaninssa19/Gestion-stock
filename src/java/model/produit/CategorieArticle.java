/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.produit;

import connection.GConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chalman
 */
public class CategorieArticle {
    private Integer idCategorie;
    private String code;
    private String designation;
    
///Getters et setters
    public Integer getIdCategorie() {
        return idCategorie;
    }
    public void setIdCategorie(Integer idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
///Constructors
    public CategorieArticle() {
    }

    public CategorieArticle(Integer idCategorie, String code, String designation) {
        this.idCategorie = idCategorie;
        this.code = code;
        this.designation = designation;
    }
   
///Fonctions
     public static CategorieArticle getById(Connection connection, int id) {
        CategorieArticle ca = new CategorieArticle();
        try {
            String query = "SELECT * FROM categorie_article WHERE id_categorie_article= %d";
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
                ca.setCode(resultset.getString("code"));
                ca.setIdCategorie(resultset.getInt("id_categorie_article"));
                ca.setDesignation(resultset.getString("designation"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return ca;
    }

    public static List<CategorieArticle> getAll(Connection connection) {
        List<CategorieArticle> cas = new ArrayList<>();
        String query = "SELECT * FROM categoriea_article";

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
                cas.add(new CategorieArticle(resultset.getInt("id_categorie_article"), resultset.getString("code"), resultset.getString("designation")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return cas;
    }
}
