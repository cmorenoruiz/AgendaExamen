/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agendaexamen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

/**
 *
 * @author cmorenoruiz
 */
public class AgendaExamen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            //Primero hay que cargar dinámicamente el Driver, pero he comprobado que funciona la conexión sin hacerlo
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println("No ha podido cargar el Driver");
            ex.printStackTrace();
        }
        String baseDeDatos = "agenda";
        String usuario = "root";
        String password = "";
        String direccionIP = "10.230.109.182";
        String url = "jdbc:mysql://" + direccionIP + ":3306/" + baseDeDatos + "?serverTimezone=UTC";
        try {
            Connection conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("La conexión ha ido bien");
            Statement ejecutor =conexion.createStatement();
            System.out.println("Ya tengo el objeto ejecutor preparado");
            String consulta ="SELECT * FROM contactos;";
            ResultSet resultados=ejecutor.executeQuery(consulta);
            ejecutor.close();
            conexion.close();
        } catch (SQLTimeoutException ex) {
            System.out.println("Ha tardado demasiado tiempo sin responder el servidor");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
