package application.mobile.healthday;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class Chat1 extends AppCompatActivity {
    TextView recemsg, sendmsg;
    Button btnConnect, btnSend;
    EditText editIp, editPort, editMessage;
    Handler msgHandler;
    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;
    LinkedList<SocketClient> threadList;
    Context context;
    String mac, tempmsg;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);
        final LinkedList<Chat1.SocketClient> threadList;
        context = this;
        //editIp = (EditText) findViewById(R.id.editIp);
        //editPort = (EditText) findViewById(R.id.editPort);
        editMessage = (EditText) findViewById(R.id.editMessage);
        //btnConnect = (Button) findViewById(R.id.btnConnect);
        btnSend = (Button) findViewById(R.id.btnSend);
        threadList = new LinkedList<Chat1.SocketClient>();
        recemsg = (TextView) findViewById(R.id.recemsg);
        sendmsg = (TextView) findViewById(R.id.sendmsg);
        //핸들러 작성
        //메세지 수신했을 때 어떻게할지
        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1111) {
                    //채팅 서버로부터 수신한 메시지 텍스트뷰에 추가
                    String temp = msg.obj.toString();
                    temp = temp.substring(18);
                    System.out.println("temp : " + temp);
                    System.out.println("tempmsg : " + tempmsg);
                    if(temp.equals(tempmsg)){
                        System.out.println("SEND");
                        recemsg.append("\n");
                        sendmsg.append(temp + "\n");
                    }
                    else{
                        System.out.println("RECEIVE");
                        recemsg.append(temp + "\n");
                        sendmsg.append("\n");
                    }

                }
            }
        };
        //서버 접속 버튼
        /*btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                client = new SocketClient(editIp.getText().toString(), editPort.getText().toString());
                threadList.add(client);
                client.start();
            }

        });*/
        //메시지 전송 버튼
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(flag == false){
                    client = new SocketClient("192.168.0.12", "5001");
                    threadList.add(client);
                    client.start();
                    flag = true;
                }
                String message = editMessage.getText().toString();
                tempmsg = message;
                if (message != null && !message.equals("")) {
                    send = new SendThread(socket);
                    send.start();
                    editMessage.setText("");
                }
            }
        });
    }
    class SocketClient extends Thread{
        boolean threadAlive;
        //thread 동작 여부
        String ip;
        String port;
        OutputStream outputStream = null;
        DataOutputStream output = null;
        public SocketClient(String ip, String port){
            threadAlive = true;
            this.ip = ip;
            this.port = port;
        }
        public void run(){
            try{
                socket = new Socket(ip,Integer.parseInt(port));
                output = new DataOutputStream(socket.getOutputStream());
                receive = new ReceiveThread(socket);
                receive.start();
                WifiManager mng = (WifiManager)context.getSystemService(WIFI_SERVICE);
                WifiInfo info = mng.getConnectionInfo();
                mac = info.getMacAddress();
                output.writeUTF(mac);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //내부 클래스
    class ReceiveThread extends Thread{
        Socket socket = null;
        DataInputStream input = null;
        public ReceiveThread(Socket socket){
            this.socket = socket;
            try{
                input = new DataInputStream(socket.getInputStream());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void run(){
            try{
                while(input != null){
                    String msg = input.readUTF();
                    if(msg != null){
                        Message hdmsg = msgHandler.obtainMessage();
                        hdmsg.what = 1111;
                        hdmsg.obj=msg;
                        msgHandler.sendMessage(hdmsg);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //내부클래스
    class SendThread extends Thread{
        Socket socket;
        String sendmsg = editMessage.getText().toString();
        DataOutputStream output;
        public SendThread(Socket socket){
            this.socket = socket;
            try{
                output = new DataOutputStream(socket.getOutputStream());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public void run(){
            try{
                if(output !=null){
                    if(sendmsg !=null){
                        output.writeUTF(mac+":"+sendmsg);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}