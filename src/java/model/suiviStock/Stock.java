/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.suiviStock;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import model.local.Store;
import model.mouvement.In;
import model.mouvement.Out;
import model.produit.Article;

/**
 *
 * @author Chalman
 */
public class Stock {
    private String codeArticle;
    private String article;
    private String unity;
    private String store;
    private Double stockInitial;
    private Double in;
    private Double out;
    private Double stockFinal;
    private Double prixUnitaire;
    private Double stockValue;
    
///Getters et setters
    public String getCodeArticle() {
        return codeArticle;
    }
    public void setCodeArticle(String codeArticle) {
        this.codeArticle = codeArticle;
    }

    public String getArticle() {
        return article;
    }
    public void setArticle(String article) {
        this.article = article;
    }

    public String getUnity() {
        return unity;
    }
    public void setUnity(String unity) {
        this.unity = unity;
    }

    public String getStore() {
        return store;
    }
    public void setStore(String store) {
        this.store = store;
    }

    public Double getStockInitial() {
        return stockInitial;
    }
    public void setStockInitial(Double stockInitial) {
        this.stockInitial = stockInitial;
    }

    public Double getIn() {
        return in;
    }
    public void setIn(Double in) {
        this.in = in;
    }

    public Double getOut() {
        return out;
    }
    public void setOut(Double out) {
        this.out = out;
    }

    public Double getStockFinal() {
        return stockFinal;
    }
    public void setStockFinal(Double stockFinal) {
        this.stockFinal = stockFinal;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }
    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Double getStockValue() {
        return stockValue;
    }
    public void setStockValue(Double stockValue) {
        this.stockValue = stockValue;
    }
    
///Constructors

    public Stock() {
    }

    public Stock(String codeArticle, String article, String unity, String store, Double stockInitial, Double in, Double out, Double stockFinal, Double prixUnitaire, Double stockValue) {
        this.codeArticle = codeArticle;
        this.article = article;
        this.unity = unity;
        this.store = store;
        this.stockInitial = stockInitial;
        this.in = in;
        this.out = out;
        this.stockFinal = stockFinal;
        this.prixUnitaire = prixUnitaire;
        this.stockValue = stockValue;
    }
    
///Fonctions
    //Stock initial du stock par rapport aux entrees et sorties en parametre
    public static Double getStockInitial(List<In> insBeforeDebut, List<Out> outsBeforeDebut) throws Exception {
        Double sumQteIn = In.calculSumQuantiteIn(insBeforeDebut);
        Double sumQteOut = Out.calculSumQuantiteOut(outsBeforeDebut);
        System.out.println("Somme entree : "+sumQteOut);
        
        return sumQteIn - sumQteOut;
    }
    
    //Quantite entree
    public static Double getQuantityIn(List<In> insBeforeFin) throws Exception {
        return In.calculSumQuantiteIn(insBeforeFin);
    }
    
    //Quantite sortie
    public static Double getQuantityOut(List<Out> outsBeforeFin) throws Exception {
        return Out.calculSumQuantiteOut(outsBeforeFin);
    }
    
    //Stock final
    public static Double getStockFinal(Double stockInitial, List<In> insBeforeFin, List<Out> outsBeforeFin) throws Exception {
        return (Stock.getQuantityIn(insBeforeFin) - Stock.getQuantityOut(outsBeforeFin));
    }
    
    //Valeur d'un stock
    public static Double getStockValue(List<In> insBeforeFin, List<Out> outsBeforeFin) throws Exception {
        return In.calculSumMontantIn(insBeforeFin) - Out.calculSumMontantOut(outsBeforeFin);
    }
    
    //Valeur du prix unitaire
    public static Double getUnitaryPrice(Double stockValue, Double stockFinal) {
        return stockValue / stockFinal;
    }
    
    //Avoir le stock
    public static Stock getStock(Connection connection, String dateDebut, String dateFin, Article article, Store store) throws Exception {
            List<In> insBeforeDebut = In.getFilterByDate(null, article.getIdArticle(), store.getIdStore(), Date.valueOf(dateDebut), "<=");
            List<In> insBeforeFin = In.getFilterByDate(null, article.getIdArticle(), store.getIdStore(), Date.valueOf(dateFin), "<=");
            List<Out> outsBeforeDebut = Out.getFilterByDate(null, article.getIdArticle(), store.getIdStore(), Date.valueOf(dateDebut), "<=");
            List<Out> outsBeforeFin = Out.getFilterByDate(null, article.getIdArticle(), store.getIdStore(), Date.valueOf(dateFin), "<=");
            
            Double stockInitial = Stock.getStockInitial(insBeforeDebut, outsBeforeDebut);
            Double quantityIn = Stock.getQuantityIn(insBeforeFin);
            Double quantityOut = Stock.getQuantityOut(outsBeforeFin);
            Double stockFinal = Stock.getStockFinal(stockInitial, insBeforeFin, outsBeforeFin);
            Double stockValue = Stock.getStockValue(insBeforeFin, outsBeforeFin);
            Double unitaryPrice = Stock.getUnitaryPrice(stockValue, stockFinal);
            if(unitaryPrice.isNaN()) {
                unitaryPrice = 0.0;
            }
            
        return new Stock(article.getCode(), article.getName(), article.getUnity().getNom(), store.getName(), stockInitial, quantityIn, quantityOut, stockFinal, unitaryPrice, stockValue);
    }
     
    //Couleur d'un etat
    public String getColorStatus() {
        if(this.getStockFinal() <= 10.0) {
             return "red";
        }
        else if(this.getStockFinal() <= 50.0) {
            return "rgb(254, 215, 19)";
        }
        
        return "green";
     }
}
