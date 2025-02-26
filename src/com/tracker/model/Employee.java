package com.tracker.model;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String email;
    private Role role;
    private Team team;
    private List<Task> tasks;
    private transient String password; /* Hashed Password */

    public Employee() { }

    public Employee(int id, String name, String email, Role role, Team team, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.team = team;
        this.tasks = tasks;
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}
