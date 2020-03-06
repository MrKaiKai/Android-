package com.example.liu.graduationdesignapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.open.net.client.impl.tcp.bio.BioClient;
import com.open.net.client.structures.BaseClient;
import com.open.net.client.structures.BaseMessageProcessor;
import com.open.net.client.structures.IConnectListener;
import com.open.net.client.structures.TcpAddress;
import com.open.net.client.structures.message.Message;

import java.util.LinkedList;

public class RGBActivity extends MainActivity {
    private String str = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgb);
        initView();
        ESP32_connect();
    }

    private void initView()
    {
        findViewById(R.id.R_o).setOnClickListener(listener_RGB);
        findViewById(R.id.R_c).setOnClickListener(listener_RGB);
        findViewById(R.id.R_a).setOnClickListener(listener_RGB);
        findViewById(R.id.R_r).setOnClickListener(listener_RGB);
        findViewById(R.id.R_g).setOnClickListener(listener_RGB);
        findViewById(R.id.R_b).setOnClickListener(listener_RGB);
        findViewById(R.id.R_q).setOnClickListener(listener_RGB);
        findViewById(R.id.R_y).setOnClickListener(listener_RGB);
        findViewById(R.id.R_p).setOnClickListener(listener_RGB);

    }
    private View.OnClickListener listener_RGB =new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.R_o:
                    str = String.format("R:%c,%d",'o',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_c:
                    str = String.format("R:%c,%d",'c',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_r:
                    str = String.format("R:%c,%d",'r',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_g:
                    str = String.format("R:%c,%d",'g',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_b:
                    str = String.format("R:%c,%d",'b',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_q:
                    str = String.format("R:%c,%d",'q',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_p:
                    str = String.format("R:%c,%d",'p',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_y:
                    str = String.format("R:%c,%d",'y',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.R_a:
                    str = String.format("R:%c,%d",'a',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                default:
                    break;
            }
        }
    };
    /*
     **************网络连接***************
     */
    public void ESP32_connect(){
        mClient = new BioClient(mMessageProcessor,mConnectResultListener);
        mClient.setConnectAddress(new TcpAddress[]{new TcpAddress(HOST, Integer.valueOf(PORT))});//设置IP和Prot
        mClient.connect();
        if(!mClient.isConnected()){
            ((TextView)findViewById(R.id.connect)).setText("Connectioning");
        }
    };
    /*
     ***************关闭连接*********************
     */
    public void ESP32_Disconnect(){
        mClient.disconnect();
    };

    public BaseMessageProcessor mMessageProcessor =new BaseMessageProcessor() {

        @Override
        public void onReceiveMessages(BaseClient mClient, final LinkedList<Message> mQueen) {
            for (int i = 0 ;i< mQueen.size();i++) {
                Message msg = mQueen.get(i);
                final String s = new String(msg.data,msg.offset,msg.length);
                runOnUiThread(new Runnable() {
                    public void run() {

//                        recContent.getText().append(s).append("\r\n");
                    }
                });
            }
        }
    };
    private IConnectListener mConnectResultListener = new IConnectListener() {
        @Override
        public void onConnectionSuccess() {
            runOnUiThread(new Runnable() {
                public void run() {

//                    TextView v = getLayoutInflater().inflate(R.layout.activity_rgb,null).findViewById(R.id.connect);
//                    v.setText("Connection-Success");
                    ((TextView)findViewById(R.id.connect)).setText("Connection-Success");
//                    ((TextView)findViewById(R.id.connect)).setText();
                }
            });
        }

        @Override
        public void onConnectionFailed() {

            runOnUiThread(new Runnable() {
                private int i = 0;
                public void run() {
                    ((TextView)findViewById(R.id.connect)).setText("Connection-Failed");
                    if((i ++) % 10 ==0)mClient.connect();
                    if(i > 100)i = 0;
                }
            });
        }
    };
//    public void onBackPressed() {
//        super.onBackPressed();
//        mClient.disconnect();
//    }
    @Override
    protected void onPause() {
        ESP32_Disconnect();
        super.onPause();
    }
}
