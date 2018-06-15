package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * Created by luominming on 2018/3/28.
 */
public class Fragment_dealWithRepair extends Fragment  implements View.OnClickListener{
    private static final String TAG = "fragment";
    ImageButton backImageButton,forwardImageButton;
    Context mContext;
    Myversion_2_Activity mActivity;
    Myversion2_Callback myversion2_callback;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @Override
    public void onAttach(Context context) {
        mActivity=(Myversion_2_Activity) context;
        super.onAttach(context);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dealwith_layout, container, false);
        backImageButton=view.findViewById(R.id.backward123);
        forwardImageButton=view.findViewById(R.id.forward123);
        backImageButton.setOnClickListener(this);
        forwardImageButton.setOnClickListener(this);
//        myversion2_callback.getTimeTest();
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forward123:
                mActivity.printMytips("向前");
//                Log.i(TAG, "onClick: fragment");
//                 fragmentManager = mActivity.getFragmentManager();
//                transaction = fragmentManager. beginTransaction();
//                 Fragment_haocai fragment_haocai=new Fragment_haocai();
//                transaction.replace(R.id.fragment,fragment_haocai);
//                transaction.commit();
                mActivity.gotocostMatirier();
                break;
            case R.id.backward123:
                mActivity.printMytips("返回");
                fragmentManager = mActivity.getFragmentManager();
                transaction = fragmentManager. beginTransaction();
                transaction.detach(this);
                transaction.commit();
                /**
                 1、在相应的fragmnt中引用myversion2_callback
                 2、调用String value= myversion2_callback.getValue("M4",0);
                 3、获取value值并作出相应的处理
                 4、需要判断接收的数据是否为对应的值，用if 判断
                 */

//                Log.i(TAG, "onClick: myversion2_callback.getValue");
//               String value= myversion2_callback.getValue("M4",0);
//               dayin(value);
//                Log.i(TAG, "onClick: getValue"+value);
                break;
            default:break;
        }
    }
//    public void dayin(String str) {
//        try {
//            if(mActivity.getApplicationContext()!=null){
//                Toast.makeText(mActivity.getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
//        }catch (NullPointerException e){
//            e.printStackTrace();
//            Log.i(TAG, "dayin: kongzhizhen");
//        }
//    }
    public void setCallback(Myversion2_Callback callbacktest){
        myversion2_callback=callbacktest;
    }
}
