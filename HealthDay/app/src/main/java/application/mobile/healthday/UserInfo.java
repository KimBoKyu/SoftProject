package application.mobile.healthday;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserInfo extends AppCompatActivity {
    Button info_ch;
    JSONObject info;
    String val, g_name, g_regi,
            g_wp, g_wo, g_wnch,
            g_bp, g_bo, g_bnch,
            g_fp, g_fo, g_fnch,
            c_w, c_b, c_f,
            r_n;
    TextView name, register,
            weight_p, weight_o, weight_nch,
            bone_p, bone_o, bone_nch,
            fat_p, fat_o, fat_nch,
            remain_num;
    EditText weight_ch, bone_ch, fat_ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        name = (TextView) findViewById(R.id.name);
        register = (TextView) findViewById(R.id.register);
        weight_p = (TextView)  findViewById(R.id.weight_p);
        weight_o = (TextView)  findViewById(R.id.weight_o);
        weight_nch = (TextView)findViewById(R.id.weight_nch);
        bone_p = (TextView) findViewById(R.id.bone_p);
        bone_o = (TextView) findViewById(R.id.bone_o);
        bone_nch = (TextView) findViewById(R.id.bone_nch);
        fat_p = (TextView) findViewById(R.id.fat_p);
        fat_o = (TextView) findViewById(R.id.fat_o);
        fat_nch = (TextView) findViewById(R.id.fat_nch);
        remain_num = (TextView) findViewById(R.id.remain_num);
        init();
        info_ch = (Button) findViewById(R.id.info_change);
        info_ch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = info_ch.getText().toString();
                weight_ch = (EditText) findViewById(R.id.weight_ch);
                bone_ch = (EditText) findViewById(R.id.bone_ch);
                fat_ch = (EditText) findViewById(R.id.fat_ch);
                if(val.equals("수정하기")){
                    weight_nch.setVisibility(View.GONE);
                    bone_nch.setVisibility(View.GONE);
                    fat_nch.setVisibility(View.GONE);
                    weight_ch.setVisibility(View.VISIBLE);
                    weight_ch.requestFocus();
                    bone_ch.setVisibility(View.VISIBLE);
                    fat_ch.setVisibility(View.VISIBLE);
                    info_ch.setText("수정완료");
                }
                else {
                    updateInfo(Login.u_num);
                    weight_nch.setVisibility(View.VISIBLE);
                    bone_nch.setVisibility(View.VISIBLE);
                    fat_nch.setVisibility(View.VISIBLE);
                    weight_ch.setVisibility(View.GONE);
                    bone_ch.setVisibility(View.GONE);
                    fat_ch.setVisibility(View.GONE);
                    info_ch.setText("수정하기");
                }
            }
        });
    }
    void init(){
        info = getInfo(Login.u_num);
        try{
            g_name = (String)info.get("name");
            g_regi = (String)info.get("regiDate");
            g_wp = (String)info.get("weight_p");
            g_wo = (String)info.get("weight_o");
            g_wnch = (String)info.get("weight_c");
            g_bp = (String)info.get("bone_p");
            g_bo = (String)info.get("bone_o");
            g_bnch = (String)info.get("bone_c");
            g_fp = (String)info.get("fat_p");
            g_fo = (String)info.get("fat_o");
            g_fnch = (String)info.get("fat_c");
            r_n = (String)info.get("remainNum");

        }catch (Exception e){}
        name.setText(g_name);
        register.setText(g_regi);
        weight_p.setText(g_wp);
        weight_o.setText(g_wo);
        weight_nch.setText(g_wnch);
        bone_p.setText(g_bp);
        bone_o.setText(g_bo);
        bone_nch.setText(g_bnch);
        fat_p.setText(g_fp);
        fat_o.setText(g_fo);
        fat_nch.setText(g_fnch);
        remain_num.setText(r_n);
    }
    public void updateInfo(String un){
        c_w = weight_ch.getText().toString();
        c_b = bone_ch.getText().toString();
        c_f = fat_ch.getText().toString();
        String phpurl = "http://192.168.64.3/healthday/updateInfo.php?usernum='"+
                un+"'&c_w="+c_w+"&c_b="+c_b+"&c_f="+c_f;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(phpurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }
        weight_nch.setText(c_w);
        bone_nch.setText(c_b);
        fat_nch.setText(c_f);
    }



    public JSONObject getInfo(String un) {
        System.out.println(un);
        String phpurl = "http://192.168.64.3/healthday/getInfo.php?un="+un;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        JSONObject res;
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
        try{
            res = new JSONObject(sb.toString());
            System.out.println(res);
            return res;
        } catch (JSONException e){

        }
        res = new JSONObject();
        return res;
    }
}