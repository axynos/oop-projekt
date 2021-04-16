package me.oopprojekt.backend;

public interface AppActions {
    void addTask(String userID, String taskTitle, String taskContent);
    void removeTask(String taskID);
    void modifyTaskTitle(String taskID, String taskTitle);
    void modifyTaskContent(String taskID, String taskContent);
    void commentTask(String taskID, String Comment);
}
