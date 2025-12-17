package ejercicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MenuTablasAlumnosEj4 {

	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("¿Qué tabla quieres mostrar?");
        System.out.println("1. Alumno");
        System.out.println("2. Modulo");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();

        Connection conexion = ConexionAlumnosEj2.conectar();

        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs;

            if (opcion == 1) {
                rs = stmt.executeQuery("SELECT * FROM alumno");

                System.out.println("\nALUMNOS:");
                System.out.println("ID  NOMBRE             CURSO");

                while (rs.next()) {
                    System.out.println(
                            rs.getInt("id") + "   " +
                            rs.getString("nombre") + "   " +
                            rs.getString("curso")
                    );
                }

            } else if (opcion == 2) {
                rs = stmt.executeQuery("SELECT * FROM modulo");

                System.out.println("\nMODULOS:");
                System.out.println("CODIGO   NOMBRE");

                while (rs.next()) {
                    System.out.println(
                            rs.getString("codigo") + "   " +
                            rs.getString("nombre")
                    );
                }
            } else {
                System.out.println("Opción no válida");
            }

            stmt.close();
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
