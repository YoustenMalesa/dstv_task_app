package za.co.dvt.taskify.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.Database;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.RealtimeDatabaseFactory;
import za.co.dvt.taskify.utils.GestureListenerImpl;
import za.co.dvt.taskify.utils.TaskSwipeHelper;
import za.co.dvt.taskify.utils.ToDoListAdapter;
import za.co.dvt.taskify.utils.Util;

public class ToDoActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtTitle, edtDescription;
    private RecyclerView rcToDOItems;
    private ProgressBar mTaskProgress;
    private TextView mProgressPerc;
    private List<za.co.dvt.taskify.model.Task> mTasks = new ArrayList<>();
    private ToDoListAdapter mListAdapter;
    private LinearLayout mAddTaskSectionLayout;
    private TextInputLayout mTitleError;
    private TextInputLayout mDescriptionError;
    private GestureDetectorCompat mGestureCompat;
    private int mFilterConstraint = 0;
    public static final String TAG = "ToDoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        mainToolbar.setNavigationIcon(R.drawable.ic_sort_black_24dp);
        getSupportActionBar().setTitle(R.string.app_title);

        initComponents();

        DatabaseFactory vDBFactory  = RealtimeDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        final Database vSQLiteDb = vDBFactory.getSQLiteDatabase(getApplicationContext());

        mTasks = vSQLiteDb.findAllTasks();
        initRecycleViewer();
        initSwipeListener();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vTitle = edtTitle.getText().toString();
                String vDescription = edtDescription.getText().toString();
                if(vTitle != null && !vTitle.isEmpty() && vDescription != null && !vDescription.isEmpty()) {
                    Task vTask = new Task();

                    vTask.setShortDescription(vDescription);
                    vTask.setTitle(vTitle);
                    vTask.setDone(Task.TO_DO);

                    vSQLiteDb.createTask(vTask);
                    clearInputFields();
                    updateRecyclerView(vTask);

                    Snackbar.make(v, "Task successfully created.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    if(vTitle == null || vTitle.isEmpty()) {
                        Snackbar.make(v, "Title is required.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    if(vDescription == null || vDescription.isEmpty()) {
                        Snackbar.make(v, "Description is required.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_to_do, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int vItemId = item.getItemId();

        if(vItemId == R.id.action_instructions) {
            String vMessage = "", vTtitle = "Taskify Instructions";
            StringBuilder vBuilder = new StringBuilder();

            vBuilder.append("1. To add a task, swipe bottom of the screen from left to right,")
            .append(" Swipe from right to left when done adding. \n\n")
            .append("2. To delete a task, swipe list item from left to right. \n\n")
            .append("3. Click on the checkbox of a list item to indicate that it is done. \n");

            vMessage = vBuilder.toString();
            Util.buildDialog(ToDoActivity.this, vMessage, vTtitle).show();
        }else {
            filterTaskList();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void filterTaskList() {
        if(mFilterConstraint == Task.TO_DO) {
            mListAdapter.getFilter().filter(String.valueOf(Task.TO_DO));
            mFilterConstraint ++;
            Log.d(TAG, "onOptionsItemSelected::Filter Constraint: " + Task.TO_DO);
        }else if(mFilterConstraint == Task.DONE) {
            mListAdapter.getFilter().filter(String.valueOf(Task.DONE));
            mFilterConstraint ++;
            Log.d(TAG, "onOptionsItemSelected::Filter Constraint: " + Task.DONE);
        }else {
            mListAdapter.getFilter().filter(String.valueOf(Task.ALL));
            mFilterConstraint = Task.TO_DO;
            Log.d(TAG, "onOptionsItemSelected::Filter Constraint: " + Task.ALL);
        }
    }

    private void initRecycleViewer() {
        LinearLayoutManager vLayoutManager = new LinearLayoutManager(getApplicationContext());
        vLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mListAdapter = new ToDoListAdapter(getApplicationContext(), mTasks, mProgressPerc, mTaskProgress);
        rcToDOItems.setAdapter(mListAdapter);
        rcToDOItems.setLayoutManager(vLayoutManager);
        rcToDOItems.setItemAnimator(new DefaultItemAnimator());
    }

    private void clearInputFields() {
        edtTitle.setText(null);
        edtDescription.setText(null);
        edtTitle.clearFocus();
        edtDescription.clearFocus();
    }

    private void initComponents() {
        btnAdd         = (Button)findViewById(R.id.btn_add_item);
        edtTitle       = (EditText) findViewById(R.id.edt_item_title);
        edtDescription = (EditText) findViewById(R.id.edt_task_desc);
        rcToDOItems    = (RecyclerView)findViewById(R.id.rc_to_do_list);

        mAddTaskSectionLayout = (LinearLayout)findViewById(R.id.bottom_layout);
        mTaskProgress  = (ProgressBar)findViewById(R.id.task_progress_bar);
        mProgressPerc  = (TextView)findViewById(R.id.txt_progress);

        mTitleError = (TextInputLayout) findViewById(R.id.add_item_title_input_layout);
        mDescriptionError = (TextInputLayout) findViewById(R.id.add_item_description_input_layout);
        mGestureCompat = new GestureDetectorCompat(getApplicationContext(), new GestureListenerImpl(mAddTaskSectionLayout));
    }

    public void updateRecyclerView(Task vTask) {
        mTasks.add(vTask);
        mListAdapter.notifyDataSetChanged();
    }
    private void hideAddTaskSection(View pView) {
        pView.setVisibility(View.GONE);
    }

    public void initSwipeListener() {
        ItemTouchHelper.Callback vSwipeCallback = new TaskSwipeHelper(mListAdapter);
        ItemTouchHelper vHelper = new ItemTouchHelper(vSwipeCallback);
        vHelper.attachToRecyclerView(rcToDOItems);
    }

}
