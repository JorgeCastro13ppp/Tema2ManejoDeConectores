package ejercicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MostrarAlumnosEj3 {

	public static void main(String[] args) {

        // 1️⃣ Abrimos la conexión
        Connection conexion = ConexionAlumnosEj2.conectar();

        try {
            // 2️⃣ Creamos la sentencia
            Statement stmt = conexion.createStatement();

            // 3️⃣ Ejecutamos la consulta
            ResultSet rs = stmt.executeQuery("SELECT * FROM alumno");

            System.out.println("ID  NOMBRE             CURSO");

            // 4️⃣ Recorremos los resultados
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "   " +
                        rs.getString("nombre") + "   " +
                        rs.getString("curso")
                );
            }

            // 5️⃣ Cerramos recursos
            rs.close();
            stmt.close();
            conexion.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
