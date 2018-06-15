package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by luominming on 2018/3/1.
 */

public class DateAndTimeActvity extends Activity {
    private ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_1);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
      //  imageButton=findViewById(R.id.)
    }
    public void click_backward(View view){
        Intent intent = new Intent();
        intent.setClass(DateAndTimeActvity.this, Myversion_2_Activity.class);
        intent.putExtra("date","date test");
        intent.putExtra("time","time test");
        startActivity(intent);
        this.finish();
    }
}
