package com.tracker.model;

public class Team {
    private int id;
    private String name;
    private Employee leader;

    public Team() {

    }

    public Team(int id, String name, Employee leader) {
        this.id = id;
        this.name = name;
        this.leader = leader;
    }

    public Team(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Employee getLeader() {
        return leader;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }
}
