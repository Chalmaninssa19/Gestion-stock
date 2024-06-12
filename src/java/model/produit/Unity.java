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
public class Unity {
    private Integer idUnity;
    private String nom;
    
///Getters et setters
    public Integer getIdUnity() {
        return idUnity;
    }
    public void setIdUnity(Integer idUnity) {
        this.idUnity = idUnity;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    
///Constructors
    public Unity() {
    }

    public Unity(Integer idUnity, String nom) {
        this.idUnity = idUnity;
        this.nom = nom;
    }

///Fonctions
    public static Unity getById(Connection connection, int id) {
        Unity unity = new Unity();
        try {
            String query = "SELECT * FROM unity WHERE id_unity= %d";
            query = String.format(query, id);
            boolean closeable = false;

            if (connection == null) {
                closeable = true;
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }
            
            Statement statement = null;
            ResultSet resultset = null;

            connection = GConnection.getSimpleConnection();
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                unity.setIdUnity(resultset.getInt("id_unity"));
                unity.setNom(resultset.getString("designation"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return unity;
    }

    public static List<Unity> getAll(Connection connection) {
        List<Unity> unitys = new ArrayList<>();
        String query = "SELECT * FROM unity";

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
                unitys.add(new Unity(resultset.getInt("id_unity"), resultset.getString("designation")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return unitys;
    }
}
