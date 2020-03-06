package com.example.liu.graduationdesignapp;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.net.client.impl.tcp.bio.BioClient;
import com.open.net.client.structures.BaseClient;
import com.open.net.client.structures.BaseMessageProcessor;
import com.open.net.client.structures.IConnectListener;
import com.open.net.client.structures.TcpAddress;
import com.open.net.client.structures.message.Message;

import java.util.LinkedList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{
    private CircleImageView mp3,rgb,fan;
    private boolean isImageBigger;
    private ViewGroup sceneRoot;
    private int primarySize;

    View myViwe;


    //定义相关变量,完成初始化
    public static final String HOST = "192.168.4.1";
    public static final int PORT = 8080;
    public BioClient mClient =null;
    private int flag = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        ESP32_connect();
    }

    private void initView()
    {
        findViewById(R.id.fab).setOnClickListener(listener);
        sceneRoot = (ViewGroup) findViewById(R.id.function_show);
        sceneRoot.setVisibility(View.GONE);
        mp3 = (CircleImageView) findViewById(R.id.mp3);
        rgb = (CircleImageView)findViewById(R.id.rgb);
        fan = (CircleImageView)findViewById(R.id.fan);
        primarySize = mp3.getLayoutParams().width;
        mp3.setOnClickListener(listener);
        rgb.setOnClickListener(listener);
        fan.setOnClickListener(listener);
    }

    private View.OnClickListener listener =new View.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onClick(View v) {
            myViwe = v;
            switch(v.getId())
            {
                case R.id.mp3:
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
                    //next scene 此时通过代码已改变了scene statue
                    changeScene(v);
//                    delay_ms(1000);
                    startActivity(new Intent(getApplicationContext(), MP3Activity.class));
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
                    //next scene 此时通过代码已改变了scene statue
                    changeScene(v);

                    break;
                case R.id.rgb:
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
                    //next scene 此时通过代码已改变了scene statue
                    changeScene(v);
//                    delay_ms(1000);
                    startActivity(new Intent(getApplicationContext(), RGBActivity.class));
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
                    //next scene 此时通过代码已改变了scene statue
                    changeScene(v);
                    break;
                case R.id.fan:
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
                    //next scene 此时通过代码已改变了scene statue
                    changeScene(v);
//                    delay_ms(1000);
                    startActivity(new Intent(getApplicationContext(), FANActivity.class));
                    //start scene 是当前的scene
                    TransitionManager.beginDelayedTransition(sceneRoot, TransitionInflater.from(getApplicationContext()).inflateTransition(R.transition.explode_and_changebounds));
//                    next scene 此时通过代码已改变了scene statue
                    changeScene(v);
                    break;
                case R.id.fab:
                    if(sceneRoot.isShown()){
                        sceneRoot.setVisibility(View.GONE);
                        sceneRoot.setAnimation(AnimationUtils.makeOutAnimation(getApplication(), true));
                    }else{
                        sceneRoot.setVisibility(View.VISIBLE);
                        sceneRoot.setAnimation(AnimationUtils.makeInAnimation(getApplication(), true));
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private void delay_ms(int ms){
        try{
            Thread.currentThread();
            Thread.sleep(ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     **************网络连接***************
     */
//    public void ESP32_connect(){
//        mClient = new BioClient(mMessageProcessor,mConnectResultListener);
//        mClient.setConnectAddress(new TcpAddress[]{new TcpAddress(HOST, Integer.valueOf(PORT))});//设置IP和Prot
//        mClient.connect();
//        if(!mClient.isConnected()){
//            ((TextView)findViewById(R.id.connect)).setText("Connectioning");
//        }
//
//    };
    /*
     ***************关闭连接*********************
     */
//    public void ESP32_Disconnect(){
//        mClient.disconnect();
//    };
//
//
//
//    public BaseMessageProcessor mMessageProcessor =new BaseMessageProcessor() {
//
//        @Override
//        public void onReceiveMessages(BaseClient mClient, final LinkedList<Message> mQueen) {
//            for (int i = 0 ;i< mQueen.size();i++) {
//                Message msg = mQueen.get(i);
//                final String s = new String(msg.data,msg.offset,msg.length);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//
////                        recContent.getText().append(s).append("\r\n");
//                    }
//                });
//            }
//        }
//    };
//    private IConnectListener mConnectResultListener = new IConnectListener() {
//        @Override
//        public void onConnectionSuccess() {
//            runOnUiThread(new Runnable() {
//                public void run() {
//
//                    TextView v = getLayoutInflater().inflate(R.layout.activity_main,null).findViewById(R.id.connect);
//                    v.setText("Connection-Success");
////                    ((TextView)findViewById(R.id.connect)).setText();
//                }
//            });
//        }
//
//        @Override
//        public void onConnectionFailed() {
//
//            runOnUiThread(new Runnable() {
//                private int i = 0;
//                public void run() {
//                    ((TextView)findViewById(R.id.connect)).setText("Connection-Failed");
//                    if((i ++) % 10 ==0)mClient.connect();
//                    if(i > 100)i = 0;
//                }
//            });
//        }
//    };

    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(mp3,rgb,fan);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * view的宽高1.5倍和原尺寸大小切换
     * 配合ChangeBounds实现缩放效果
     * @param view
     */
    private void changeSize(View view) {
        isImageBigger=!isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(isImageBigger){
            layoutParams.width=(int)(2*primarySize);
            layoutParams.height=(int)(2*primarySize);
        }else {
            layoutParams.width=primarySize;
            layoutParams.height=primarySize;
        }
        view.setLayoutParams(layoutParams);
    }

    /**
     * VISIBLE和INVISIBLE状态切换
     * @param views
     */
    private void changeVisibility(View ...views){
        for (View view:views){
            view.setVisibility(view.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
        }
    }

//    public void onBackPressed() {
//        super.onBackPressed();
//        mClient.disconnect();
//    }

    @Override
    protected void onPause() {
//        ESP32_Disconnect();
        super.onPause();
    }


    /*
    ******************
     */

}
