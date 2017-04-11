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
    private String startTime;
    private String endTime;
    private String status;
    private String priority;

    public Task() {

    }

    public Task(String taskId, String title, String shortDescription, String startTime, String endTime, String status, String priority) {
        this.taskId = taskId;
        this.title = title;
        this.shortDescription = shortDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.priority = priority;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
