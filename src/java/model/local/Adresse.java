/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.local;

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
public class Adresse {
    private Integer idAdresse;
    private String adresse;
    
///Getters et setters
    public Integer getIdAdresse() {
        return idAdresse;
    }
    public void setIdAdresse(Integer idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getAdresse() {
        return adresse;
    }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
///Constructors
    public Adresse() {
    }

    public Adresse(Integer idAdresse, String adresse) {
        this.idAdresse = idAdresse;
        this.adresse = adresse;
    }
   
///Fonction
     public static Adresse getById(Connection connection, int id) {
        Adresse adresse = new Adresse();
        try {
            String query = "SELECT * FROM adress WHERE id_adress= %d";
            query = String.format(query, id);

            Statement statement = null;
            ResultSet resultset = null;

            if (connection == null) {
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
                
            }
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if (resultset.next()) {
                adresse.setIdAdresse(resultset.getInt("id_adress"));
                adresse.setAdresse(resultset.getString("designation"));
            }

            resultset.close();
            statement.close();
            connection.close();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return adresse;
    }

    public static List<Adresse> getAll(Connection connection) {
        List<Adresse> adresses = new ArrayList<>();
        String query = "SELECT * FROM adress";

        Statement statement = null;
        ResultSet resultset = null;

        try {
            if (connection == null) {
                connection = GConnection.getSimpleConnection();
                connection.setAutoCommit(false);
            }
            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            while (resultset.next()) {
                adresses.add(new Adresse(resultset.getInt("id_adress"), resultset.getString("designation")));
            }

            resultset.close();
            statement.close();
           connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return adresses;
    }
}
