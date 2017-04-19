package za.co.dvt.taskify.persistence;

import java.util.List;
import za.co.dvt.taskify.model.Task;

public interface Database {

    void createTask(Task pTask);
    void updateTask(Task pTask);
    void removeTask(String pTaskId);
    Task findTask(Task pTask);
    List<Task> findAllTasks();
}
