package za.co.dvt.taskify.persistence.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.model.Task;

/**
 * Created by YMalesa on 2017/04/12.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "taskdb.db";
    private SQLiteDatabase mSQLiteDB;

    private final String TABLE_NAME = "tbl_task";
    private final String TASK_ID    = "_id";
    private final String TASK_TITLE = "task_title";
    private final String TASK_DESC  = "task_desc";
    private final String TASK_DONE  = "task_done";

    private final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            " (" + TASK_ID + " INTEGER PRIMARY KEY, " +
            TASK_TITLE + " TEXT NOT NULL, " +
            TASK_DESC + " TEXT NOT NULL, "  +
            TASK_DONE + " INTEGER DEFAULT 0 );";

    public SQLiteDBHelper(Context pContext){
        super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
        mSQLiteDB = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createTask(Task pTask) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TITLE, pTask.getTitle());
        contentValues.put(TASK_DESC, pTask.getShortDescription());
        contentValues.put(TASK_DONE, pTask.isDone());

        mSQLiteDB.insert(TABLE_NAME, null, contentValues);
    }

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

    public Task findTaskById(String pTaskId) {
        Task vTask = null;
        Cursor res = mSQLiteDB.rawQuery("SELECT * FROM tbl_task WHERE _id = ? ", new String[]{pTaskId});
        if(res.moveToNext()) {
            vTask = new Task(); vTask.setTitle(res.getString(res.getColumnIndex(TASK_TITLE)));
            vTask.setShortDescription(res.getString(res.getColumnIndex(TASK_DESC)));
            vTask.setDone(res.getInt(res.getColumnIndex(TASK_DONE)));
            vTask.setTaskId(String.valueOf(res.getInt(res.getColumnIndex(TASK_ID))));
            return vTask;
        }
        return null;
    }

    public boolean updateTask(Task pTask) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TITLE, pTask.getTitle());
        contentValues.put(TASK_DESC, pTask.getShortDescription());
        contentValues.put(TASK_DONE, pTask.isDone());
        mSQLiteDB.update(TABLE_NAME, contentValues, "_id = ? ", new String[]{pTask.getTaskId()});
        return true;
    }

    public boolean removeTask(String pTaskId) {
        return mSQLiteDB.delete(TABLE_NAME, TASK_ID + " = ? ", new String[]{pTaskId}) > 0;
    }
}
