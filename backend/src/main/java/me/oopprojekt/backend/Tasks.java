package me.oopprojekt.backend;


import me.oopprojekt.backend.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tasks implements AppActions {
    private final DBConnection db = new DBConnection();
    Connection dbConnection = db.getDbConnection();

    protected String addTaskQuery = "INSERT INTO todoapp.tasks(title, start_at, end_at, creator, content) VALUES(?,?,?,?,?)";
    protected String removeTaskQuery = "DELETE FROM todoapp.tasks WHERE uuid = ?";
    protected String modifyTaskTitleQuery = "UPDATE todoapp.tasks SET title = ? WHERE uuid = ?";
    protected String modifyTaskContentQuery = "UPDATE todoapp.tasks SET content = ? WHERE uuid = ?";
    protected String modifyTaskStartTimeQuery = "UPDATE todoapp.tasks SET start_at = ? WHERE uuid = ?";
    protected String modifyTaskEndTimeQuery = "UPDATE todoapp.tasks SET end_at = ? WHERE uuid = ?";
    protected String commentTaskQuery = "INSERT INTO todoapp.discussions";//vaja lõpetada.

    public Tasks() throws SQLException, ClassNotFoundException {
    }//Ei tea mida lisada siia atm.


    @Override
    public void addTask(String userName, String taskTitle, String taskContent, String startAt, String endAt) {
        try {
            PreparedStatement addTasktoDB = dbConnection.prepareStatement(addTaskQuery);
            addTasktoDB.setString(1,taskTitle);
            addTasktoDB.setString(2,startAt);
            addTasktoDB.setString(3,endAt);
            addTasktoDB.setString(4,userName);
            addTasktoDB.setString(5,taskContent);

            addTasktoDB.executeQuery();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void removeTask(String taskID) {
        try{
            PreparedStatement removeTasktoDB = dbConnection.prepareStatement(removeTaskQuery);
            removeTasktoDB.setString(1,taskID);

            removeTasktoDB.executeQuery();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void modifyTaskTitle(String taskID, String taskTitle) {
        try{
            PreparedStatement modifyTaskTitletoDB = dbConnection.prepareStatement(modifyTaskTitleQuery);
            modifyTaskTitletoDB.setString(1,taskTitle);
            modifyTaskTitletoDB.setString(2,taskID);

            modifyTaskTitletoDB.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void modifyTaskContent(String taskID, String taskContent) {
        try{
            PreparedStatement modifyTaskContenttoDB = dbConnection.prepareStatement(modifyTaskContentQuery);
            modifyTaskContenttoDB.setString(1,taskContent);
            modifyTaskContenttoDB.setString(2,taskID);

            modifyTaskContenttoDB.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void commentTask(String taskID, String comment, String userID) {
        try{
            PreparedStatement commentTasktoDB = dbConnection.prepareStatement(commentTaskQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void modifyTaskStartTime(String taskID, String startTime) {
        try {
            PreparedStatement modifyTaskStartTimetoDB = dbConnection.prepareStatement(modifyTaskStartTimeQuery);
            modifyTaskStartTimetoDB.setString(1,startTime);
            modifyTaskStartTimetoDB.setString(2,taskID);

            modifyTaskStartTimetoDB.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void modifyTaskEndTime(String taskID, String endTime) {
        try {
            PreparedStatement modifyTaskEndTimetoDB = dbConnection.prepareStatement(modifyTaskEndTimeQuery);
            modifyTaskEndTimetoDB.setString(1,endTime);
            modifyTaskEndTimetoDB.setString(2,taskID);

            modifyTaskEndTimetoDB.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
