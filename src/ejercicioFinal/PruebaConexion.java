package ejercicioFinal;

/*
 * CLASE: PruebaConexion
 * --------------------------------------------------
 * Esta clase se utiliza únicamente para comprobar
 * que la conexión a la base de datos funciona correctamente.
 *
 * Es una clase de PRUEBA:
 * - No forma parte de la lógica principal del ejercicio
 * - No ejecuta consultas
 * - No inserta datos
 * - No se usa en el flujo final del programa
 *
 * Su única finalidad es verificar que:
 * - El driver JDBC está bien configurado
 * - La URL, usuario y contraseña son correctos
 * - MySQL está accesible
 */
public class PruebaConexion {

	public static void main(String[] args) {

		/*
		 * Llamada al método conectar() de la clase ConexionZapatos.
		 *
		 * Si la conexión es correcta:
		 * - Se mostrará el mensaje "Conexión correcta a BDZapatos"
		 *
		 * Si hay algún problema:
		 * - Se mostrará el mensaje de error correspondiente
		 *
		 * Esta clase se suele ejecutar AL PRINCIPIO del desarrollo
		 * para detectar errores de conexión antes de continuar
		 * con el resto del ejercicio.
		 */
		ConexionZapatos.conectar();
	}
}
