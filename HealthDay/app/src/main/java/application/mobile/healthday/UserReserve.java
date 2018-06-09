package application.mobile.healthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserReserve extends AppCompatActivity {
    private Button btn_goCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_reserve);
        btn_goCalendar = (Button) findViewById(R.id.btn_goCalendar);
        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        btn_goCalendar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserReserve.this, UserCalendar.class);
                startActivity(intent);
            }
        });
    }
}
