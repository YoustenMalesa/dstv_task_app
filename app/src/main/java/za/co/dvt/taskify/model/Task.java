package za.co.dvt.taskify.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

/**
 * Created by YMalesa on 2017/04/10.
 */

@IgnoreExtraProperties
public class Task {
    private String taskId;
    private String title;
    private String shortDescription;
    private int isDone;
    public static final int DONE = 1;
    public static final int TO_DO = 0;
    public static final int ALL = 2;

    public Task() {

    }

    public Task(String taskId, String title, String shortDescription, int isDone) {
        this.taskId = taskId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.isDone = isDone;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int isDone() {
        return isDone;
    }

    public void setDone(int done) {
        isDone = done;
    }
}
