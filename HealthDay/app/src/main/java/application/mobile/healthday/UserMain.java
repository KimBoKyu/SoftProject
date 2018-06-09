package application.mobile.healthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by KimBoKyu on 2018. 5. 15..
 */

public class UserMain extends AppCompatActivity {
    Button logout;
    ImageButton myInfo, c_chatting, calendar, reserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);
        logout = (Button)findViewById(R.id.logout) ;
        myInfo = (ImageButton)findViewById(R.id.my_info);
        c_chatting = (ImageButton)findViewById(R.id.c_chatting);
        calendar = (ImageButton)findViewById(R.id.calendar);
        reserve = (ImageButton)findViewById(R.id.userReserve);
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMain.this, Login.class) ;
                startActivity(intent) ;
                Login.u_num = null;
            }
        });
        myInfo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMain.this, UserInfo.class) ;
                startActivity(intent) ;
            }
        });
        c_chatting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMain.this, Chat1.class) ;
                startActivity(intent) ;
            }
        });
        /*calendar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMain.this, UserReserve.class) ;
                startActivity(intent) ;
            }
        });*/
        reserve.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMain.this, UserReserve.class) ;
                startActivity(intent) ;
            }
        });
    }
}
