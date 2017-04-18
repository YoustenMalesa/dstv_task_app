package za.co.dvt.taskify.persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.utils.GenericException;

/**
 * Created by YMalesa on 2017/04/10.
 */

public class FireBaseDatabase implements Database {
    private FirebaseDatabase vDatabase;
    private DatabaseReference mFirebase;

    public FireBaseDatabase() {
        vDatabase = FirebaseDatabase.getInstance();
        //vDatabase.setPersistenceEnabled(true);
        mFirebase = vDatabase.getReference();
    }

    @Override
    public void createTask(Task pTask) {
        String vTaskId = mFirebase.child("tasks").push().getKey();
        pTask.setTaskId(vTaskId);
        mFirebase.child("tasks").child(pTask.getTaskId()).setValue(pTask);
    }

    @Override
    public void updateTask(Task pTask){
        mFirebase.child("tasks").child(pTask.getTaskId()).setValue(pTask);
    }

    @Override
    public void removeTask(String pTaskId){
        mFirebase.child("tasks").child(pTaskId).removeValue();
    }

    @Override
    public Task findTask(Task pTask){
        return null;
    }


    @Override
    public List<Task> findAllTasks(){
       return null;
    }
}
