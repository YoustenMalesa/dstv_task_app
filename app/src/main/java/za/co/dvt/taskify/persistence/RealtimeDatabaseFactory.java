package za.co.dvt.taskify.persistence;

import android.content.Context;

/**
 * Created by YMalesa on 2017/04/10.
 */

public class RealtimeDatabaseFactory extends DatabaseFactory {

    @Override
    public Database getFireBaseDatabase() {
        return new FireBaseDatabase();
    }

    @Override
    public Database getSQLiteDatabase(Context pContext) {
        return new SQLiteDatabase(pContext);
    }
}
