package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionAlumnosEj2 {
		private static final String URL =
	            "jdbc:mysql://localhost:3306/alumnos";
	    private static final String USER = "root";
	    private static final String PASSWORD = "";

	    public static Connection conectar() {
	        Connection conexion = null;

	        try {
	            // 1️⃣ Cargar el driver
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 2️⃣ Establecer la conexión
	            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

	            System.out.println("Conexión correcta a la base de datos ALUMNOS");

	        } catch (ClassNotFoundException e) {
	            System.out.println("Error: No se encontró el driver de MySQL");
	        } catch (SQLException e) {
	            System.out.println("Error: No se pudo conectar a la base de datos");
	        }

	        return conexion;
	    }

}
