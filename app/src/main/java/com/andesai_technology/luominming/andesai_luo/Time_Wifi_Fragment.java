package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by luominming on 2018/3/30.
 */

public  class Time_Wifi_Fragment extends Fragment implements  View.OnClickListener{
    Activity mActivity;
    Myversion2_Callback myversion2_callback;
    FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.time_wifi_layout, container, false);
        TextView timeView=view.findViewById(R.id.timeView);
        ImageButton wifiButton=view.findViewById(R.id.wifi_2);
        ImageView moonNight=view.findViewById(R.id.testMessage);
        timeView.setOnClickListener(this);
        moonNight.setOnClickListener(this);
       wifiButton.setOnClickListener(this);
      // myversion2_callback.getTimeTest();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity=getActivity();
        if(mActivity instanceof  Myversion2_Callback){
            myversion2_callback=(Myversion2_Callback) mActivity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.testMessage){
             editIP();
        }
    }
    public void editIP() {
        final EditText inputedit=new EditText(mActivity);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(mActivity);
        alertdialog.setTitle("测试获取的结果");
        alertdialog.setView(inputedit);
       // alertdialog.setIcon(R.drawable.wifi);
        alertdialog.setNegativeButton("取消",null);
        alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                if (!editTextString.equals("")) {
                 myversion2_callback.getValue(editTextString,3);
                }
            }
        });
        alertdialog.show();
    }
}
