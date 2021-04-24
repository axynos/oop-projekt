package me.oopprojekt.backend;


import me.oopprojekt.backend.database.DBConnection;

import java.sql.SQLException;

public class Tasks implements AppActions {
    private DBConnection db = new DBConnection();

    public Tasks() throws SQLException, ClassNotFoundException {
    }

    private String addTaskQuery = "INSERT INTO todoapp.tasks(title, start_at, end_at, creator, content) VALUES(?,?,?,?,?)";
    private String removeTaskQuery = "DELETE FROM todoapp.tasks WHERE uuid = ?";
    private String modifyTaskTitleQuery ="UPDATE todoapp.tasks SET title = ? WHERE uuid = ?";
    private String modifyTaskContentQuery = "UPDATE todoapp.tasks SET content = ? WHERE uuid = ?";
    private String modifyTaskStartTimeQuery = "UPDATE todoapp.tasks SET start_at = ? WHERE uuid = ?";
    private String modifyTaskEndTimeQuery = "UPDATE todoapp.tasks SET end_at = ? WHERE uuid = ?";
    private String commentTaskQuery = "INSERT INTO todoapp.discussions(";


    @Override
    public void addTask(String userID, String taskTitle, String taskContent, String startAt, String endAt) {

    }

    @Override
    public void removeTask(String taskID) {

    }

    @Override
    public void modifyTaskTitle(String taskID, String taskTitle) {

    }

    @Override
    public void modifyTaskContent(String taskID, String taskContent) {

    }

    @Override
    public void commentTask(String taskID, String comment, String userID) {

    }

    @Override
    public void modifyTaskStartTime(String taskID, String startTime) {

    }

    @Override
    public void modifyTaskEndTime(String taskID, String endTime) {

    }
}
