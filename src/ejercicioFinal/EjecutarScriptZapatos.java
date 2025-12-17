package ejercicioFinal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EjecutarScriptZapatos {

    public static void ejecutarScript() {

        try {
            // Cargar driver JDBC (recomendado en DAM)
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?allowMultiQueries=true",
                    "root",
                    ""
                );
                Statement st = con.createStatement()
            ) {
                // El script recrea la BD desde cero (DROP + CREATE)
                String script = Files.readString(
                        Path.of("scriptZapatos.sql"));

                st.execute(script);

                System.out.println("Script ejecutado correctamente");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void crearFuncionTotal() {

        String sql = """
            CREATE FUNCTION total_zapatos()
            RETURNS INT
            DETERMINISTIC
            BEGIN
                RETURN (SELECT COUNT(*) FROM zapato);
            END
        """;

        try (
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/BDZapatos?allowMultiQueries=true",
                "root",
                ""
            );
            Statement st = con.createStatement()
        ) {
            st.execute(sql);
            System.out.println("Función total_zapatos creada");

        } catch (Exception e) {
            System.out.println("La función ya existe o no se pudo crear");
        }
    }

}
