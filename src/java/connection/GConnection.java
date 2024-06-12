package connection;
import java.io.*;
import java.sql.*;

public class GConnection {
    public static String type_base="postgres";
    public static String database="gestion_stock";
    public static String owner="postgres";
    public static String password="root";
    public static String port="5432";
    public static String host = "localhost";
    public static String driver="org.postgresql.Driver";
    public static String connection;
    
    public static Connection getSimpleConnection()throws Exception{
        Connection con=null;
    	if (con == null) {
           try{
               Class.forName(driver);    
               con = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+database,owner,password);
          }
          catch(Exception e){
               e.printStackTrace();
               throw new Exception("il y a un probleme avec la connection, verifier les donnees de connection");
          } 
       }
       return con;
   }
   public static void main(String[]args){
         try{
              Connection con=getSimpleConnection();
              //System.out.println("connection reussie");
              con.close();
         }catch(Exception e){
              e.printStackTrace();
         }
   }
}
