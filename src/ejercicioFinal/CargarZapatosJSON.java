package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.JSONArray;
import org.json.JSONObject;

public class CargarZapatosJSON {

    public static void main(String[] args) {

        // 1️ Ejecutar el script SQL (OBLIGATORIO POR ENUNCIADO)
        EjecutarScriptZapatos.ejecutarScript();
        
        EjecutarScriptZapatos.crearFuncionTotal();
        
        System.out.println("Script ejecutado correctamente.");

        String sql = """
            INSERT INTO zapato (marca, modelo, tamano, color, stock, precio)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection con = ConexionZapatos.conectar();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            // 2️ Leer el JSON
            String contenido = Files.readString(Path.of("zapatos.json"));
            JSONObject obj = new JSONObject(contenido);
            JSONArray zapatos = obj.getJSONArray("zapatos");

            // 3️ Recorrer el array
            for (int i = 0; i < zapatos.length(); i++) {

                JSONObject z = zapatos.getJSONObject(i);

                ps.setString(1, z.getString("marca"));
                ps.setString(2, z.getString("modelo"));
                ps.setString(3, z.getString("tamano"));
                ps.setString(4, z.getString("color"));
                ps.setInt(5, z.getInt("stock"));
                ps.setDouble(6, z.getDouble("precio"));

                ps.addBatch();
            }

            // 4️ Ejecutar batch
            ps.executeBatch();

            System.out.println("Zapatos insertados correctamente en la BD");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
