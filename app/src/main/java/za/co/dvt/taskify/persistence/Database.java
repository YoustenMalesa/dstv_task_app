package za.co.dvt.taskify.persistence;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.utils.GenericException;

/**
 * Created by YMalesa on 2017/04/10.
 */

public interface Database {

    void createTask(Task pTask);
    void updateTask(Task pTask);
    void removeTask(String pTaskId);
    Task findTask(Task pTask);
    List<Task> findAllTasks();
}
