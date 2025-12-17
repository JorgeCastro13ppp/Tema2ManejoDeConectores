package ejemplos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class EjemploMostrarPersonas {
public static void main(String[] args) {
 // Llamar al método conectar para establecer la conexión
 Connection conexion = ConexionBD.conectar();

 try {
 Statement sentencia = conexion.createStatement();
 ResultSet resultado = sentencia.executeQuery ("select * from persona");

 System.out.println ("DNI \t \t Nombre \tEdad");

 while (resultado.next()) {
 System.out.println (resultado.getString (1) +
 "\t " + resultado.getString (2) +
 " \t" + resultado.getInt (3));
 }
 resultado.close();
 sentencia.close();
 conexion.close();

 } catch (SQLException e1) {
// TODO Bloque catch generado automáticamente
e1.printStackTrace();
}
 }
}

