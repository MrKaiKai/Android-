package com.example.liu.graduationdesignapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.TextView;

import com.open.net.client.impl.tcp.bio.BioClient;
import com.open.net.client.structures.BaseClient;
import com.open.net.client.structures.BaseMessageProcessor;
import com.open.net.client.structures.IConnectListener;
import com.open.net.client.structures.TcpAddress;
import com.open.net.client.structures.message.Message;

import java.util.LinkedList;
import android.widget.SeekBar;

public class MP3Activity extends MainActivity {
    private String str = null;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        view_init();
        ESP32_connect();
    }


    private void view_init(){
        seekBar = (SeekBar) findViewById(R.id.M_vol);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                     /**
              * 拖动条进度改变的时候调用
              */
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                    boolean fromUser) {
                        str = String.format("M:%c,%d",'v',progress);
                        mMessageProcessor.send(mClient,str.getBytes());
                    }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.M_P).setOnClickListener(listener);
        findViewById(R.id.M_p).setOnClickListener(listener);
        findViewById(R.id.M_u).setOnClickListener(listener);
        findViewById(R.id.M_d).setOnClickListener(listener);
        findViewById(R.id.M_r).setOnClickListener(listener);
        findViewById(R.id.M_n).setOnClickListener(listener);
        findViewById(R.id.M_m).setOnClickListener(listener);
        findViewById(R.id.M_l).setOnClickListener(listener);
    }


    private View.OnClickListener listener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.M_P:
                    str = String.format("M:%c,%d",'P',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.M_p:
                    str = String.format("M:%c,%d",'p',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;

                case R.id.M_r:
                    str = String.format("M:%c,%d",'r',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;

                case R.id.M_n:
                    str = String.format("M:%c,%d",'n',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;

                case R.id.M_u:
                    str = String.format("M:%c,%d",'u',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;

                case R.id.M_d:
                    str = String.format("M:%c,%d",'d',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.M_m:
                    str = String.format("M:%c,%d",'m',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.M_l:
                    str = String.format("M:%c,%d",'l',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
//                case R.id.fab:
//                    Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    break;
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

//                    TextView v = getLayoutInflater().inflate(R.layout.activity_mp3,null).findViewById(R.id.connect);
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
