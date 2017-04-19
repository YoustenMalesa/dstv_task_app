package za.co.dvt.taskify.utils;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.taskify.model.Task;

public class TaskListFilter extends Filter {
    private ToDoListAdapter mAdapter;
    private List<Task> mTasks;

    public TaskListFilter(ToDoListAdapter pAdapter, List<Task> pTasks) {
        mAdapter = pAdapter;
        mTasks = pTasks;
        Log.d("TaskListFilter", "performFiltering:: List Before Filtering: " + mTasks.size());
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults vResults = new FilterResults();

        int vConstraint = -1;
        if (constraint != null && constraint.length() > 0) {
            vConstraint = Integer.parseInt(String.valueOf(constraint));
            Log.d("TaskListFilter", "performFiltering::Constraint: " + vConstraint);
            List<Task> vFilteredList = new ArrayList<>();

            if (vConstraint == Task.ALL) {
                vResults.count = mTasks.size();
                vResults.values = mTasks;
                return vResults;
            }

            for (int x = 0; x < mTasks.size(); x++) {
                if (mTasks.get(x).isDone() == vConstraint) {
                    vFilteredList.add(mTasks.get(x));
                }
            }

            vResults.count = vFilteredList.size();
            vResults.values = vFilteredList;

        } else {
            vResults.count = mTasks.size();
            vResults.values = mTasks;
        }

        return vResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        mAdapter.setTasks((List<Task>) results.values);
        Log.d("TaskListFilter", "publishResults::Filtered List: " + mTasks.size());
        mAdapter.notifyDataSetChanged();
    }
}
