/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Chalman
 */
public class DateManagement {
    public static LocalDate dateToLocalDate(Date date) throws Exception {
        return LocalDate.parse(date.toString());
    }
}
