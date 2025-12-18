package ejercicioFinal;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Scanner;

/*
 * CLASE: MenuZapatos
 * --------------------------------------------------
 * Esta clase implementa un menú por consola que permite
 * interactuar con la base de datos BDZapatos.
 *
 * A través del menú se realizan distintas operaciones:
 * - Consultas SELECT
 * - Actualizaciones UPDATE
 * - Modificaciones de estructura (ALTER TABLE)
 * - Llamadas a funciones almacenadas
 *
 * Esta clase demuestra el uso de:
 * - Statement
 * - PreparedStatement
 * - CallableStatement
 */
public class MenuZapatos {

    public static void main(String[] args) {

        /*
         * Scanner para leer la opción introducida por el usuario.
         */
        Scanner sc = new Scanner(System.in);
        int opcion;

        /*
         * Bucle principal del menú.
         * Se repite hasta que el usuario introduce la opción 0.
         */
        do {
            System.out.println("\n--- MENÚ BD ZAPATOS ---");
            System.out.println("1. Mostrar zapatos con stock < 10");
            System.out.println("2. Subir 2€ a los zapatos Nike");
            System.out.println("3. Añadir campo descripcion");
            System.out.println("4. Zapatos rojos con stock < 20");
            System.out.println("5. Total de zapatos (función)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            /*
             * Lectura de la opción del usuario.
             */
            opcion = sc.nextInt();

            /*
             * Selección de la acción según la opción elegida.
             */
            switch (opcion) {
                case 1: consulta1(); break;
                case 2: consulta2(); break;
                case 3: consulta3(); break;
                case 4: consulta4(); break;
                case 5: consulta5(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción incorrecta"); break;
            }

        } while (opcion != 0);
    }

    /*
     * CONSULTA 1
     * --------------------------------------------------
     * Muestra los zapatos cuyo stock es menor que un valor dado.
     *
     * Se utiliza Statement porque:
     * - La consulta es fija
     * - No hay parámetros
     */
    private static void consulta1() {

        try (
            Connection con = ConexionZapatos.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM zapato WHERE stock < 10")
        ) {
            /*
             * Recorrido del ResultSet.
             * Cada fila representa un zapato.
             */
            while (rs.next()) {
                System.out.println(
                        rs.getString("marca") + " - " +
                        rs.getString("modelo") + " - stock: " +
                        rs.getInt("stock")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * CONSULTA 2
     * --------------------------------------------------
     * Actualiza el precio de los zapatos de la marca Nike,
     * incrementándolo en 2 euros.
     *
     * Se utiliza Statement porque:
     * - No hay parámetros dinámicos
     * - La sentencia es fija
     *
     * executeUpdate devuelve el número de filas afectadas.
     */
    private static void consulta2() {

        try (
            Connection con = ConexionZapatos.conectar();
            Statement st = con.createStatement()
        ) {
            int filas = st.executeUpdate(
                "UPDATE zapato SET precio = precio + 2 WHERE marca = 'Nike'");

            System.out.println("Filas actualizadas: " + filas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * CONSULTA 3
     * --------------------------------------------------
     * Modifica la estructura de la tabla zapato,
     * añadiendo una nueva columna llamada descripcion.
     *
     * Se utiliza Statement porque se trata de DDL.
     */
    private static void consulta3() {

        try (
            Connection con = ConexionZapatos.conectar();
            Statement st = con.createStatement()
        ) {
            st.executeUpdate(
                "ALTER TABLE zapato ADD descripcion VARCHAR(100)");

            System.out.println("Campo descripcion añadido");

        } catch (Exception e) {
            /*
             * Si la columna ya existe, MySQL lanza una excepción.
             * No es un error crítico.
             */
            System.out.println("El campo ya existe");
        }
    }

    /*
     * CONSULTA 4
     * --------------------------------------------------
     * Muestra los zapatos de un color determinado
     * y con stock inferior a un valor dado.
     *
     * Se utiliza PreparedStatement porque:
     * - Hay parámetros (?, ?)
     * - Se evitan inyecciones SQL
     */
    private static void consulta4() {

        String sql = "SELECT * FROM zapato WHERE color = ? AND stock < ?";

        try (
            Connection con = ConexionZapatos.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            /*
             * Asignación de valores a los parámetros.
             * El orden debe coincidir con los ? de la consulta.
             */
            ps.setString(1, "Rojo");
            ps.setInt(2, 21);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getString("marca") + " - " +
                        rs.getString("modelo") + " - " +
                        rs.getInt("stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * CONSULTA 5
     * --------------------------------------------------
     * Llama a una FUNCIÓN almacenada en la base de datos.
     *
     * La función total_zapatos devuelve el número total
     * de registros de la tabla zapato.
     *
     * Se utiliza CallableStatement porque:
     * - Se ejecuta una función/procedimiento almacenado
     * - Se recibe un valor de salida
     */
    private static void consulta5() {

        try (
            Connection con = ConexionZapatos.conectar();
            CallableStatement cs =
                con.prepareCall("{? = call total_zapatos()}")
        ) {
            /*
             * Registro del parámetro de salida.
             * Types.INTEGER indica el tipo devuelto por la función.
             */
            cs.registerOutParameter(1, Types.INTEGER);

            /*
             * Ejecución de la función.
             */
            cs.execute();

            /*
             * Obtención del valor devuelto por la función.
             */
            int total = cs.getInt(1);
            System.out.println("Total de zapatos: " + total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
