package za.co.dvt.taskify.persistence;

import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.utils.GenericException;

/**
 * Created by YMalesa on 2017/04/10.
 */

public interface Database {

    void createTask(Task pTask) throws GenericException;
    void updateTask(Task pTask) throws GenericException;
    void removeTask(String pTaskId) throws GenericException;
    Task findTask(Task pTask) throws GenericException;
    List<Task> findAllTasks() throws GenericException;
    List<Task> findTasksByStatus(String pStatus) throws GenericException;
}
