package application.mobile.healthday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TrainerManage extends AppCompatActivity {
    ListView listView;
    SingerAdapter adapter;
    JSONArray resjson;
    public JSONArray getCustomers(String tnum) {
        System.out.println(tnum);
        String phpurl = "http://192.168.64.3/healthday/getCustomers.php?tnum="+tnum;
        BufferedReader bufferedReader = null;
        StringBuilder sb = new StringBuilder();
        JSONArray res;
        try{
            URL url = new URL(phpurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb.append(json + "\n");
                //System.out.println(json);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            res = new JSONArray(sb.toString());
            return res;
        } catch (JSONException e){

        }
        res = new JSONArray();
        return res;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_manage_customer);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SingerAdapter();
        resjson = getCustomers(Login.u_num);
        for(int i=0; i<resjson.length(); i++){
            try{
                adapter.addItem(new SingerItem(resjson.getJSONObject(i).getString("name"),
                        resjson.getJSONObject(i).getString("usernum"),
                        R.drawable.customer));
            } catch (Exception e){}
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingerItem item = (SingerItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingerItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());

            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setId(item.getId());
            view.setImage(item.getResId());

            return view;
        }
    }

}

