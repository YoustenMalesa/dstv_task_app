package za.co.dvt.taskify.persistence;

import android.content.Context;

/**
 * Created by YMalesa on 2017/04/10.
 */

public abstract class DatabaseFactory {

    public static final int REAL_TIME_DATABASE = 0;
    public static final int RELATIONAL_DATABASE = 1;

    public abstract Database getFireBaseDatabase();
    public abstract Database getSQLiteDatabase(Context pContext);

    public static DatabaseFactory getDatabaseFactory(int which) {
        switch (which) {
            case REAL_TIME_DATABASE:
                return new RealtimeDatabaseFactory();
            case RELATIONAL_DATABASE:
                return  new RelationalDatabaseFactory();
            default:
                return null;
        }
    }
}
