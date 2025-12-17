package ejercicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionNavaVintedEj5 {
	private static final String URL =
            "jdbc:mysql://localhost:3306/navavinted";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión correcta a la base de datos NAVAVINTED");

        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la BD");
        }

        return conexion;
    }
}
