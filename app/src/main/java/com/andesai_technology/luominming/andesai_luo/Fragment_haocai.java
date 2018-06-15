package com.testclient.luominming.andesai_luo;

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
import android.widget.TextView;

/**
 * Created by luominming on 2018/3/31.
 */

public class Fragment_haocai extends Fragment implements  View.OnClickListener {
    private ImageButton outButton;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private Myversion_2_Activity mActivity;
    TextView chuxiaoTextView,life_tanTextView,HEAP_TextView;
    String zValueString="98-99-55";
    private String TAG="Fragment_haocai";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_haocai, container, false);
        ImageButton  goButton=view.findViewById(R.id.forward123);
        outButton=view.findViewById(R.id.backward123);

        chuxiaoTextView=view.findViewById(R.id.chuxiao);
        life_tanTextView=view.findViewById(R.id.life_tan);
        HEAP_TextView=view.findViewById(R.id.HEAP);
        goButton.setOnClickListener(this);
        outButton.setOnClickListener(this);
        fragmentManager = mActivity.getFragmentManager();
        transaction = fragmentManager. beginTransaction();
        setTextView();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        mActivity=(Myversion_2_Activity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // transaction.detach(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forward123:
                Intent intent1=new Intent(mActivity,EndActivity.class);
               // intent1=new Intent(mActivity,Myversion_2_Activity.class);
                onDetach();
                startActivity(intent1);
                transaction.addToBackStack(null);
                break;
            case R.id.backward123:
                transaction.detach(this);
                transaction.commit();
                break;
        }
    }
    public String setValue(String remain){
       // kk-ll-mm
        Log.i(TAG, "setValue: "+remain);
        zValueString=remain;

     return  zValueString;
    }
    private void setTextView(){
        Log.i(TAG, "setTextView: "+zValueString);
        String[]  remain_3=zValueString.split("-");
        chuxiaoTextView.setText(remain_3[2]+"%");
        life_tanTextView.setText(remain_3[1]+"%");
        HEAP_TextView.setText(remain_3[0]+"%");
    }
}
