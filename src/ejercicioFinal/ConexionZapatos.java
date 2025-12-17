package ejercicioFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionZapatos {

	private static final String URL =
		    "jdbc:mysql://localhost:3306/BDZapatos?allowMultiQueries=true";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión correcta a BDZapatos");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la BD");
            e.printStackTrace();
        }

        return conexion;
    }
}
