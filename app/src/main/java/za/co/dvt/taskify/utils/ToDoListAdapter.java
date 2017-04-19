package za.co.dvt.taskify.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.Database;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.RelationalDatabaseFactory;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> implements Filterable {
    private LayoutInflater mInflater;
    protected List<Task> mTasks = Collections.emptyList(), mFilteredList = Collections.emptyList();
    private ProgressBar mTaskProgress;
    private TextView mProgressPerc;
    private Context mContext;
    private TaskListFilter mFilter;

    public ToDoListAdapter(Context pContext, List<Task> pTasks, TextView pProgressPerc, ProgressBar pTaskProgress) {
        mInflater = LayoutInflater.from(pContext);
        mTasks = pTasks;
        mProgressPerc = pProgressPerc;
        mTaskProgress = pTaskProgress;
        mContext = pContext;
        mFilteredList = mTasks;
    }

    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vView = mInflater.inflate(R.layout.to_do_list_row, parent, false);
        ToDoListViewHolder vListViewHolder = new ToDoListViewHolder(vView);
        return vListViewHolder;
    }

    @Override
    public void onBindViewHolder(ToDoListViewHolder holder, int position) {
        Task vTask = mTasks.get(position);
        Log.d("ToDoListAdapter", "onBindViewHolder::" + vTask.getShortDescription());
        holder.txtTitle.setText(vTask.getTitle());
        holder.txtDesctription.setText(vTask.getShortDescription());
        if (vTask.isDone() == Task.DONE)
            holder.chkMarAsDone.setChecked(true);
        else
            holder.chkMarAsDone.setChecked(false);
        mTaskProgress.setProgress(Util.taskComplettionProgress(mTasks));
        mProgressPerc.setText(Util.taskComplettionProgress(mTasks) + "%");
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    void updateProgressBar(List<Task> pTasks) {
        mTaskProgress.setProgress(Util.taskComplettionProgress(mTasks));
        mProgressPerc.setText(Util.taskComplettionProgress(mTasks) + "%");
    }

    public void removeTask(int pPosition) {
        DatabaseFactory vDBFactory = RelationalDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        Database vSQLiteDB = vDBFactory.getSQLiteDatabase(mContext);

        vSQLiteDB.removeTask(mTasks.get(pPosition).getTaskId());
        mTasks.remove(pPosition);
        notifyItemRemoved(pPosition);
        notifyItemRangeChanged(pPosition, mTasks.size());
    }

    public void updateTask(Task pTask) {
        DatabaseFactory vDBFactory = RelationalDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        Database vSQLiteDB = vDBFactory.getSQLiteDatabase(mContext);

        vSQLiteDB.updateTask(pTask);
        mTasks = vSQLiteDB.findAllTasks();
        notifyDataSetChanged();

    }

    public void updateTask() {
        DatabaseFactory vDBFactory = RelationalDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        Database vSQLiteDB = vDBFactory.getSQLiteDatabase(mContext);

        mTasks = vSQLiteDB.findAllTasks();
        notifyDataSetChanged();
    }

    public List<Task> getTasks() {
        return mTasks;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new TaskListFilter(this, mTasks);
        return mFilter;
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitle, txtDesctription;
        private CheckBox chkMarAsDone;
        private int mIndex;

        public ToDoListViewHolder(View itemView) {
            super(itemView);
            initComponents(itemView);
            Log.d("ToDoListViewHolder", "ToDoListViewHolder::Setting properties");
        }

        private void initComponents(View itemView) {
            txtTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
            txtDesctription = (TextView) itemView.findViewById(R.id.txt_item_description);
            chkMarAsDone = (CheckBox) itemView.findViewById(R.id.chk_done);
            chkMarAsDone.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //handle mark as done action
            Log.d("ToDoListHolder", "ponClick::Button MarkAsDone clicked::Index->" + mIndex);
            Task vTask = mTasks.get(mIndex);
            CheckBox chkMarkAsDone = (CheckBox) v;

            int isDone = chkMarkAsDone.isChecked() ? Task.DONE : Task.TO_DO;
            vTask.setDone(isDone);
            updateTask(mTasks.get(mIndex));
            updateProgressBar(mTasks);
        }

        public void setPosition(int pPosition) {
            mIndex = pPosition;
        }

    }

}
