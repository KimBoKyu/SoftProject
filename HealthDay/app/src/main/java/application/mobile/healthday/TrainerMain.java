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

public class TrainerMain extends AppCompatActivity {
    Button logout;
    ImageButton t_chatting, manage_c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_main);
        logout = (Button)findViewById(R.id.logout) ;
        t_chatting = (ImageButton)findViewById(R.id.t_chatting);
        manage_c = (ImageButton)findViewById(R.id.manage_c);
        logout.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerMain.this, Login.class) ;
                startActivity(intent) ;
                Login.u_num = null;
            }
        });
        t_chatting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerMain.this, Chat1.class) ;
                startActivity(intent) ;
            }
        });
        manage_c.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerMain.this, TrainerManage.class) ;
                startActivity(intent) ;
            }
        });    }
}
