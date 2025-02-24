package com.tracker.model;

import java.util.List;

public class Employee {
    private int id;
    private String name;
    private String contact;
    private Role role;
    private Team team;
    private List<Task> tasks;

    public Employee() { }

    public Employee(int id, String name, String contact, Role role, Team team, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.role = role;
        this.team = team;
        this.tasks = tasks;
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
}
