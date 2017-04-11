package za.co.dvt.taskify.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.FireBaseDatabase;
import za.co.dvt.taskify.persistence.RealtimeDatabaseFactory;

public class AddTaskActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText edtTitle, edtShortDesc;
    private Spinner spnPriority;
    private ArrayAdapter<CharSequence> spinnerAdaper;
    private final String TAG = "AddTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnSend       = (Button)findViewById(R.id.btnAdd);
        edtShortDesc  = (EditText)findViewById(R.id.edtShortDesc);
        edtTitle      = (EditText)findViewById(R.id.edtTitle);
        spnPriority   = (Spinner)findViewById(R.id.spnPriority);

        spinnerAdaper = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        spinnerAdaper.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPriority.setAdapter(spinnerAdaper);

        final Task vTask = new Task();
        spnPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vPriority = parent.getItemAtPosition(position).toString();
                vTask.setPriority(vPriority);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseFactory vDBFactory = DatabaseFactory.getDatabaseFactory(DatabaseFactory.REAL_TIME_DATABASE);
                FireBaseDatabase vDatabase = vDBFactory.getFireBaseDatabase();
                try {
                    String vTitle = edtTitle.getText().toString();
                    String vDescription = edtShortDesc.getText().toString();
                    String vPriority = "LOW";
                    vTask.setPriority(vPriority);
                    vTask.setTitle(vTitle);
                    vTask.setShortDescription(vDescription);
                    SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                    vTask.setEndTime("");
                    vTask.setStartTime("");
                    vTask.setStatus("To-Do");
                    vDatabase.createTask(vTask);
                    Toast.makeText(getApplicationContext(), "Task added successfully", Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_LONG).show();
                    Log.d(TAG, ex.getMessage());
                }

            }
        });

    }


}
