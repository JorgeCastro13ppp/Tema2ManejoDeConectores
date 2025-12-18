package ejercicioFinal;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/*
 * CLASE: ExportarZapatosTXT
 * --------------------------------------------------
 * Esta clase se encarga de EXPORTAR los datos de la
 * base de datos a un fichero de texto.
 *
 * Concretamente:
 * - Lee los datos de la tabla zapato
 * - Los escribe en un fichero .txt
 * - Utiliza un formato separado por ';'
 *
 * Esta clase demuestra que sabes:
 * - Leer datos desde la base de datos
 * - Recorrer un ResultSet
 * - Escribir datos en un fichero
 */
public class ExportarZapatosTXT {

    public static void main(String[] args) {

        /*
         * Ruta del fichero de salida.
         *
         * El fichero se creará en la carpeta del proyecto
         * si no existe, o se sobrescribirá si ya existe.
         */
        Path fichero = Path.of("zapatos.txt");

        /*
         * try-with-resources para asegurar el cierre automático
         * de todos los recursos:
         * - Connection
         * - Statement
         * - ResultSet
         * - BufferedWriter
         */
        try (
            Connection con = ConexionZapatos.conectar();
            Statement st = con.createStatement();

            /*
             * Ejecución de la consulta SELECT.
             * Se obtiene un ResultSet con todos los zapatos.
             */
            ResultSet rs = st.executeQuery("SELECT * FROM zapato");

            /*
             * BufferedWriter permite escribir texto de forma eficiente
             * en el fichero de salida.
             */
            BufferedWriter bw = Files.newBufferedWriter(fichero)
        ) {
            /*
             * Recorrido del ResultSet.
             *
             * Cada llamada a rs.next() avanza una fila,
             * que representa un registro de la tabla zapato.
             */
            while (rs.next()) {

                /*
                 * Escritura de los datos en una línea del fichero.
                 *
                 * Se utiliza ';' como separador para que el fichero
                 * pueda ser leído fácilmente como CSV.
                 *
                 * IMPORTANTE PARA EL EXAMEN:
                 * - Los nombres de las columnas deben coincidir
                 *   exactamente con los de la tabla
                 * - El orden es el que tú decidas, pero debe ser coherente
                 */
                bw.write(
                    rs.getString("marca") + ";" +
                    rs.getString("modelo") + ";" +
                    rs.getString("tamano") + ";" +
                    rs.getString("color") + ";" +
                    rs.getInt("stock") + ";" +
                    rs.getDouble("precio")
                );

                /*
                 * Salto de línea para el siguiente registro.
                 */
                bw.newLine();
            }

            /*
             * Mensaje de confirmación de que la exportación
             * se ha realizado correctamente.
             */
            System.out.println("Datos exportados a zapatos.txt");

        } catch (Exception e) {
            /*
             * Captura de cualquier error:
             * - Problemas de conexión
             * - Error en la consulta SQL
             * - Problemas al escribir el fichero
             */
            e.printStackTrace();
        }
    }
}
