package com.tracker.model;

public enum TaskStatus {
    PENDING(0, "Pending"),
    TO_DO(1, "To Do"),
    IN_PROGRESS(2, "In Progress"),
    DONE(3, "Done");

    private final int value;
    private final String label;

    private TaskStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static TaskStatus fromValue(int value) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return null;
    }
}