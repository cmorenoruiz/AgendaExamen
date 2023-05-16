/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agendaexamen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cmorenoruiz
 */
public class AgendaExamen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String baseDeDatos = "agenda";
        String usuario = "root";
        String password = "";
        String direccionIP = "10.230.109.182";
        String url = "jdbc:mysql://" + direccionIP + ":3306/" + baseDeDatos + "?serverTimezone=UTC";
        try {
            Connection conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Todo ha ido bien");
            conexion.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


