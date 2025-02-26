package com.tracker.model;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private Employee leader;
    private List<Employee> members;

    public Team() {
        this.members = null;
    }

    public Team(int id, String name, Employee leader) {
        this();
        this.id = id;
        this.name = name;
        this.leader = leader;
    }

    public Team(int id) {
        this();
        this.id = id;
    }

    public Team(String name, int leaderId) {
        this();
        this.name = name;
        this.leader = new Employee(leaderId);
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

    public Employee getLeader() {
        return leader;
    }

    public void setLeader(Employee leader) {
        this.leader = leader;
    }

    public List<Employee> getMembers() {
        return members;
    }

    public void setMembers(List<Employee> members) {
        this.members = members;
    }

    public void addMember(Employee member) {
        this.members.add(member);
    }
}
