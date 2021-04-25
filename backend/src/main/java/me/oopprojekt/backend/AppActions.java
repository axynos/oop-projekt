package me.oopprojekt.backend;

public interface AppActions {
    void addTask(String userID, String taskTitle, String taskContent, String startTime, String endTime);
    void removeTask(String taskID);
    void modifyTaskTitle(String taskID, String taskTitle);
    void modifyTaskContent(String taskID, String taskContent);
    void commentTask(String taskID, String comment, String userID);
    void modifyTaskStartTime(String taskID, String startTime);
    void modifyTaskEndTime(String taskID, String endTime);
}
