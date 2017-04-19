package za.co.dvt.taskify.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

import za.co.dvt.taskify.R;
import za.co.dvt.taskify.model.Task;

/**
 * Created by YMalesa on 2017/04/19.
 */

public class ToDoViewHolder extends RecyclerView.ViewHolder {
    protected TextView txtTitle, txtDesctription;
    protected CheckBox chkMarAsDone;
    protected int mIndex;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
        txtDesctription = (TextView) itemView.findViewById(R.id.txt_item_description);
        chkMarAsDone = (CheckBox) itemView.findViewById(R.id.chk_done);
        Log.d("ToDoListViewHolder", "ToDoListViewHolder::Setting properties");
    }


}

