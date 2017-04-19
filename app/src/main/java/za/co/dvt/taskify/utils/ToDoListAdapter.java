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

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoViewHolder> implements Filterable {
    private List<Task> mTasks = Collections.emptyList(), mFilteredList = Collections.emptyList();
    private ProgressBar mTaskProgress;
    private TextView mProgressPerc;
    private Context mContext;
    private TaskListFilter mFilter;
    private View.OnClickListener mChckBoxClickListener;

    public ToDoListAdapter(Context pContext, List<Task> pTasks, TextView pProgressPerc, ProgressBar pTaskProgress,View.OnClickListener vClickListener) {
        mTasks = pTasks;
        mProgressPerc = pProgressPerc;
        mTaskProgress = pTaskProgress;
        mContext = pContext;
        mFilteredList = mTasks;
        mChckBoxClickListener = vClickListener;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_row, parent, false);
        return new ToDoViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
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
        holder.chkMarAsDone.setOnClickListener(mChckBoxClickListener);
        holder.chkMarAsDone.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public List<Task> getTasks() {
        return mTasks;
    }
    public void setTasks(List<Task> vTasks){mTasks = vTasks;}

    @Override
    public Filter getFilter() {
        if (mFilter == null)
            mFilter = new TaskListFilter(this, mTasks);
        return mFilter;
    }
}
