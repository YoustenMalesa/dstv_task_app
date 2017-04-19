package za.co.dvt.taskify.persistence.sqlitehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "taskdb.db";
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
}
