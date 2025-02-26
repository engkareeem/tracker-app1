package com.tracker.util;

import com.tracker.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public static List<Task> sortTasks(List<Task> tasks, int by, boolean asc) {
        List<Task> newTasks = new ArrayList<>(tasks);
        if(by == 0) { /* By ID */
            if(asc) {
                 newTasks.sort(Comparator.comparingInt(Task::getId));
            } else {
                newTasks.sort(Comparator.comparingInt(Task::getId).reversed());
            }
        } else if(by == 1) { /* By status */
            if(asc) {
                newTasks.sort(Comparator.comparingInt(task -> task.getStatus().getValue()));
            } else {
                newTasks.sort(Comparator.comparingInt(task -> -task.getStatus().getValue()));
            }
        }

        return newTasks;
    }
}
