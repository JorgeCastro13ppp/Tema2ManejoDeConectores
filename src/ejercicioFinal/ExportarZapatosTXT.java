package ejercicioFinal;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class ExportarZapatosTXT {

    public static void main(String[] args) {

        Path fichero = Path.of("zapatos.txt");

        try (
            Connection con = ConexionZapatos.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM zapato");
            BufferedWriter bw = Files.newBufferedWriter(fichero)
        ) {
            while (rs.next()) {
                bw.write(
                    rs.getString("marca") + ";" +
                    rs.getString("modelo") + ";" +
                    rs.getString("tamano") + ";" +
                    rs.getString("color") + ";" +
                    rs.getInt("stock") + ";" +
                    rs.getDouble("precio")
                );
                bw.newLine();
            }

            System.out.println("Datos exportados a zapatos.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
