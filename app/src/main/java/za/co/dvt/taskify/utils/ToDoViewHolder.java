package za.co.dvt.taskify.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import za.co.dvt.taskify.R;

public class ToDoViewHolder extends RecyclerView.ViewHolder {
    protected TextView txtTitle, txtDesctription;
    protected CheckBox chkMarAsDone;

    public ToDoViewHolder(View itemView) {
        super(itemView);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_item_title);
        txtDesctription = (TextView) itemView.findViewById(R.id.txt_item_description);
        chkMarAsDone = (CheckBox) itemView.findViewById(R.id.chk_done);
    }


}

