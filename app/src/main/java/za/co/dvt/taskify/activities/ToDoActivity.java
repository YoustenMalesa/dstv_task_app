package za.co.dvt.taskify.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.Database;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.RelationalDatabaseFactory;
import za.co.dvt.taskify.utils.TaskSwipeHelper;
import za.co.dvt.taskify.utils.ToDoListAdapter;
import za.co.dvt.taskify.utils.Util;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rcToDOItems;
    private ProgressBar mTaskProgress;
    private TextView mProgressPerc;
    private List<za.co.dvt.taskify.model.Task> mTasks = new ArrayList<>();
    private ToDoListAdapter mListAdapter;
    public static final String TAG = "ToDoActivity";
    private DatabaseFactory vDBFactory;
    private Database vSQLiteDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_main);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(R.string.app_title);

        initComponents();

        vDBFactory = RelationalDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        vSQLiteDb = vDBFactory.getSQLiteDatabase(ToDoActivity.this);
        mTasks = vSQLiteDb.findAllTasks();

        initRecycleViewer();
        initSwipeListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vSwitch = new Intent(getApplicationContext(), AddTaskActivity.class);
                startActivity(vSwitch);
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

        if (vItemId == R.id.action_view_all) {
            filterTaskList(Task.ALL);
        } else if (vItemId == R.id.action_view_done) {
            filterTaskList(Task.DONE);
        } else {
            filterTaskList(Task.TO_DO);
        }

        return super.onOptionsItemSelected(item);
    }

    private void filterTaskList(int vState) {
        mListAdapter.getFilter().filter(String.valueOf(vState));
    }

    private void initRecycleViewer() {
        LinearLayoutManager vLayoutManager = new LinearLayoutManager(getApplicationContext());
        vLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mListAdapter = new ToDoListAdapter(getApplicationContext(), mTasks, mProgressPerc, mTaskProgress, this);
        rcToDOItems.setAdapter(mListAdapter);
        rcToDOItems.setLayoutManager(vLayoutManager);
        rcToDOItems.setItemAnimator(new DefaultItemAnimator());
    }

    private void initComponents() {
        rcToDOItems = (RecyclerView) findViewById(R.id.rc_to_do_list);
        mTaskProgress = (ProgressBar) findViewById(R.id.task_progress_bar);
        mProgressPerc = (TextView) findViewById(R.id.txt_progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTasks = vSQLiteDb.findAllTasks();
        mListAdapter.setTasks(mTasks);
        mListAdapter.notifyDataSetChanged();
    }

    public void initSwipeListener() {
        ItemTouchHelper.Callback vSwipeCallback = new TaskSwipeHelper(mListAdapter, getApplicationContext());
        ItemTouchHelper vHelper = new ItemTouchHelper(vSwipeCallback);
        vHelper.attachToRecyclerView(rcToDOItems);
    }

    @Override
    public void onClick(View v) {
        CheckBox vChckDone = (CheckBox) v;
        RecyclerView.ViewHolder vHolder = (RecyclerView.ViewHolder) vChckDone.getTag();
        Task vTask = mTasks.get(vHolder.getAdapterPosition());
        int isDone = vChckDone.isChecked() ? Task.DONE : Task.TO_DO;

        vTask.setDone(isDone);
        vSQLiteDb.updateTask(vTask);

        mTasks = vSQLiteDb.findAllTasks();
        mTaskProgress.setProgress(Util.taskComplettionProgress(mTasks));
        mProgressPerc.setText(Util.taskComplettionProgress(mTasks) + "%");
    }

}
