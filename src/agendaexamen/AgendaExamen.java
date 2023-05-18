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
import java.util.Scanner;

/**
 *
 * @author cmorenoruiz
 */
public class AgendaExamen {

    /**
     * @param args the command line arguments
     */
    public static int menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println();
            System.out.print("""
                         ----- Men\u00fa Principal -----
                         1. Muestra la agenda
                         2. Edita un contacto
                         3. Elimina un contacto
                         4. Salir
                         Escribe una opción válida: """);
            try {
                opcion = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Escribe una opción numérica válida");
                opcion = 5;
            }

        } while (opcion > 4);
        return opcion;
    }

    public static void muestraAgenda(ResultSet rs) throws SQLException {

        Integer id;
        String nombre, telefono;
        while (rs.next()) {
            id = rs.getInt("id");
            nombre = rs.getString("nombre");
            telefono = rs.getString("telefono");
            System.out.println(id + nombre + telefono);
        }
    }

    public static Statement conectandoBD(Connection conn) throws SQLException {
        Statement ejecutor = conn.createStatement();
        return ejecutor;
    }

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
        String direccionIPIES = "10.230.109.182";
        String direccionIPHome = "192.168.1.159";
        String direccionIP = "";
        try {
            //Leemos del teclado para elegir la IP del servidor en casa o en el IES
            System.out.println("Si estás en casa escribe H de Home, en el instituto I de IES");
            Scanner sc = new Scanner(System.in);
            String respuesta = sc.next("[hHiI]");
            switch (respuesta.toUpperCase()) {
                case "H":
                    direccionIP = direccionIPHome;
                    break;
                case "I":
                    direccionIP = direccionIPIES;
                    break;
                default:;
            }
        } catch (Exception ex) {
            System.out.println("Te empeñas en escribir cualquier cosa en vez de una letra H o I");
        }
        //Construyo la url de conexión a la BD
        String url = "jdbc:mysql://" + direccionIP + ":3306/" + baseDeDatos + "?serverTimezone=UTC";
        try {
            Connection conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("La conexión ha ido bien");
            Statement ejecutor = conexion.createStatement();
            System.out.println("Ya tengo el objeto ejecutor preparado");
            //Lanzo el menú
            int opcion = 1;
            do {
                opcion = menu();
                switch (opcion) {
                    case 1:
                        muestraAgenda();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        System.out.println("No debería ejecutarse esta opción del menú");
                }

            } while (opcion != 4);
            //No puedo cerrar las conexiones antes de mostrar los resultados
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
