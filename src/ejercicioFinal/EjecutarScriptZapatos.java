package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
 * CLASE: EjecutarScriptZapatos
 * --------------------------------------------------
 * Esta clase se encarga de la INICIALIZACIÓN de la base de datos.
 *
 * Es una clase clave del ejercicio porque demuestra que:
 * - Sabes ejecutar un script SQL desde Java
 * - No dependes de phpMyAdmin
 * - Sabes trabajar con scripts que contienen varias sentencias
 *
 * Su función es preparar la base de datos antes de:
 * - Leer el JSON
 * - Insertar los datos
 * - Usar el menú
 */
public class EjecutarScriptZapatos {

    /*
     * MÉTODO: ejecutarScript()
     * --------------------------------------------------
     * Este método ejecuta un fichero SQL completo desde Java.
     *
     * CHULETA PARA EL EXAMEN:
     * ----------------------
     * Para ejecutar un script SQL desde Java hay que:
     *
     * 1️⃣ Cargar el driver JDBC
     * 2️⃣ Conectarse al SERVIDOR MySQL (no a una BD concreta)
     * 3️⃣ Usar allowMultiQueries=true
     * 4️⃣ Leer el fichero .sql completo
     * 5️⃣ Ejecutarlo con Statement.execute()
     *
     * El script suele contener:
     * - DROP DATABASE
     * - CREATE DATABASE
     * - USE database
     * - CREATE TABLE
     */
    public static void ejecutarScript() {

        try {
            /*
             * Carga explícita del driver JDBC.
             * En DAM se recomienda hacerlo para dejar claro
             * qué driver se está utilizando.
             */
            Class.forName("com.mysql.cj.jdbc.Driver");

            /*
             * try-with-resources para asegurar el cierre automático
             * de la conexión y del Statement.
             *
             * La conexión se hace SIN especificar base de datos,
             * porque el script se encarga de crearla.
             */
            try (
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?allowMultiQueries=true",
                    "root",
                    ""
                );
                Statement st = con.createStatement()
            ) {
                /*
                 * Lectura completa del fichero SQL.
                 * El contenido se almacena en un String.
                 */
                String script = Files.readString(
                        Path.of("scriptZapatos.sql"));

                /*
                 * Ejecución del script completo.
                 * Statement.execute() se usa porque:
                 * - No devuelve ResultSet
                 * - Puede ejecutar DDL
                 * - Soporta múltiples sentencias
                 */
                st.execute(script);

                // Mensaje de confirmación
                System.out.println("Script ejecutado correctamente");
            }

        } catch (Exception e) {
            /*
             * Captura cualquier error:
             * - Driver no encontrado
             * - Error de conexión
             * - Error de sintaxis SQL
             * - Fichero SQL no encontrado
             */
            e.printStackTrace();
        }
    }

    /*
     * MÉTODO: crearFuncionTotal()
     * --------------------------------------------------
     * Este método crea una FUNCIÓN almacenada en la base de datos.
     *
     * La función devuelve el número total de zapatos
     * almacenados en la tabla zapato.
     *
     * Se utiliza posteriormente desde Java mediante
     * CallableStatement.
     */
    public static void crearFuncionTotal() {

        /*
         * Sentencia SQL para crear la función.
         *
         * - RETURNS INT: devuelve un entero
         * - DETERMINISTIC: siempre devuelve el mismo resultado
         * - El cuerpo devuelve un COUNT(*) de la tabla zapato
         */
        String sql = """
            CREATE FUNCTION total_zapatos()
            RETURNS INT
            DETERMINISTIC
            BEGIN
                RETURN (SELECT COUNT(*) FROM zapato);
            END
        """;

        try (
            /*
             * Conexión directa a la base de datos BDZapatos,
             * ya creada previamente por el script.
             */
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/BDZapatos?allowMultiQueries=true",
                "root",
                ""
            );
            Statement st = con.createStatement()
        ) {
            /*
             * Ejecución de la sentencia de creación de la función.
             */
            st.execute(sql);

            System.out.println("Función total_zapatos creada");

        } catch (Exception e) {
            /*
             * Si la función ya existe, MySQL lanza una excepción.
             * No es un error grave, por eso solo se muestra un mensaje.
             */
            System.out.println("La función ya existe o no se pudo crear");
        }
    }
}

/*
 * =======================
 * CHULETA SQL – EXAMEN AD
 * =======================
 *
 * --- BASES DE DATOS ---
 * - Borrar BD si existe:
 *   DROP DATABASE IF EXISTS nombreBD;
 *
 * - Crear BD:
 *   CREATE DATABASE nombreBD;
 *
 * - Usar BD:
 *   USE nombreBD;
 *
 *
 * --- TABLAS ---
 * - Crear tabla:
 *   CREATE TABLE tabla (
 *       id INT AUTO_INCREMENT PRIMARY KEY,
 *       campo1 VARCHAR(50),
 *       campo2 INT,
 *       campo3 DECIMAL(8,2)
 *   );
 *
 * - Añadir columna:
 *   ALTER TABLE tabla ADD columna VARCHAR(100);
 *
 *
 * --- INSERT ---
 * - Insertar datos:
 *   INSERT INTO tabla (campo1, campo2)
 *   VALUES ('valor', 10);
 *
 *
 * --- SELECT ---
 * - Todos los registros:
 *   SELECT * FROM tabla;
 *
 * - Con condición:
 *   SELECT * FROM tabla WHERE campo < 5;
 *
 *
 * --- UPDATE ---
 * - Actualizar registros:
 *   UPDATE tabla SET campo = campo + 2;
 *
 * - Con condición:
 *   UPDATE tabla SET campo = campo + 2 WHERE nombre = 'Nike';
 *
 *
 * --- DELETE ---
 * - Borrar registros:
 *   DELETE FROM tabla WHERE campo = 0;
 *
 *
 * --- FUNCIONES ---
 * - Crear función:
 *   CREATE FUNCTION total_registros()
 *   RETURNS INT
 *   DETERMINISTIC
 *   BEGIN
 *       RETURN (SELECT COUNT(*) FROM tabla);
 *   END;
 *
 * - Llamar función desde Java:
 *   CallableStatement con parámetro de salida
 *
 *
 * --- PROCEDIMIENTOS ---
 * - Crear procedimiento:
 *   CREATE PROCEDURE subir_precio(IN cantidad DECIMAL(5,2))
 *   BEGIN
 *       UPDATE tabla SET precio = precio + cantidad;
 *   END;
 *
 * - Llamar procedimiento desde Java:
 *   CallableStatement con parámetros de entrada
 *
 *
 * --- RECORDATORIOS EXAMEN ---
 * - SELECT → executeQuery()
 * - INSERT / UPDATE / DELETE / DDL → executeUpdate()
 * - Scripts completos → execute()
 * - Funciones / procedimientos → CallableStatement
 * - Varias sentencias → allowMultiQueries=true
 *
 */

