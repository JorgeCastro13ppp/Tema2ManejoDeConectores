package ejercicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MenuNavaVintedEj5 {

	public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("¿Qué tabla quieres mostrar?");
        System.out.println("1. Producto");
        System.out.println("2. Categoria");
        System.out.println("3. Talla");
        System.out.println("4. Color");
        System.out.println("5. Material");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();

        String tabla = "";

        switch (opcion) {
            case 1 -> tabla = "producto";
            case 2 -> tabla = "categoria";
            case 3 -> tabla = "talla";
            case 4 -> tabla = "color";
            case 5 -> tabla = "material";
            default -> {
                System.out.println("Opción no válida");
                return;
            }
        }

        Connection conexion = ConexionNavaVintedEj5.conectar();

        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tabla);

            System.out.println("\nTABLA: " + tabla.toUpperCase());

            while (rs.next()) {
                // Mostramos solo la primera columna (suficiente para el ejercicio)
                System.out.println(rs.getString(1)+" "+rs.getString(2));
                System.out.println();
            }

            rs.close();
            stmt.close();
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
