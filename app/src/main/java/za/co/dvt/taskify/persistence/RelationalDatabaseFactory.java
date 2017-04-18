package za.co.dvt.taskify.persistence;

import android.content.Context;

/**
 * Created by YMalesa on 2017/04/12.
 */

public class RelationalDatabaseFactory extends DatabaseFactory {
    @Override
    public Database getFireBaseDatabase() {
        return new FireBaseDatabase();
    }

    @Override
    public Database getSQLiteDatabase(Context pContext) {
        return new SQLiteDatabase(pContext);
    }
}
