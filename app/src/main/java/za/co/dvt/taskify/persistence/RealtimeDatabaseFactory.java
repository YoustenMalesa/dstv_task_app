package za.co.dvt.taskify.persistence;

/**
 * Created by YMalesa on 2017/04/10.
 */

public class RealtimeDatabaseFactory extends DatabaseFactory {

    @Override
    public FireBaseDatabase getFireBaseDatabase() {
        return new FireBaseDatabase();
    }
}
