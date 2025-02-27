package com.tracker.util;

import com.tracker.model.Employee;
import com.tracker.model.Task;
import com.tracker.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public static List<Task> sortTasks(List<Task> tasks, String by, String order) {
        List<Task> newTasks = new ArrayList<>(tasks);

        if (by.equals("status")) {
            if (order.equals("asc")) {
                newTasks.sort(Comparator.comparingInt(task -> task.getStatus().getValue()));
            } else {
                newTasks.sort(Comparator.comparingInt(task -> -task.getStatus().getValue()));
            }
        } else if (by.equals("employee")) {
            if (order.equals("asc")) {
                newTasks.sort(Comparator.comparing(task -> task.getAssignedTo().getName()));
            } else {
                newTasks.sort(Comparator.comparing(task -> ((Task) task).getAssignedTo().getName()).reversed());
            }
        } else { /* By ID (Default) */
            if (order.equals("asc")) {
                newTasks.sort(Comparator.comparingInt(Task::getId));
            } else {
                newTasks.sort(Comparator.comparingInt(Task::getId).reversed());
            }
        }

        return newTasks;
    }

    public static List<Employee> sortEmployees(List<Employee> employees, String by, String order) {
        List<Employee> newEmployees = new ArrayList<>(employees);
        if (by.equals("name")) { /* By Name */
            if (order.equals("asc")) {
                newEmployees.sort(Comparator.comparing(Employee::getName));
            } else {
                newEmployees.sort(Comparator.comparing(Employee::getName).reversed());
            }
        } else {
            if (order.equals("asc")) {
                newEmployees.sort(Comparator.comparingInt(Employee::getId));
            } else {
                newEmployees.sort(Comparator.comparingInt(Employee::getId).reversed());
            }
        }

        return newEmployees;
    }

    public static List<Team> sortTeams(List<Team> teams, String by, String order) {
        List<Team> newTeams = new ArrayList<>(teams);
        if (by.equals("name")) { /* By Name */
            if (order.equals("asc")) {
                newTeams.sort(Comparator.comparing(Team::getName));
            } else {
                newTeams.sort(Comparator.comparing(Team::getName).reversed());
            }
        } else if (by.equals("leader")) { /* By Leader */
            if (order.equals("asc")) {
                newTeams.sort(Comparator.comparing(team -> team.getLeader().getName()));
            } else {
                newTeams.sort(Comparator.comparing(team -> ((Team) team).getLeader().getName()).reversed());
            }
        } else {
            if (order.equals("asc")) {
                newTeams.sort(Comparator.comparingInt(Team::getId));
            } else {
                newTeams.sort(Comparator.comparingInt(Team::getId).reversed());
            }
        }

        return newTeams;
    }
}
