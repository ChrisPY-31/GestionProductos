package BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionPostgreSQL {
    private Connection connection;
    private String usuario = "postgres"; // Usuario de PostgreSQL
    private String password = "eytFkUccrxxPpBuZjyCvdYVfnCjFLcGd"; // Cambia a la contraseña de tu usuario de PostgreSQL
    private String servidor = "autorack.proxy.rlwy.net";
    private String puerto = "18347"; // Puerto predeterminado de PostgreSQL
    private String nombreBD = "railway";

    // Concatenar la URL de conexión para PostgreSQL
    private String url = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + nombreBD;

    private String driver = "org.postgresql.Driver"; // Driver de PostgreSQL

    public ConexionPostgreSQL() {

        verificarVariables();

            //  variables de entorno
            usuario = System.getenv("DB_USER");
            password = System.getenv("DB_PASSWORD");
            servidor = System.getenv("DB_HOST");
            puerto = System.getenv("DB_PORT");
            nombreBD = System.getenv("DB_NAME");

            url = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + nombreBD;

        try {
            // Cargar el driver de PostgreSQL
            Class.forName(driver);

            // Crear la conexión y asignarla a la variable connection
            connection = DriverManager.getConnection(url, usuario, password);

            if (connection != null) {
                System.out.println("Conexión exitosa a PostgreSQL");
            }

        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos PostgreSQL");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Detalle del error: ");
            e.printStackTrace();
        }
    }

    private void verificarVariables() {
        if (System.getenv("DB_HOST") == null ||
                System.getenv("DB_NAME") == null ||
                System.getenv("DB_USER") == null ||
                System.getenv("DB_PASSWORD") == null ||
                System.getenv("DB_PORT") == null) {
            throw new IllegalStateException("Algunas variables de entorno necesarias no están configuradas.");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

