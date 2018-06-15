package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by luominming on 2018/3/1.
 */

public class EndActivity extends Activity {
    FragmentManager fragmentManager;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.endlatout);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
    public void mainActivity(View view){
        Intent intent1=new Intent(EndActivity.this,Myversion_2_Activity.class);
        startActivity(intent1);
//        fragmentManager = this.getFragmentManager();
//        transaction = fragmentManager. beginTransaction();
//        transaction.detach();
//        transaction.commit();
    }
}
