package com.taskmanagement.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.taskmanagement.model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    static {
        // Create data directory if it doesn't exist
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Generic method to save list to JSON file
    public static <T> void saveToFile(List<T> list, String filename) {
        try (Writer writer = new FileWriter(DATA_DIR + "/" + filename)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic method to load list from JSON file
    public static <T> List<T> loadFromFile(String filename, Type typeToken) {
        File file = new File(DATA_DIR + "/" + filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            List<T> list = gson.fromJson(reader, typeToken);
            return list != null ? list : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Specific methods for each entity type
    public static void saveUsers(List<User> users) {
        saveToFile(users, "users.json");
    }

    public static List<User> loadUsers() {
        Type type = new TypeToken<List<User>>(){}.getType();
        return loadFromFile("users.json", type);
    }

    public static void saveEmployees(List<Employee> employees) {
        saveToFile(employees, "employees.json");
    }

    public static List<Employee> loadEmployees() {
        Type type = new TypeToken<List<Employee>>(){}.getType();
        return loadFromFile("employees.json", type);
    }

    public static void saveTasks(List<Task> tasks) {
        saveToFile(tasks, "tasks.json");
    }

    public static List<Task> loadTasks() {
        Type type = new TypeToken<List<Task>>(){}.getType();
        return loadFromFile("tasks.json", type);
    }

    public static void saveProjects(List<Project> projects) {
        saveToFile(projects, "projects.json");
    }

    public static List<Project> loadProjects() {
        Type type = new TypeToken<List<Project>>(){}.getType();
        return loadFromFile("projects.json", type);
    }

    public static void saveTaskLogs(List<TaskLog> taskLogs) {
        saveToFile(taskLogs, "tasklogs.json");
    }

    public static List<TaskLog> loadTaskLogs() {
        Type type = new TypeToken<List<TaskLog>>(){}.getType();
        return loadFromFile("tasklogs.json", type);
    }

    public static void saveTimecards(List<Timecard> timecards) {
        saveToFile(timecards, "timecards.json");
    }

    public static List<Timecard> loadTimecards() {
        Type type = new TypeToken<List<Timecard>>(){}.getType();
        return loadFromFile("timecards.json", type);
    }

    public static void saveLeaveRequests(List<LeaveRequest> leaveRequests) {
        saveToFile(leaveRequests, "leaverequests.json");
    }

    public static List<LeaveRequest> loadLeaveRequests() {
        Type type = new TypeToken<List<LeaveRequest>>(){}.getType();
        return loadFromFile("leaverequests.json", type);
    }

    public static void saveMissionRequests(List<MissionRequest> missionRequests) {
        saveToFile(missionRequests, "missionrequests.json");
    }

    public static List<MissionRequest> loadMissionRequests() {
        Type type = new TypeToken<List<MissionRequest>>(){}.getType();
        return loadFromFile("missionrequests.json", type);
    }
}
