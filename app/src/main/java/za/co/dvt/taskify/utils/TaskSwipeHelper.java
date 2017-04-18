package za.co.dvt.taskify.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by YMalesa on 2017/04/13.
 */

public class TaskSwipeHelper extends ItemTouchHelper.SimpleCallback {
    private ToDoListAdapter mAdapter;

    public TaskSwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public TaskSwipeHelper(ToDoListAdapter pAdapter) {
        super(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT);
        mAdapter = pAdapter;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.removeTask(viewHolder.getAdapterPosition());
        mAdapter.updateProgressBar(mAdapter.getTasks());
    }
}
