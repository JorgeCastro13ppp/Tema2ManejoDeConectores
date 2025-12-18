package ejercicioFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * CLASE: ConexionZapatos
 * --------------------------------------------------
 * Esta clase se encarga EXCLUSIVAMENTE de gestionar
 * la conexión con la base de datos MySQL.
 *
 * Es una clase de apoyo (utility):
 * - No contiene lógica del ejercicio
 * - No hace consultas
 * - No inserta datos
 * - Solo abre y devuelve una conexión JDBC
 *
 * Todas las demás clases reutilizan este método
 * para conectarse a la base de datos.
 */
public class ConexionZapatos {

    /*
     * URL de conexión a la base de datos.
     *
     * - localhost: servidor MySQL local
     * - 3306: puerto por defecto de MySQL
     * - BDZapatos: base de datos a la que nos conectamos
     * - allowMultiQueries=true:
     *   permite ejecutar varias sentencias SQL seguidas
     *   (por ejemplo scripts o funciones)
     */
	private static final String URL =
		    "jdbc:mysql://localhost:3306/BDZapatos?allowMultiQueries=true";

    // Usuario de la base de datos
    private static final String USER = "root";

    // Contraseña del usuario (vacía en entorno local)
    private static final String PASSWORD = "";

    /*
     * MÉTODO: conectar()
     * --------------------------------------------------
     * Método estático que:
     * 1. Carga el driver JDBC de MySQL
     * 2. Abre la conexión con la base de datos
     * 3. Devuelve el objeto Connection
     *
     * Si ocurre algún error:
     * - Se muestra un mensaje por consola
     * - Se devuelve null
     *
     * Este método NO cierra la conexión,
     * eso se hace en las clases que lo usan
     * mediante try-with-resources.
     */
    public static Connection conectar() {

        // Variable donde se guardará la conexión
        Connection conexion = null;

        try {
            /*
             * Carga explícita del driver JDBC.
             *
             * Aunque en versiones modernas no es obligatorio,
             * en DAM se recomienda hacerlo para dejar claro
             * qué driver se está utilizando.
             */
            Class.forName("com.mysql.cj.jdbc.Driver");

            /*
             * Apertura de la conexión con DriverManager.
             * Si todo va bien, se obtiene un objeto Connection
             * que permitirá ejecutar sentencias SQL.
             */
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            // Mensaje informativo para comprobar que la conexión funciona
            System.out.println("Conexión correcta a BDZapatos");

        } catch (ClassNotFoundException e) {
            /*
             * Esta excepción se lanza si Java no encuentra
             * la clase del driver JDBC.
             *
             * Suele ocurrir por:
             * - Driver no añadido al proyecto
             * - Nombre del driver mal escrito
             */
            System.out.println("Error: Driver no encontrado");

        } catch (SQLException e) {
            /*
             * Esta excepción se lanza si hay un problema
             * al conectar con la base de datos:
             * - Usuario incorrecto
             * - Contraseña incorrecta
             * - Base de datos inexistente
             * - Servidor MySQL apagado
             */
            System.out.println("Error: No se pudo conectar a la BD");
            e.printStackTrace();
        }

        // Se devuelve la conexión (o null si hubo error)
        return conexion;
    }
}
