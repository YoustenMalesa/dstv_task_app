package za.co.dvt.taskify.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.sqlitehelper.SQLiteDBHelper;

public class SQLiteDatabase implements Database {
    private android.database.sqlite.SQLiteDatabase mSQLiteDB;
    private final String TABLE_NAME = "tbl_task";
    private final String TASK_ID    = "_id";
    private final String TASK_TITLE = "task_title";
    private final String TASK_DESC  = "task_desc";
    private final String TASK_DONE  = "task_done";

    public SQLiteDatabase(Context pContext) {
        mSQLiteDB = new SQLiteDBHelper(pContext).getReadableDatabase();
    }

    @Override
    public void createTask(Task pTask) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TITLE, pTask.getTitle());
        contentValues.put(TASK_DESC, pTask.getShortDescription());
        contentValues.put(TASK_DONE, pTask.isDone());

        mSQLiteDB.insert(TABLE_NAME, null, contentValues);
    }

    @Override
    public void updateTask(Task pTask) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TITLE, pTask.getTitle());
        contentValues.put(TASK_DESC, pTask.getShortDescription());
        contentValues.put(TASK_DONE, pTask.isDone());
        mSQLiteDB.update(TABLE_NAME, contentValues, "_id = ? ", new String[]{pTask.getTaskId()});
    }

    @Override
    public void removeTask(String pTaskId) {
        mSQLiteDB.delete(TABLE_NAME, TASK_ID + " = ? ", new String[]{pTaskId});
    }

    @Override
    public Task findTask(Task pTask) {
        Task vTask = null;
        Cursor res = mSQLiteDB.rawQuery("SELECT * FROM tbl_task WHERE _id = ? ", new String[]{pTask.getTaskId()});
        if(res.moveToNext()) {
            vTask = new Task(); vTask.setTitle(res.getString(res.getColumnIndex(TASK_TITLE)));
            vTask.setShortDescription(res.getString(res.getColumnIndex(TASK_DESC)));
            vTask.setDone(res.getInt(res.getColumnIndex(TASK_DONE)));
            vTask.setTaskId(String.valueOf(res.getInt(res.getColumnIndex(TASK_ID))));
            return vTask;
        }
        return null;
    }

    @Override
    public List<Task> findAllTasks() {
        List<Task> vTasks = new ArrayList<>();
        Task vTask = null;
        Cursor res = mSQLiteDB.rawQuery("SELECT * FROM tbl_task ORDER BY _id ASC", null);
        res.moveToFirst();

        while (res.moveToNext()) {
            vTask = new Task();
            vTask.setTitle(res.getString(res.getColumnIndex(TASK_TITLE)));
            vTask.setShortDescription(res.getString(res.getColumnIndex(TASK_DESC)));
            vTask.setDone(res.getInt(res.getColumnIndex(TASK_DONE)));
            vTask.setTaskId(String.valueOf(res.getInt(res.getColumnIndex(TASK_ID))));
            vTasks.add(vTask);
        }
        return vTasks;
    }
}
