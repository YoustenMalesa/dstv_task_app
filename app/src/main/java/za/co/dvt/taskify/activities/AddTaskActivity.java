package za.co.dvt.taskify.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.Database;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.RealtimeDatabaseFactory;

public class AddTaskActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText edtTitle, edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle(R.string.add_task_title);

        initComponents();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vTitle = edtTitle.getText().toString();
                String vDescription = edtDescription.getText().toString();

                if (!validateInput(vTitle)) {
                    edtTitle.setError("Title is required.");
                    return;
                }

                if (!validateInput(vDescription)) {
                    edtDescription.setError("Description is required");
                    return;
                }

                DatabaseFactory vDBFactory = RealtimeDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
                final Database vSQLiteDb = vDBFactory.getSQLiteDatabase(getApplicationContext());
                Task vTask = new Task();

                vTask.setShortDescription(vDescription);
                vTask.setTitle(vTitle);
                vTask.setDone(Task.TO_DO);
                vSQLiteDb.createTask(vTask);

                Toast.makeText(getApplicationContext(), "Task successfully created.", Toast.LENGTH_LONG).show();

                finish();
            }
        });
    }

    private boolean validateInput(String pValue) {
        return pValue == null || pValue.isEmpty() ? false : true;
    }

    private void initComponents() {
        btnAdd = (Button) findViewById(R.id.btn_add_task);
        edtTitle = (EditText) findViewById(R.id.edt_item_title);
        edtDescription = (EditText) findViewById(R.id.edt_task_desc);
    }
}
