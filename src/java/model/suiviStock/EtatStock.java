/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.suiviStock;

import connection.GConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chalman
 */
public class EtatStock {
    private LocalDate datedebut;
    private LocalDate dateFin;
    private List<Stock> stocks;
    private Double montantTotal;
    
///Getters et setters
    public LocalDate getDatedebut() {
        return datedebut;
    }
    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Double getMontantTotal() {
       return this.montantTotal;
    }
    public void setMontantTotal() {
        Double sum = 0.0;
        for(int i = 0; i < this.getStocks().size(); i++) {
            sum += this.getStocks().get(i).getStockValue();
        }
        this.montantTotal = sum;
    }
    
///Constructors
    public EtatStock() {
    }

    public EtatStock(LocalDate datedebut, LocalDate dateFin, List<Stock> stocks) {
        this.datedebut = datedebut;
        this.dateFin = dateFin;
        this.stocks = stocks;
        this.stocks = stocks;
        this.setMontantTotal();
    }
    
///Fonctions
    //Liste des entrees groupes par article par rapport a une date et magasin
    public static List<Integer> groupByArticleIn(Connection connection, Date date, int idStore, String comparaison) throws Exception {
        List<Integer> allIdArticleIn = new ArrayList<>();
        String query = "SELECT id_article FROM enter WHERE date_in %s '%s' AND id_store=%d AND status>=10 GROUP BY id_article";
        query = String.format(query, comparaison, date, idStore);

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
                allIdArticleIn.add(resultset.getInt("id_article"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return allIdArticleIn;
    }
    
    //Grouper une entree par magasin par rapport a article et date entree
    public static List<Integer> groupByStoreIn(Connection connection, Date date, int idArticle, String comparaison) throws Exception {
        List<Integer> allIdArticleIn = new ArrayList<>();
        String query = "SELECT id_store FROM enter WHERE date_in %s '%s' AND id_article=%d AND status>=10 GROUP BY id_store";
        query = String.format(query, comparaison, date, idArticle);

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
                allIdArticleIn.add(resultset.getInt("id_store"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return allIdArticleIn;
    }
}
