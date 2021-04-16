package me.oopprojekt.backend.database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

public class DBConnection {
    private Connection dbConnection;

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        dbConnection = createDbConnection(loadConfigFromEnv());
    }

    private static Map<String, String> loadConfigFromEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        HashMap<String, String> config = new HashMap<>();

        config.put("host", dotenv.get("DBHOST"));
        config.put("database", dotenv.get("DBDATABASE"));
        config.put("port", dotenv.get("DBPORT"));
        config.put("user", dotenv.get("DBUSER"));
        config.put("pass", dotenv.get("DBPASS"));

        return config;
    }

    private static Connection createDbConnection(Map<String, String> config) throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%s/%s", config.get("host"), config.get("port"), config.get("database"));

        Properties props = new Properties();
        props.setProperty("user", config.get("user"));
        props.setProperty("password", config.get("pass"));
        props.setProperty("ssl", "false"); // SSL is causing some weird issues, need to investigate later.

        return DriverManager.getConnection(url, props);
    }
}
