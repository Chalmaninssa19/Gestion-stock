/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Chalman
 */
public class Util {
    public static String formattedNumber( Double number ) {
        // Créer un objet DecimalFormat pour formater le nombre
        DecimalFormat decimalFormat = new DecimalFormat("#.######");

        // Formater le nombre en utilisant le modèle spécifié
        String formattedNumber = decimalFormat.format(number);
        
        return formattedNumber;
    }
    
    public static String formatMonetaire(Double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        // Formater le nombre en utilisant le modèle spécifié
        String formattedNumber = decimalFormat.format(number);
        
        return formattedNumber;
    }
}
