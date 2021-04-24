package me.oopprojekt.backend.database;

import java.sql.*;


public class DBData {
    private DBConnection db = new DBConnection();

    public DBData() throws SQLException, ClassNotFoundException {
    }

    protected String insertUserPublicQuery = "INSERT INTO todoapp.users(name, avatar) VALUES(?,?)";
    protected String insertUserPrivateQuery = "INSERT INTO todoapp_private.users(uuid, email, github_id) VALUES(?,?,?)";
    public void insertUser(String name, String avatar) throws SQLException {
        Connection dbConnection = db.getDbConnection();

        try {
            PreparedStatement insertUserPublic = dbConnection.prepareStatement(insertUserPublicQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement insertUserPrivate = dbConnection.prepareStatement(insertUserPrivateQuery);

            insertUserPublic.setString(1, "Mari Maasikas");
            insertUserPublic.setString(2, "https://example.com/test.png");

            insertUserPublic.executeUpdate();//probleem

            ResultSet results = insertUserPublic.getGeneratedKeys();
            results.next();

            String uuid = results.getString(1);

            insertUserPrivate.setString(1, uuid);
            insertUserPrivate.setString(2, "marimaasikas@gmail.com");
            insertUserPrivate.setString(3, "12397861");

            insertUserPrivate.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
