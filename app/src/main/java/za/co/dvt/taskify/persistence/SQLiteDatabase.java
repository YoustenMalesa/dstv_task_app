package za.co.dvt.taskify.persistence;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.sqlitehelper.SQLiteDBHelper;

/**
 * Created by YMalesa on 2017/04/12.
 */

public class SQLiteDatabase implements Database {
    private SQLiteDBHelper mSQLiteDB;

    public SQLiteDatabase(Context pContext) {
        mSQLiteDB = new SQLiteDBHelper(pContext);
    }

    @Override
    public void createTask(Task pTask) {
        mSQLiteDB.createTask(pTask);
    }

    @Override
    public void updateTask(Task pTask) {
        mSQLiteDB.updateTask(pTask);
    }

    @Override
    public void removeTask(String pTaskId) {
        mSQLiteDB.removeTask(pTaskId);
    }

    @Override
    public Task findTask(Task pTask) {
        return mSQLiteDB.findTaskById(pTask.getTaskId());
    }

    @Override
    public List<Task> findAllTasks() {
        return mSQLiteDB.findAllTasks();
    }
}
