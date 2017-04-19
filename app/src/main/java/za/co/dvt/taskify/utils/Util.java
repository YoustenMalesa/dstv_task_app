package za.co.dvt.taskify.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.Date;
import java.util.List;

import za.co.dvt.taskify.model.Task;

/**
 * Created by YMalesa on 2017/04/10.
 */

public class Util {

    public static int taskComplettionProgress(List<Task> pTasks) {
        int vTotalTasks = pTasks.size();
        int vDoneTasks = 0, vPercentage = 0;

        if(pTasks.size() == 0)
            return 0;

        for(int x = 0; x < pTasks.size(); x ++) {
            Task vTask = pTasks.get(x);
            if(vTask.isDone() == 1) {
                vDoneTasks ++;
            }
        }
        vPercentage = vDoneTasks * 100 / vTotalTasks;

        return vPercentage;
    }

    public static boolean validateInput(String pValue) {
        return pValue == null || pValue.isEmpty() ? false : true;
    }
}
