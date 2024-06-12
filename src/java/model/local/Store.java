/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.local;

import connection.GConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utilitaire.DateManagement;

/**
 *
 * @author Chalman
 */
public class Store {
    private Integer idStore;
    private LocalDate dateCreation;
    private String name;
    private Integer status;
    private Adresse adresse;
    private Double surface;
    
///Getters et setters
    public Integer getIdStore() {
        return idStore;
    }
    public void setIdStore(Integer idStore) {
        this.idStore = idStore;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Adresse getAdresse() {
        return adresse;
    }
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Double getSurface() {
        return surface;
    }
    public void setSurface(Double surface) {
        this.surface = surface;
    }
    
///Constructors
    public Store() {
    }

    public Store(Integer idStore, LocalDate dateCreation, String name, Integer status, Adresse adresse, Double surface) {
        this.idStore = idStore;
        this.dateCreation = dateCreation;
        this.name = name;
        this.status = status;
        this.adresse = adresse;
        this.surface = surface;
    }
    
///Fonctions
    public void save(Connection connection) {
        String query = "INSERT INTO store ( name, id_adress, surface, date_creation, status) VALUES ( ?, ?, ?, ?, ?)";
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
                statement.setString(1, this.getName());
                statement.setInt(2, this.getAdresse().getIdAdresse());
                statement.setDouble(3, this.getSurface());
                statement.setDate(4, Date.valueOf(this.getDateCreation()));
                statement.setInt(5, this.getStatus());

                statement.executeUpdate();

                statement.close();

                if (closeable) {
                    connection.commit();
                    connection.close();
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Store getById(Connection connection, int id) {
        Store store = new Store();
        try {
            String query = "SELECT * FROM store WHERE id_store= %d";

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
                store.setIdStore(resultset.getInt("id_store"));
                store.setAdresse(Adresse.getById(null, resultset.getInt("id_adress")));
                store.setName(resultset.getString("name"));
                store.setSurface(resultset.getDouble("surface"));
                LocalDate dateCreation = DateManagement.dateToLocalDate(resultset.getDate("date_creation"));
                store.setDateCreation(dateCreation);
                store.setStatus(resultset.getInt("status"));
            }

            resultset.close();
            statement.close();
            if (closeable) {
                    connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return store;
    }

    public static List<Store> getAll(Connection connection) {
        List<Store> stores = new ArrayList<>();
        String query = "SELECT * FROM store";

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
              stores.add(new Store(resultset.getInt("id_store"), DateManagement.dateToLocalDate(resultset.getDate("date_creation")), resultset.getString("name"), resultset.getInt("status"), Adresse.getById(null, resultset.getInt("id_adress")), resultset.getDouble("surface")));
            }

            resultset.close();
            statement.close();
            if (closeable) {
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return stores;
    }
}
