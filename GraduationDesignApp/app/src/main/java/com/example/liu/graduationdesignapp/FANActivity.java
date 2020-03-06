package com.example.liu.graduationdesignapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.open.net.client.impl.tcp.bio.BioClient;
import com.open.net.client.structures.BaseClient;
import com.open.net.client.structures.BaseMessageProcessor;
import com.open.net.client.structures.IConnectListener;
import com.open.net.client.structures.TcpAddress;
import com.open.net.client.structures.message.Message;

import java.util.LinkedList;

public class FANActivity extends MainActivity {
    private String str = null;
    private SeekBar seekBar_fan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        initView();
        ESP32_connect();
    }
    private void initView()
    {
        seekBar_fan = (SeekBar) findViewById(R.id.F_vol);
        seekBar_fan.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
            * 拖动条进度改变的时候调用
            */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                str = String.format("F:%c,%d",'s',progress);
                mMessageProcessor.send(mClient,str.getBytes());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.F_o).setOnClickListener(listener_fan);
        findViewById(R.id.F_c).setOnClickListener(listener_fan);
        findViewById(R.id.F_u).setOnClickListener(listener_fan);
        findViewById(R.id.F_d).setOnClickListener(listener_fan);

    }
    private View.OnClickListener listener_fan =new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.F_o:
                    str = String.format("F:%c,%d",'o',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.F_c:
                    str = String.format("F:%c,%d",'c',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;

                case R.id.F_u:
                    str = String.format("F:%c,%d",'u',0);
                    mMessageProcessor.send(mClient,str.getBytes());
                    break;
                case R.id.F_d:
                    str = String.format("F:%c,%d",'d',0);
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

//                    TextView v = getLayoutInflater().inflate(R.layout.activity_fan,null).findViewById(R.id.connect);
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
