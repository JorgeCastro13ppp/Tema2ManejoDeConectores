package ejercicioFinal;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Scanner;

public class MenuZapatos {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENÚ BD ZAPATOS ---");
            System.out.println("1. Mostrar zapatos con stock < 5");
            System.out.println("2. Subir 2€ a los zapatos Nike");
            System.out.println("3. Añadir campo descripcion");
            System.out.println("4. Zapatos rojos con stock < 20");
            System.out.println("5. Total de zapatos (función)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();

            switch (opcion) {
                case 1: consulta1();
                break;
                case 2: consulta2();
                break;
                case 3: consulta3();
                break;
                case 4: consulta4();
                break;
                case 5: consulta5();
                break;
                case 0: System.out.println("Saliendo...");
                break;
                default: System.out.println("Opción incorrecta");
                break;
            }

        } while (opcion != 0);
    }
    
    private static void consulta1() {
        try (Connection con = ConexionZapatos.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(
                     "SELECT * FROM zapato WHERE stock < 10")) {

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
    
    private static void consulta2() {
        try (Connection con = ConexionZapatos.conectar();
             Statement st = con.createStatement()) {

            int filas = st.executeUpdate(
                    "UPDATE zapato SET precio = precio + 2 WHERE marca = 'Nike'");

            System.out.println("Filas actualizadas: " + filas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consulta3() {
        try (Connection con = ConexionZapatos.conectar();
             Statement st = con.createStatement()) {

            st.executeUpdate(
                    "ALTER TABLE zapato ADD descripcion VARCHAR(100)");

            System.out.println("Campo descripcion añadido");

        } catch (Exception e) {
            System.out.println("El campo ya existe");
        }
    }

    private static void consulta4() {
        String sql = "SELECT * FROM zapato WHERE color = ? AND stock < ?";

        try (Connection con = ConexionZapatos.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

    private static void consulta5() {
        try (Connection con = ConexionZapatos.conectar();
             CallableStatement cs =
                     con.prepareCall("{? = call total_zapatos()}")) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();

            int total = cs.getInt(1);
            System.out.println("Total de zapatos: " + total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


