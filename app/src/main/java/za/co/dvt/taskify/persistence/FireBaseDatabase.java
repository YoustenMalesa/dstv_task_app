package za.co.dvt.taskify.persistence;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.utils.GenericException;

/**
 * Created by YMalesa on 2017/04/10.
 */

public class FireBaseDatabase implements Database {
    private DatabaseReference mFirebase = FirebaseDatabase.getInstance().getReference();
    private List<Task> mTasks = new ArrayList<>();

    @Override
    public void createTask(Task pTask) throws GenericException {
        String vTaskId = mFirebase.child("tasks").push().getKey();
        pTask.setTaskId(vTaskId);
        mFirebase.child("tasks").child(pTask.getTaskId()).setValue(pTask);
    }

    @Override
    public void updateTask(Task pTask) throws GenericException{
        mFirebase.child("tasks").child(pTask.getTaskId()).setValue(pTask);
    }

    @Override
    public void removeTask(String pTaskId) throws GenericException{
        mFirebase.child("tasks").child(pTaskId).removeValue();
    }

    @Override
    public Task findTask(Task pTask) throws GenericException{
        findAllTasks();
        for (Task vTask: mTasks) {
            if(vTask == pTask) {
                return vTask;
            }
        }
        return null;
    }

    @Override
    public List<Task> findAllTasks() throws GenericException{
        mFirebase.child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> vChildren = dataSnapshot.getChildren();
                for (DataSnapshot vChild: vChildren) {
                    Task vTask = vChild.getValue(Task.class);
                    mTasks.add(vTask);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });
        return mTasks;
    }

    @Override
    public List<Task> findTasksByStatus(String pStatus) throws GenericException {
        findAllTasks();
        List<Task> vTaskByCat = new ArrayList<>();
        for (Task vTask: mTasks) {
            if(vTask.getStatus().equalsIgnoreCase(pStatus)) {
                vTaskByCat.add(vTask);
            }
        }
        return vTaskByCat;
    }
}
