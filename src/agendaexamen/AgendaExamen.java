/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agendaexamen;

import java.util.Scanner;

/**
 *
 * @author cmorenoruiz
 */
public class AgendaExamen {

    /**
     * @param args the command line arguments
     */
    public static boolean menuPrincipal() {
        System.out.println("");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1. Listar agenda");
        System.out.println("2. Nuevo contacto");
        System.out.println("3. Modificar contacto");
        System.out.println("4. Eliminar contacto");
        System.out.println("5. Salir");
        try {
            int opcion = pideInt("Elige una opción: ");
            switch (opcion) {
                case 1:
                    opcionMostrarContactos();
                    return false;
                case 2:
                    opcionNuevoContacto();
                    return false;
                case 3:
                    opcionModificarContacto();
                    return false;
                case 4:
                    opcionEliminarContacto();
                    return false;
                case 5:
                    return true;
                default:
                    System.out.println("Opción elegida incorrecta");
                    return false;
            }

        } catch (Exception ex) {
            System.out.println("Escribe una opción numérica válida");
            return false;
        }

    }

    public static int pideInt(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                Scanner sc = new Scanner(System.in);
                int valor = sc.nextInt();
                //in.nextLine();
                return valor;
            } catch (Exception e) {
                System.out.println("No has introducido un número entero. Vuelve a intentarlo.");
            }
        }
    }

    public static String pideLinea(String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                Scanner sc = new Scanner(System.in);
                String linea = sc.nextLine();
                return linea;
            } catch (Exception e) {
                System.out.println("No has introducido una cadena de texto. Vuelve a intentarlo.");
            }
        }
    }

    public static void opcionMostrarContactos() {
        System.out.println("Listado de Contacto:");
        DBManager.printTablaContactos();
    }

    public static void opcionNuevoContacto() {
        Scanner in = new Scanner(System.in);

        System.out.println("Introduce los datos del nuevo contacto:");
        String nombre = pideLinea("Nombre: ");
        String telefono = pideLinea("Teléfono: ");

        boolean res = DBManager.insertContacto(nombre, telefono);

        if (res) {
            System.out.println("Contacto registrado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }

    public static void opcionModificarContacto() {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del cliente a modificar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsContacto(id)) {
            System.out.println("El contacto " + id + " no existe.");
            return;
        }

        // Mostramos datos del cliente a modificar
        DBManager.printContacto(id);

        // Solicitamos los nuevos datos
        String nombre = pideLinea("Nuevo nombre: ");
        String telefono = pideLinea("Nuevo telefono: ");

        // Registramos los cambios
        boolean res = DBManager.updateContacto(id, nombre, telefono);

        if (res) {
            System.out.println("Cliente modificado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }

    public static void opcionEliminarContacto() {
        Scanner in = new Scanner(System.in);

        int id = pideInt("Indica el id del contacto a eliminar: ");

        // Comprobamos si existe el cliente
        if (!DBManager.existsContacto(id)) {
            System.out.println("El contacto " + id + " no existe.");
            return;
        }

        // Eliminamos el contacto
        boolean res = DBManager.deleteContacto(id);

        if (res) {
            System.out.println("Contacto eliminado correctamente");
        } else {
            System.out.println("Error :(");
        }
    }

    public static void main(String[] args) {

        //Cargo el driver y conecto la BD
        DBManager.loadDriver();
        DBManager.connect();

        boolean salir = false;
        do {
            salir = menuPrincipal();
        } while (!salir);

        DBManager.close();

    }
}
