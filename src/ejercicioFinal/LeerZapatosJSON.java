package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

public class LeerZapatosJSON {

    public static void main(String[] args) {

        try {
            // 1️⃣ Leer el fichero completo
            String contenido = Files.readString(
                    Path.of("zapatos.json"));

            // 2️⃣ Convertir a JSONObject
            JSONObject obj = new JSONObject(contenido);

            // 3️⃣ Obtener el array "zapatos"
            JSONArray zapatos = obj.getJSONArray("zapatos");

            // 4️⃣ Recorrer el array
            for (int i = 0; i < zapatos.length(); i++) {

                JSONObject z = zapatos.getJSONObject(i);

                String marca = z.getString("marca");
                String modelo = z.getString("modelo");
                String tamano = z.getString("tamano");
                String color = z.getString("color");
                int stock = z.getInt("stock");
                double precio = z.getDouble("precio");

                System.out.println(
                        marca + " | " +
                        modelo + " | " +
                        tamano + " | " +
                        color + " | " +
                        stock + " | " +
                        precio
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
