package za.co.dvt.taskify.persistence;

import android.content.Context;

public abstract class DatabaseFactory {

    public static final int RELATIONAL_DATABASE = 1;

    public abstract Database getSQLiteDatabase(Context pContext);

    public static DatabaseFactory getDatabaseFactory(int which) {
        switch (which) {
            case RELATIONAL_DATABASE:
                return  new RelationalDatabaseFactory();
            default:
                return null;
        }
    }
}
