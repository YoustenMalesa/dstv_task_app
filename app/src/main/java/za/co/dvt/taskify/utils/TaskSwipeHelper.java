package za.co.dvt.taskify.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import za.co.dvt.taskify.model.Task;
import za.co.dvt.taskify.persistence.Database;
import za.co.dvt.taskify.persistence.DatabaseFactory;
import za.co.dvt.taskify.persistence.RelationalDatabaseFactory;

public class TaskSwipeHelper extends ItemTouchHelper.SimpleCallback {
    private ToDoListAdapter mAdapter;
    private Context mContext;

    public TaskSwipeHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public TaskSwipeHelper(ToDoListAdapter pAdapter, Context vContext) {
        super(ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT);
        mAdapter = pAdapter;
        mContext = vContext;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        DatabaseFactory vDBFactory = RelationalDatabaseFactory.getDatabaseFactory(DatabaseFactory.RELATIONAL_DATABASE);
        final Database vSQLiteDb = vDBFactory.getSQLiteDatabase(mContext);
        List<Task> vTasks = mAdapter.getTasks();
        int vTaskPosition = viewHolder.getAdapterPosition();
        vSQLiteDb.removeTask(String.valueOf(vTasks.get(vTaskPosition)));

        vTasks.remove(vTaskPosition);

        mAdapter.setTasks(vTasks);
        mAdapter.notifyItemRemoved(vTaskPosition);
        mAdapter.notifyDataSetChanged();
    }
}
