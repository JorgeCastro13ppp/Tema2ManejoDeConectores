package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

/*
 * CLASE: LeerZapatosJSON
 * --------------------------------------------------
 * Esta clase se utiliza para PROBAR la lectura del fichero JSON.
 *
 * Su objetivo NO es insertar datos en la base de datos,
 * sino comprobar que:
 * - El fichero JSON se lee correctamente
 * - La estructura del JSON se entiende bien
 * - Se puede acceder a cada campo sin errores
 *
 * Es una clase de APOYO durante el desarrollo.
 * No es estrictamente necesaria para la entrega final,
 * pero demuestra que sabes trabajar con JSON.
 */
public class LeerZapatosJSON {

    public static void main(String[] args) {

        try {
            /*
             * PASO 1️⃣: Leer el fichero JSON completo.
             *
             * - Files.readString() lee todo el contenido del fichero
             * - El contenido se almacena en un String
             * - El fichero debe existir en la ruta indicada
             */
            String contenido = Files.readString(
                    Path.of("zapatos.json"));

            /*
             * PASO 2️⃣: Convertir el texto leído en un JSONObject.
             *
             * El JSON tiene esta estructura:
             * {
             *   "zapatos": [ { ... }, { ... } ]
             * }
             *
             * Por eso primero se crea un JSONObject raíz.
             */
            JSONObject obj = new JSONObject(contenido);

            /*
             * PASO 3️⃣: Obtener el array asociado a la clave "zapatos".
             *
             * - "zapatos" es un JSONArray
             * - Cada elemento del array es un JSONObject
             *   que representa un zapato
             */
            JSONArray zapatos = obj.getJSONArray("zapatos");

            /*
             * PASO 4️⃣: Recorrer el array de zapatos.
             *
             * En cada iteración:
             * - Se obtiene un JSONObject
             * - Se accede a cada campo por su nombre
             */
            for (int i = 0; i < zapatos.length(); i++) {

                JSONObject z = zapatos.getJSONObject(i);

                /*
                 * Extracción de los campos del zapato.
                 *
                 * IMPORTANTE PARA EL EXAMEN:
                 * - Los nombres deben coincidir EXACTAMENTE
                 *   con los del JSON
                 * - Java distingue mayúsculas y minúsculas
                 */
                String marca = z.getString("marca");
                String modelo = z.getString("modelo");
                String tamano = z.getString("tamano");
                String color = z.getString("color");
                int stock = z.getInt("stock");
                double precio = z.getDouble("precio");

                /*
                 * Mostrar los datos por consola.
                 *
                 * Esto sirve para comprobar visualmente
                 * que el JSON se ha leído correctamente.
                 */
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
            /*
             * Captura cualquier error:
             * - Fichero JSON no encontrado
             * - JSON mal formado
             * - Error al acceder a una clave inexistente
             */
            e.printStackTrace();
        }
    }
}
/*📂 CHULETA EXAMEN – CAMBIO DE TIPO DE FICHERO
Acceso a Datos (JSON · TXT · CSV · XML)
🧠 IDEA CLAVE (LO MÁS IMPORTANTE)

👉 El tipo de fichero SOLO cambia la lectura
👉 La inserción en BD es SIEMPRE IGUAL

El esquema mental NO cambia nunca:

Leer fichero
→ Recorrer datos
→ Insertar en BD (PreparedStatement)

🟦 PARTE COMÚN (SIEMPRE IGUAL)

Esto NO cambia aunque el fichero sea distinto:

La tabla

El INSERT

El PreparedStatement

El addBatch() / executeBatch()

String sql = """
INSERT INTO zapato (marca, modelo, tamano, color, stock, precio)
VALUES (?, ?, ?, ?, ?, ?)
""";

PreparedStatement ps = con.prepareStatement(sql);


👉 Lo único que cambia es de dónde sacas los valores.

🟨 CASO 1 – JSON (EL MÁS TÍPICO)
📄 Estructura típica
{
  "zapatos": [
    {
      "marca": "Nike",
      "modelo": "Air Max",
      "tamano": "42",
      "color": "Rojo",
      "stock": 20,
      "precio": 99.99
    }
  ]
}

🧠 ESQUEMA MENTAL
Fichero → JSONObject raíz
→ JSONArray
→ JSONObject
→ campos

🧩 CÓDIGO DE LECTURA JSON
String contenido = Files.readString(Path.of("zapatos.json"));
JSONObject obj = new JSONObject(contenido);
JSONArray zapatos = obj.getJSONArray("zapatos");

for (int i = 0; i < zapatos.length(); i++) {
    JSONObject z = zapatos.getJSONObject(i);

    String marca = z.getString("marca");
    String modelo = z.getString("modelo");
    String tamano = z.getString("tamano");
    String color = z.getString("color");
    int stock = z.getInt("stock");
    double precio = z.getDouble("precio");

    // aquí va el INSERT
}

⚠️ ERRORES TÍPICOS JSON

Tratar el fichero como array directamente ❌

Escribir mal el nombre de las claves ❌

Confundir "tamano" con "tamaño" ❌

🟨 CASO 2 – TXT / CSV (MUY PROBABLE EN EXAMEN)
📄 Ejemplo de fichero zapatos.txt
Nike;Air Max;42;Rojo;20;99.99
Adidas;Ultraboost;44;Negro;15;129.99

🧠 ESQUEMA MENTAL
Fichero → línea
→ split(";")
→ array de String
→ conversiones

🧩 CÓDIGO DE LECTURA TXT / CSV
BufferedReader br = Files.newBufferedReader(Path.of("zapatos.txt"));
String linea;

while ((linea = br.readLine()) != null) {

    String[] partes = linea.split(";");

    String marca = partes[0];
    String modelo = partes[1];
    String tamano = partes[2];
    String color = partes[3];
    int stock = Integer.parseInt(partes[4]);
    double precio = Double.parseDouble(partes[5]);

    // aquí va el INSERT
}

⚠️ ERRORES TÍPICOS TXT / CSV

Olvidar convertir int y double ❌

Usar mal el separador (; vs ,) ❌

No comprobar el orden de columnas ❌

🟨 CASO 3 – XML (MENOS COMÚN, PERO PUEDE CAER)
📄 Ejemplo de fichero XML
<zapatos>
    <zapato>
        <marca>Nike</marca>
        <modelo>Air Max</modelo>
        <tamano>42</tamano>
        <color>Rojo</color>
        <stock>20</stock>
        <precio>99.99</precio>
    </zapato>
</zapatos>

🧠 ESQUEMA MENTAL
Documento XML
→ NodeList
→ Element
→ getTextContent()

🧩 CÓDIGO DE LECTURA XML
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document doc = db.parse(new File("zapatos.xml"));

NodeList lista = doc.getElementsByTagName("zapato");

for (int i = 0; i < lista.getLength(); i++) {

    Element e = (Element) lista.item(i);

    String marca = e.getElementsByTagName("marca")
                     .item(0).getTextContent();

    String modelo = e.getElementsByTagName("modelo")
                      .item(0).getTextContent();

    String tamano = e.getElementsByTagName("tamano")
                      .item(0).getTextContent();

    String color = e.getElementsByTagName("color")
                     .item(0).getTextContent();

    int stock = Integer.parseInt(
                 e.getElementsByTagName("stock")
                  .item(0).getTextContent());

    double precio = Double.parseDouble(
                    e.getElementsByTagName("precio")
                     .item(0).getTextContent());

    // aquí va el INSERT
}

⚠️ ERRORES TÍPICOS XML

Confundir nodos con atributos ❌

No convertir a int / double ❌

Usar mal el nombre de las etiquetas ❌

🟩 COMPARATIVA RÁPIDA (MEMORIZAR)
Fichero	Cómo se recorre
JSON	JSONArray + JSONObject
TXT / CSV	línea → split
XML	NodeList → Element */
