package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * CLASE: CargarZapatosJSON
 * --------------------------------------------------
 * Esta es la CLASE PRINCIPAL del ejercicio.
 *
 * Su responsabilidad es:
 * 1️⃣ Ejecutar el script SQL para crear la base de datos y la tabla
 * 2️⃣ Crear la función almacenada necesaria para el menú
 * 3️⃣ Leer el fichero JSON
 * 4️⃣ Insertar los datos en la tabla zapato
 *
 * Representa el flujo completo de inicialización del sistema.
 */
public class CargarZapatosJSON {

    public static void main(String[] args) {

        /*
         * PASO 1️⃣: Inicialización de la base de datos.
         * --------------------------------------------------
         * Se ejecuta el script SQL desde Java para:
         * - Borrar la BD si existe
         * - Crear la BD
         * - Crear la tabla zapato
         *
         * Este paso es OBLIGATORIO según el enunciado.
         */
        EjecutarScriptZapatos.ejecutarScript();

        /*
         * PASO 2️⃣: Creación de la función almacenada.
         * --------------------------------------------------
         * Se crea la función total_zapatos(), que será utilizada
         * posteriormente desde el menú mediante CallableStatement.
         *
         * Se hace aquí para garantizar que la función existe
         * antes de usar el menú.
         */
        EjecutarScriptZapatos.crearFuncionTotal();

        /*
         * PASO 3️⃣: Preparación de la sentencia SQL de inserción.
         * --------------------------------------------------
         * - No se incluye el campo id porque es AUTO_INCREMENT
         * - Se utilizan parámetros (?) para cada campo
         * - Se usará PreparedStatement por seguridad y eficiencia
         */
        String sql = """
            INSERT INTO zapato (marca, modelo, tamano, color, stock, precio)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        /*
         * try-with-resources para asegurar el cierre automático
         * de la conexión y del PreparedStatement.
         */
        try (
            Connection con = ConexionZapatos.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            /*
             * PASO 4️⃣: Lectura del fichero JSON.
             * --------------------------------------------------
             * Se lee el fichero completo y se convierte en JSONObject.
             *
             * El JSON tiene esta estructura:
             * {
             *   "zapatos": [ { ... }, { ... } ]
             * }
             */
            String contenido = Files.readString(Path.of("zapatos.json"));
            JSONObject obj = new JSONObject(contenido);

            /*
             * Obtención del array "zapatos".
             * Cada elemento del array representa un zapato.
             */
            JSONArray zapatos = obj.getJSONArray("zapatos");

            /*
             * PASO 5️⃣: Recorrido del array de zapatos.
             * --------------------------------------------------
             * En cada iteración:
             * - Se obtiene un JSONObject
             * - Se extraen los campos del zapato
             * - Se asignan los valores al PreparedStatement
             */
            for (int i = 0; i < zapatos.length(); i++) {

                JSONObject z = zapatos.getJSONObject(i);

                /*
                 * Asignación de valores a los parámetros del INSERT.
                 *
                 * IMPORTANTE PARA EL EXAMEN:
                 * - El orden debe coincidir con el de la sentencia SQL
                 * - Los nombres de las claves deben coincidir
                 *   exactamente con los del JSON
                 */
                ps.setString(1, z.getString("marca"));
                ps.setString(2, z.getString("modelo"));
                ps.setString(3, z.getString("tamano"));
                ps.setString(4, z.getString("color"));
                ps.setInt(5, z.getInt("stock"));
                ps.setDouble(6, z.getDouble("precio"));

                /*
                 * Se añade la inserción al batch.
                 * No se ejecuta todavía.
                 */
                ps.addBatch();
            }

            /*
             * PASO 6️⃣: Ejecución del batch.
             * --------------------------------------------------
             * Se ejecutan todas las inserciones juntas,
             * lo que mejora el rendimiento y reduce llamadas a la BD.
             */
            ps.executeBatch();

            System.out.println("Zapatos insertados correctamente en la BD");

        } catch (Exception e) {
            /*
             * Captura de cualquier error:
             * - Problemas de conexión
             * - JSON mal formado
             * - Errores en la inserción
             */
            e.printStackTrace();
        }
    }
}
