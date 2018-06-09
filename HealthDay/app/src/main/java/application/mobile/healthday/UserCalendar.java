package application.mobile.healthday;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

public class UserCalendar extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    final Context context = this;
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(@NonNull Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.user_calendar);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            String selectedTime;
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                final String date = month+1+" / "+dayOfMonth;
                Log.d(TAG, "onSelectedDayChange: mm/dd/yyyy: "+date);
                final String[] times = {"11:00 am-13:00 pm","15:00 am-17:00 pm","21:00 am-23:00 pm"};

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle(date+" possible time");
                alertDialogBuilder.setSingleChoiceItems(times, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                selectedTime = times[id];
                            }
                        });
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(context);
                        alertDialogBuilder2.setMessage(date + " " + selectedTime);
                        alertDialogBuilder2.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "Reserved.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder2.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),
                                        "Cancled.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog2 = alertDialogBuilder2.create();
                        alertDialog2.show();

                    }});
                alertDialogBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Cancled.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }
}