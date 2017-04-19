package za.co.dvt.taskify.persistence;

import android.content.Context;

public class RelationalDatabaseFactory extends DatabaseFactory {

    @Override
    public Database getSQLiteDatabase(Context pContext) {
        return new SQLiteDatabase(pContext);
    }
}
