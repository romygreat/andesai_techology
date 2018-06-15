package com.testclient.luominming.andesai_luo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by luominming on 2018/1/18.
 */
public class MyLuancherActivity extends Activity {
    private TextView Version_1,Version_2;
    private Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.what== 1) {
                //跳转
                Intent intent = new Intent();
                intent.setClass(MyLuancherActivity.this, Myversion_2_Activity.class);
                startActivity(intent);
                finish();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.launcher1);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Version_2= findViewById(R.id.Version_2);
        Version_1=findViewById(R.id.Version_1);
        Thread mt = new Thread(mThread);
        mt.start();
        Version_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyLuancherActivity.this,MainActivitytest.class);
                startActivity(intent);
            }
        });
        Version_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // printMytips("更新中");
                Intent intent=new Intent(MyLuancherActivity.this,Myversion_2_Activity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    public void dayin(String str){
        Toast.makeText(MyLuancherActivity.this,str,Toast.LENGTH_SHORT).show();
    }
    Runnable mThread = new Runnable() {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage();
            //延时3秒
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            msg.obj = 123;
            msg.what=1;
            mHandler.sendMessage(msg);
        }

    };
}
