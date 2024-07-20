package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
        // Constructor privado para impedir instanciación directa
    }

    public void connectToDatabase() throws SQLException {
        String server = "localhost";
        String port = "3306";
        String database = "sistemappizza";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            // Manejo de la excepción (puedes imprimir un mensaje de error, lanzar una nueva excepción, etc.)
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e; // Lanza la excepción para que sea manejada por el código que llamó a connectToDatabase()
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
