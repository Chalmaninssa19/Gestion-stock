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
public class UnityEquivalent {
    private Integer idUnityEquivalent;
    private String designation;
    
///Getters et setters
    public Integer getIdUnityEquivalent() {
        return idUnityEquivalent;
    }
    public void setIdUnityEquivalent(Integer idUnityEquivalent) {
        this.idUnityEquivalent = idUnityEquivalent;
    }

    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
///Constructors
    public UnityEquivalent() {
    }

    public UnityEquivalent(Integer idUnityEquivalent, String designation) {
        this.idUnityEquivalent = idUnityEquivalent;
        this.designation = designation;
    }
    
///Fonctions
    public static UnityEquivalent getById(Connection connection, int id) {
        UnityEquivalent unityEquivalent = new UnityEquivalent();
        try {
            String query = "SELECT * FROM unity_equivalent WHERE id_unity_equivalent= %d";

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
                unityEquivalent.setIdUnityEquivalent(resultset.getInt("id_unity_equivalent"));
                unityEquivalent.setDesignation(resultset.getString("designation"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return unityEquivalent;
    }

    public static List<UnityEquivalent> getAll(Connection connection) {
        List<UnityEquivalent> unityEquivalents = new ArrayList<>();
        String query = "SELECT * FROM unity_equivalent";

        
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
              unityEquivalents.add(new UnityEquivalent(resultset.getInt("id_unity_equivalent"), resultset.getString("designation")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return unityEquivalents;
    }
}
