package za.co.dvt.taskify.persistence;

/**
 * Created by YMalesa on 2017/04/10.
 */

public abstract class DatabaseFactory {

    public static final int REAL_TIME_DATABASE = 0;

    public abstract FireBaseDatabase getFireBaseDatabase();

    public static DatabaseFactory getDatabaseFactory(int which) {
        switch (which) {
            case REAL_TIME_DATABASE:
                return new RealtimeDatabaseFactory();
            default:
                return null;
        }
    }
}
