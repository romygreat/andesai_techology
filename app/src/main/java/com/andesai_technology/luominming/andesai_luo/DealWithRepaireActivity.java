//package com.testclient.luominming.testclient;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.media.session.IMediaControllerCallback;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
///**
// * Created by luominming on 2018/3/1.
// */
//
////public class DealWithRepaireActivity extends Activity implements View.OnClickListener {
//    private ImageButton backward,forward;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.dealwith_layout);
//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//        backward=findViewById(R.id.backward123);
//        forward=findViewById(R.id.forward);
//        backward.setOnClickListener(this);
//        forward.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.forward:
//                Intent intent1=new Intent(DealWithRepaireActivity.this,EndActivity.class);//the end picture
//                startActivity(intent1);break;
//            case R.id.backward123:
//                dayin("inener");
//                Intent intent=new Intent(DealWithRepaireActivity.this,Myversion_2_Activity.class);
//                //dealWithRepaireActivity进入故障处理
//                startActivity(intent);break;
//                default:break;
//        }
//    }
//    public void dayin(String str) {
//        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//    }
//}