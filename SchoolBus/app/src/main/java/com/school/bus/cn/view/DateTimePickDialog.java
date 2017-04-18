package com.school.bus.cn.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.school.bus.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhang on 2017/4/18.
 */

public class DateTimePickDialog implements DatePicker.OnDateChangedListener, TimePicker.OnTimeChangedListener {
    Activity activity;
    DatePicker datePicker;
    TimePicker timePicker;
    AlertDialog ad;
    private String dateTime;
    private String initDateTime;

    public DateTimePickDialog(Activity activity) {
        this.activity = activity;
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        initDateTime = calendar.get(Calendar.YEAR) + "-"
                + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE)+":00";
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }
    public AlertDialog dateTimePicKDialog(final EditText inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_datetime, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        ad = new AlertDialog.Builder(activity)
                .setTitle(initDateTime)
                .setView(dateTimeLayout)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText("");
                    }
                }).show();

        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTime = sdf.format(calendar.getTime());
        ad.setTitle(dateTime);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }
}
