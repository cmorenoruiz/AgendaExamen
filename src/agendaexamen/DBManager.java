/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agendaexamen;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author lionel Adaptación de Cristina
 */
public class DBManager {

    // Conexión a la base de datos
    private static Connection conn = null;

    // Configuración de la conexión a la base de datos
    private static String IP_ADDRES_HOST = "localhost";
    private static final String IP_ADDRESS_IES = "10.230.109.71";
    private static final String IP_ADDRESS_HOME = "192.168.1.159";

    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "agenda";

    private static String DB_URL;

    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_MSQ_CONN_OK = "CONEXIÓN CORRECTA";
    private static final String DB_MSQ_CONN_NO = "ERROR EN LA CONEXIÓN";

    // Configuración de la tabla Contactos
    private static final String DB_CONT = "contactos";
    private static final String DB_CONT_SELECT = "SELECT * FROM " + DB_CONT;
    private static final String DB_CONT_ID = "id";
    private static final String DB_CONT_NOM = "nombre";
    private static final String DB_CONT_TEL = "telefono";////OJOOOOOOOOOOOOOOOOOOOOOOOOOOOO

    //////////////////////////////////////////////////
    // MÉTODOS DE CONEXIÓN A LA BASE DE DATOS
    //////////////////////////////////////////////////
    ;
    
    /**
     * Intenta cargar el JDBC driver.
     * @return true si pudo cargar el driver, false en caso contrario
     */
    public static boolean loadDriver() {
        try {
            System.out.print("Cargando Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("OK!");
            return true;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta conectar con la base de datos.
     *
     * @return true si pudo conectarse, false en caso contrario
     */
    public static boolean connect() {
        try {
            try {
                //Leemos del teclado para elegir la IP del servidor en casa o en el IES
                System.out.println("Si estás en casa escribe H de Home, en el instituto I de IES");
                Scanner sc = new Scanner(System.in);
                String respuesta = sc.next("[hHiI]");
                switch (respuesta.toUpperCase()) {
                    case "H":
                        IP_ADDRES_HOST = IP_ADDRESS_HOME;
                        break;
                    case "I":
                        IP_ADDRES_HOST = IP_ADDRESS_IES;
                        break;
                    default:;
                }
            } catch (Exception ex) {
                System.out.println("Te empeñas en escribir cualquier cosa en vez de una letra H o I");
                return false;
            }
            DB_URL = "jdbc:mysql://" + IP_ADDRES_HOST + ":" + DB_PORT + "/" + DB_NAME + "?serverTimezone=UTC";

            System.out.print("Conectando a la base de datos...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("OK!");

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Comprueba la conexión y muestra su estado por pantalla
     *
     * @return true si la conexión existe y es válida, false en caso contrario
     */
    public static boolean isConnected() {
        // Comprobamos estado de la conexión
        try {
            if (conn != null && conn.isValid(0)) {
                System.out.println(DB_MSQ_CONN_OK);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(DB_MSQ_CONN_NO);
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Cierra la conexión con la base de datos
     */
    public static void close() {
        try {
            System.out.print("Cerrando la conexión...");
            conn.close();
            System.out.println("OK!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE TABLA CONTACTOS
    //////////////////////////////////////////////////
    ;
    
    // Devuelve 
    // Los argumentos indican el tipo de ResultSet deseado
    /**
     * Obtiene toda la tabla contactos de la base de datos
     * @param resultSetType Tipo de ResultSet
     * @param resultSetConcurrency Concurrencia del ResultSet
     * @return ResultSet (del tipo indicado) con la tabla, null en caso de error
     */
    public static ResultSet getTablaContactos(int resultSetType, int resultSetConcurrency) {
        try {
            Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = stmt.executeQuery(DB_CONT_SELECT);
            //stmt.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * Obtiene toda la tabla contactos de la base de datos
     *
     * @return ResultSet (por defecto) con la tabla, null en caso de error
     */
    public static ResultSet getTablaContactos() {
        return DBManager.getTablaContactos(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Imprime por pantalla el contenido de la tabla contacto
     */
    public static void printTablaContactos() {
        try {
            ResultSet rs = DBManager.getTablaContactos(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            while (rs.next()) {
                int id = rs.getInt(DB_CONT_ID);
                String n = rs.getString(DB_CONT_NOM);
                String d = rs.getString(DB_CONT_TEL);
                System.out.println(id + "\t" + n + "\t" + d);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //////////////////////////////////////////////////
    // MÉTODOS DE UN SOLO CONTACTO
    //////////////////////////////////////////////////
    ;
    
    /**
     * Solicita a la BD el contacto con id indicado
     * @param id id del contacto
     * @return ResultSet con el resultado de la consulta, null en caso de error
     */
    public static ResultSet getContacto(int id) {
        try {
            // Realizamos la consulta SQL
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = DB_CONT_SELECT + " WHERE " + DB_CONT_ID + "='" + id + "';";
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            //stmt.close();

            // Si no hay primer registro entonces no existe el contacto
            if (!rs.first()) {
                return null;
            }

            // Todo bien, devolvemos el contacto
            return rs;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Comprueba si en la BD existe el contacto con id indicado
     *
     * @param id id del contacto
     * @return verdadero si existe, false en caso contrario
     */
    public static boolean existsContacto(int id) {
        try {
            // Obtenemos el contacto
            ResultSet rs = getContacto(id);

            // Si rs es null, se ha producido un error
            if (rs == null) {
                return false;
            }

            // Si no existe primer registro
            if (!rs.first()) {
                rs.close();
                return false;
            }

            // Todo bien, existe el contacto
            rs.close();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Imprime los datos del contacto con id indicado
     *
     * @param id id del contacto
     */
    public static void printContacto(int id) {
        try {
            // Obtenemos el contacto
            ResultSet rs = getContacto(id);
            if (rs == null || !rs.first()) {
                System.out.println("Contacto " + id + " NO EXISTE");
                return;
            }

            // Imprimimos su información por pantalla
            int cid = rs.getInt(DB_CONT_ID);
            String nombre = rs.getString(DB_CONT_NOM);
            String telefono = rs.getString(DB_CONT_TEL);
            System.out.println("Contacto " + cid + "\t" + nombre + "\t" + telefono);

        } catch (SQLException ex) {
            System.out.println("Error al solicitar contacto " + id);
            ex.printStackTrace();
        }
    }

    /**
     * Solicita a la BD insertar un nuevo registro contacto
     *
     * @param nombre nombre del contacto
     * @param telefono dirección del contacto
     * @return verdadero si pudo insertarlo, false en caso contrario
     */
    public static boolean insertContacto(String nombre, String telefono) {
        try {
            // Obtenemos la tabla contactos
            System.out.print("Insertando contacto " + nombre + "...");
            ResultSet rs = DBManager.getTablaContactos(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            // Insertamos el nuevo registro
            rs.moveToInsertRow();
            rs.updateString(DB_CONT_NOM, nombre);
            rs.updateString(DB_CONT_TEL, telefono);
            rs.insertRow();

            // Todo bien, cerramos ResultSet y devolvemos true
            rs.close();
            System.out.println("OK!");
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD modificar los datos de un contacto
     *
     * @param id id del contacto a modificar
     * @param nombre nuevo nombre del contacto
     * @param telefono nuevo telefono del contacto
     * @return verdadero si pudo modificarlo, false en caso contrario
     */
    public static boolean updateContacto(int id, String nuevoNombre, String nuevoTelefono) {
        try {
            // Obtenemos el contacto
            System.out.print("Actualizando contacto " + id + "... ");
            ResultSet rs = getContacto(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("Error. ResultSet null.");
                return false;
            }

            // Si tiene un primer registro, lo eliminamos
            if (rs.first()) {
                rs.updateString(DB_CONT_NOM, nuevoNombre);
                rs.updateString(DB_CONT_TEL, nuevoTelefono);
                rs.updateRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Solicita a la BD eliminar un contacto
     *
     * @param id id del contacto a eliminar
     * @return verdadero si pudo eliminarlo, false en caso contrario
     */
    public static boolean deleteContacto(int id) {
        try {
            System.out.print("Eliminando contacto " + id + "... ");

            // Obtenemos el contacto
            ResultSet rs = getContacto(id);

            // Si no existe el Resultset
            if (rs == null) {
                System.out.println("ERROR. ResultSet null.");
                return false;
            }

            // Si existe y tiene primer registro, lo eliminamos
            if (rs.first()) {
                rs.deleteRow();
                rs.close();
                System.out.println("OK!");
                return true;
            } else {
                System.out.println("ERROR. ResultSet vacío.");
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
