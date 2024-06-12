/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.produit;

import connection.GConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chalman
 */
public class Article {
    private Integer idArticle;
    private CategorieArticle categorieArticle;
    private String name;
    private Double unitPrice;
    private Unity unity;
    private Integer typeMvt;
    private String code;
    private Integer status;
    private UnityEquivalent unityEquivalent;
    private Double quantityEquivalent;
    
///Getters et setters
    public Integer getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(Integer idArticle) {
        this.idArticle = idArticle;
    }

    public CategorieArticle getCategorieArticle() {
        return categorieArticle;
    }
    public void setCategorieArticle(CategorieArticle categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setuntitPrice(Double untitPrice) {
        this.unitPrice = untitPrice;
    }

    public Unity getUnity() {
        return unity;
    }
    public void setUnity(Unity unity) {
        this.unity = unity;
    }

    public Integer getTypeMvt() {
        return typeMvt;
    }
    public void setTypeMvt(Integer typeMvt) {
        this.typeMvt = typeMvt;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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

    public Double getQuantityEquivalent() {
        return quantityEquivalent;
    }
    public void setQuantityEquivalent(Double quantityEquivalent) {
        this.quantityEquivalent = quantityEquivalent;
    }
    
///Constructors

    public Article() {
    }

    public Article(Integer idArticle, CategorieArticle categorieArticle, String name, Double unitPrice, Unity unity, Integer typeMvt, String code, Integer status, UnityEquivalent unityequivalent, Double quantityEquivalent) {
        this.idArticle = idArticle;
        this.categorieArticle = categorieArticle;
        this.name = name;
        this.unitPrice = unitPrice;
        this.unity = unity;
        this.typeMvt = typeMvt;
        this.code = code;
        this.status = status;
        this.unityEquivalent = unityequivalent;
        this.quantityEquivalent = quantityEquivalent;
    }

    

///fonctions
    public void save(Connection connection) {
        String query = "INSERT INTO article ( id_categorie_article,designation, id_unity, unit_price, type_mvt, status, code, id_unity_equivalent, quantity_equivalent) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                statement.setInt(1, this.getCategorieArticle().getIdCategorie());
                statement.setString(2, this.getName());
                statement.setInt(3, this.getUnity().getIdUnity());
                statement.setDouble(4, this.getUnitPrice());
                statement.setInt(5, this.getTypeMvt());
                statement.setInt(6, this.getStatus());
                statement.setString(7, this.getCode());
                statement.setInt(8, this.getUnityEquivalent().getIdUnityEquivalent());
                statement.setDouble(9, this.getQuantityEquivalent());

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

    public static Article getById(Connection connection, int id) {
        Article article = new Article();
        try {
            String query = "SELECT * FROM article WHERE id_article= %d";

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
                article.setIdArticle(resultset.getInt("id_article"));
                article.setCategorieArticle(CategorieArticle.getById(null, resultset.getInt("id_categorie_article")));
                article.setUnity(Unity.getById(null, resultset.getInt("id_unity")));
                article.setCode(resultset.getString("code"));
                article.setName(resultset.getString("designation"));
                article.setTypeMvt(resultset.getInt("type_mvt"));
                article.setuntitPrice(resultset.getDouble("unit_price"));
                article.setStatus(resultset.getInt("status"));
                article.setUnityEquivalent(UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")));
                article.setQuantityEquivalent(resultset.getDouble("quantity_equivalent"));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return article;
    }

    public static List<Article> getAll(Connection connection) {
        List<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM article";

        
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
              articles.add(new Article(resultset.getInt("id_article"), CategorieArticle.getById(null, resultset.getInt("id_categorie_article")),resultset.getString("designation"), resultset.getDouble("unit_price"), Unity.getById(null, resultset.getInt("id_unity")), resultset.getInt("type_mvt"), resultset.getString("code"), resultset.getInt("status"), UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")), resultset.getDouble("quantity_equivalent")));
            }

            if(closeable) {
                resultset.close();
                statement.close();
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return articles;
    }
    
    public static Article getByArticleUnityEquivalent(Connection connection, int idArticle, int idUnityEquivalent) {
        Article article = new Article();
        try {
            String query = "SELECT * FROM article WHERE id_article= %d AND id_unity_equivalent=%d";

            query = String.format(query, idArticle, idUnityEquivalent);

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
                article.setIdArticle(resultset.getInt("id_article"));
                article.setCategorieArticle(CategorieArticle.getById(null, resultset.getInt("id_categorie_article")));
                article.setUnity(Unity.getById(null, resultset.getInt("id_unity")));
                article.setCode(resultset.getString("code"));
                article.setName(resultset.getString("designation"));
                article.setTypeMvt(resultset.getInt("type_mvt"));
                article.setuntitPrice(resultset.getDouble("unit_price"));
                article.setStatus(resultset.getInt("status"));
                article.setUnityEquivalent(UnityEquivalent.getById(null, resultset.getInt("id_unity_equivalent")));
                article.setQuantityEquivalent(resultset.getDouble("quantity_equivalent"));
                
                return article;
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
}
