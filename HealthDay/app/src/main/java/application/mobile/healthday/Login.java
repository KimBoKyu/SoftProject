package application.mobile.healthday;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    public static String u_num;
    Button b_login;
    String userNumber, password, result, message;
    EditText unText, pwdText;
    protected void alert(String message) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Login.this);
        alert_confirm.setMessage(message);
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        b_login = (Button) findViewById(R.id.login);
        b_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                unText = (EditText)findViewById(R.id.userNum);
                pwdText = (EditText)findViewById(R.id.pwd);
                userNumber = unText.getText().toString();
                password = pwdText.getText().toString();
                if(userNumber.equals("") || password.equals("")){
                    message = "회원번호, 비밀번호를 입력해주세요.";
                    alert(message);
                }
                else{
                    result = login(userNumber, password);
                    System.out.println(result);
                    u_num = userNumber;
                    if(result.equals("T")){
                        Intent intent = new Intent(Login.this, TrainerMain.class);
                        startActivity(intent) ;
                    }
                    else if(result.equals("C")){
                        Intent intent = new Intent(Login.this, UserMain.class);
                        startActivity(intent) ;
                    }
                    else{
                        message = "회원번호, 비밀번호를 확인해주세요.";
                        alert(message);
                    }
                }
            }
        });
    }
    protected String login(String un, String pw) {
        System.out.println(un + pw);
        String phpurl = "http://192.168.64.3/healthday/login.php?usernum='"+un+"'&password='"+pw+"'";
        String result = "";
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(phpurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        result = sb.toString().trim();
        System.out.println(result);
        if(result.equals("T")){
            return "T";
        }else if(result.equals("C")){
            return "C";
        }
        else{
            return "F";
        }
    }
}