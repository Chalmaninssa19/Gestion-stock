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
import model.suiviStock.Stock;
import utilitaire.DateManagement;

/**
 *
 * @author Chalman
 */
public class Out {
    private Integer idOut;
    private In in;
    private RequestOut requestOut;
    private Double quantity;
    private LocalDate dateValidationOut;
    
///Getters et setters
    public Integer getIdOut() {
        return idOut;
    }
    public void setIdOut(Integer idOut) {
        this.idOut = idOut;
    }
    
    public In getIn() {
        return in;
    }
    public void setIn(In in) {
        this.in = in;
    }

    public RequestOut getRequestOut() {
        return requestOut;
    }

    public void setRequestOut(RequestOut requestOut) {
        this.requestOut = requestOut;
    }
    
    
    public Double getQuantity() {
        return quantity;
    }
    public void setQuantity(Double quantity) throws Exception {
        if(quantity < 0) {
            throw new Exception("La quantite doit etre un nombre positive");
        }
      /*
        Double totalQuantityOutValidate = Out.getSumOutValidate(null, Date.valueOf(this.getDateValidationOut()), this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        Double totalQuantityIn = In.getSumIn(null, Date.valueOf(this.getDateValidationOut()), this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        
        if((totalQuantityIn - totalQuantityOutValidate) - quantity < 0) {
            throw new Exception("Votre stock est insuffisant, penser a entrer");
        }*/
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
        
        Double totalQuantityOutValidate = Out.getSumOutValidate(null, Date.valueOf(this.getDateValidationOut()), this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        Double totalQuantityIn = In.getSumIn(null, Date.valueOf(this.getDateValidationOut()), this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        //System.out.println("Total quantite sortie valide= "+totalQuantityOutValidate);
        //System.out.println("Total quantite entree valide= "+totalQuantityIn);
        //System.out.println("Quantite sortie = "+quantite);
        
        if((totalQuantityIn - totalQuantityOutValidate) - quantite < 0) {
            throw new Exception("Votre stock est insuffisant a cette date");
        }
        
        this.setQuantity(quantite);
    }
    
    public LocalDate getDateValidationOut() {
        return dateValidationOut;
    }
    public void setDateValidationOut(LocalDate dateValidationOut) {
        this.dateValidationOut = dateValidationOut;
    }
    public void setDateValidationOut(String dateValidationOut) throws Exception {
        if(dateValidationOut.trim().equals("") || dateValidationOut == null) {
            throw new Exception("La date sortie ne doit pas etre null"); 
        }
        LocalDate dateOutParsed = LocalDate.parse(dateValidationOut);
        Out outed = Out.getLast(null, this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        LocalDate dateValide = LocalDate.parse(dateValidationOut);
         if(outed != null && outed.getDateValidationOut().isAfter(dateValide)) {
            throw new Exception("Date invalide : Doit etre ulterieure de la date du dernier mouvement");
         }

        this.setDateValidationOut(dateOutParsed);
    }
    
    public Double getQuantityReel() {
        return this.getQuantity() / this.getRequestOut().getArticle().getQuantityEquivalent();
    }
    public Double getPrixReel() {
        return this.getIn().getUnitary_cost() * this.getQuantity();
    } 
///Constructors
    public Out() {
    }

    public Out(Integer idOut, In in, RequestOut requestOut, Double quantity, LocalDate dateValidationOut) {
        this.idOut = idOut;
        this.in = in;
        this.requestOut = requestOut;
        this.quantity = quantity;
        this.dateValidationOut = dateValidationOut;
    }

    public Out(In in, RequestOut requestOut, Double quantity, LocalDate dateValidationOut) {
        this.in = in;
        this.requestOut = requestOut;
        this.quantity = quantity;
        this.dateValidationOut = dateValidationOut;
    }

    public Out(String dateValidation, Double quantity, RequestOut requestOut) throws Exception {
        try {
            this.setRequestOut(requestOut);
            this.setDateValidationOut(dateValidation);
            this.setQuantity(quantity.toString());
        } catch(Exception e) {
            throw e;
        }
    }
///Fonctions
    //Sauvegarder une sortie dans la base
    public void save(Connection connection) {
        String query = "INSERT INTO out ( date_validation, quantity, id_in, id_request_out) VALUES ( ?, ?, ?, ?)";
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
                statement.setDate(1, Date.valueOf(this.getDateValidationOut()));
                statement.setDouble(2, this.getQuantity());
                statement.setInt(3, this.getIn().getIdIn());
                statement.setInt(4, this.getRequestOut().getIdRequestOut());

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
     
    //Sauvgarder la decomposition de sortie
    public static void saveDecomposition(List<Out> outted, RequestOut requestOut, String date) throws Exception {
        for(int i = 0; i < outted.size(); i++) {
            outted.get(i).save(null);
        }
        requestOut.setStatus(10);
        requestOut.setDateRequestOut(date);
        requestOut.update(null);
    }

    //Recuperer la derniere sortie valider
    public static Out getLast(Connection connection, int idArticle, int idStore) {
        Out out = new Out();
        try {
        String query = "SELECT * FROM v_out_request_out WHERE id_article=%d AND id_store=%d ORDER BY id_out DESC LIMIT 1";
        
        query = String.format(query, idArticle, idStore);
        
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
                out.setDateValidationOut(DateManagement.dateToLocalDate(resultset.getDate("date_validation")));
                out.setQuantity(resultset.getDouble("quantity_outted"));
                out.setIn(In.getById(null, resultset.getInt("id_in")));
                out.setRequestOut(RequestOut.getById(null, resultset.getInt("id_request_out")));
                
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
   
    //Avoir les listes de sorties valider filtrer par date
    public static List<Out> getFilterByDate(Connection connection, int idArticle, int idStore, Date date, String comparaison) {
        List<Out> outs = new ArrayList<>();
        String query = "SELECT * FROM v_out_request_out  WHERE id_article=%d AND id_store=%d And date_validation %s '%s'";
        query = String.format(query, idArticle, idStore, comparaison, date);

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
              outs.add(new Out(resultset.getInt("id_out"), In.getById(null, resultset.getInt("id_in")), RequestOut.getById(null, resultset.getInt("id_request_out")), resultset.getDouble("quantity_outted"), DateManagement.dateToLocalDate(resultset.getDate("date_validation"))));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outs;
    }
      
    //Decomposition d'une demande de sortie
    public List<Out> decomposer(Connection connection) throws Exception {
        String action = "";
        Double quantityOutting = this.getQuantity();
        Double quantityHelp = -1.0;
        
        if(this.getRequestOut().getArticle().getTypeMvt() == 1) {   //FIFO
            action = "ASC";
        }
        else {
            action = "DESC";    //LIFO
        }
        
        List<In> ins = In.orderByDate(connection, action, this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore());
        List<Out> outDecompose = new ArrayList<>();
        for(int i = 0; i < ins.size(); i++) {
            Double sumOutValidate = Out.getSumOutValidateForEnter(null, Date.valueOf(this.getDateValidationOut()), this.getRequestOut().getArticle().getIdArticle(), this.getRequestOut().getStore().getIdStore(), ins.get(i).getIdIn());
            if(ins.get(i).getQuantity() - sumOutValidate > 0) {
                Double quantityRest = ins.get(i).getQuantity() - sumOutValidate;
                if(quantityRest - this.getQuantity() < 0) {
                    Double quantityTemp = this.getQuantity() - quantityRest;
                    outDecompose.add(new Out(ins.get(i), this.getRequestOut(), quantityRest, this.getDateValidationOut()));
                    this.setQuantity(quantityTemp);
                } else {
                   outDecompose.add(new Out(ins.get(i), this.getRequestOut(), this.getQuantity(), this.getDateValidationOut()));
                   break;
                }
            }
        }
        
        return outDecompose;
    }
    
    //Affichage d'une liste d'entree
    public static void displayEnter(List<In> enters) {
        for(int i = 0; i < enters.size(); i++) {
            System.out.println("Entree "+enters.get(i).getIdIn()+", date : "+enters.get(i).getDateIn()+", Quantite : "+enters.get(i).getQuantity());
        }
    }
   
    //Calcul de la somme quantite sortie
    public static Double calculSumQuantiteOut(List<Out> listOut) throws Exception {
        Double sum = 0.0;
        for(int i = 0; i < listOut.size(); i++) {
            sum += listOut.get(i).getQuantity();
        }
 
        return sum;
    }
    
    //Calcul de la somme montant sortie
    public static Double calculSumMontantOut(List<Out> listOut) throws Exception {
        Double sum = 0.0;
        
        for(int i = 0; i < listOut.size(); i++) {
            sum += listOut.get(i).getQuantity() * listOut.get(i).getIn().getUnitary_cost();
        }
        
        return sum;
    }
    
    //Avoir le total des mouvements par rapport au quantite
    public static Double getTotalMvt(List<In> insBefore, List<Out> outsBefore) throws Exception {
        Double sumInsBefore = Stock.getQuantityIn(insBefore);
        Double sumOutsBefore = Stock.getQuantityOut(outsBefore);
        Double sumMvt = sumInsBefore - sumOutsBefore;
        if(sumMvt < 0) {
            throw new Exception("Erreur de donnee sortie: La somme des quantites des mouvements effectues avant la date sortie ne doit pas etre negative");
        }
        return sumMvt;
    }
    
    //Recuperer la somme quantite des sorties valide d'une article dans un magasin
    public static Double getSumOutValidate(Connection connection, Date dateOut, int idArticle, int idStore) {
        Double sumOut = 0.0;
        try {
        String query = "select sum(quantity_outted) as sum_quantity from v_out_request_out where date_validation <= '%s' and id_article=%d and id_store=%d AND status >= 10";
        
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
    
    //Somme des sorties d'une entree
     public static Double getSumOutValidateForEnter(Connection connection, Date dateValidation, int idArticle, int idStore, int idIn) {
        Double sumOut = 0.0;
        try {
        String query = "select sum(quantity_outted) as sum_quantity from v_out_request_out where date_validation <= '%s' and id_article=%d and id_store=%d AND id_in=%d";
        
        query = String.format(query, dateValidation, idArticle, idStore, idIn);
        System.out.println("Requete sum ou : "+query);
        
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
    
  /*  public void update(Connection connection) {
        String query = "UPDATE out SET date_out=?, id_article=?, quantity=?, id_store=?, id_in=?, status=? WHERE id_out=?";
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
                statement.setDate(1, Date.valueOf(this.getDateOut()));
                statement.setInt(2, this.getArticle().getIdArticle());
                statement.setDouble(3, this.getQuantity());
                statement.setInt(4, this.getStore().getIdStore());
                statement.setInt(5, this.getIn().getIdIn());
                statement.setInt(6, this.getStatus());
                statement.setInt(7, this.getIdOut());

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
    public static void main(String [] args) throws Exception {
        try {
          Out out = new Out("2023-11-01", 5.0, RequestOut.getById(null, 5) );
          out.setDateValidationOut("2023-10-11");
          System.out.println("Effectue");
        } catch(Exception e) {
            e.printStackTrace();
        }
    } */
}
