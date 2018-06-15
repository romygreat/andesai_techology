package com.testclient.luominming.andesai_luo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by luominming on 2018/3/31.
 */
public class fragment_date_time extends Fragment implements View.OnClickListener {
    private static final String TAG ="fragment_date_time";
    public TextView dateSettingTextView,timeSettingTextView,backlighTextView;
    Button OKButton;
    ImageButton backwardMainButton;
    Myversion_2_Activity mActivity;
   SharedPreferences sharedPreferences=null;
    String editTextString="5:20";
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.layout_time_setings, container, false);
         dateSettingTextView=view.findViewById(R.id.datesetting);
         timeSettingTextView=view.findViewById(R.id.timesetting);
         OKButton=view.findViewById(R.id.OKButton);
         backwardMainButton=view.findViewById(R.id.backmain);
         backlighTextView=view.findViewById(R.id.backlight);
         dateSettingTextView.setOnClickListener(this);
         timeSettingTextView.setOnClickListener(this);
         backlighTextView.setOnClickListener(this);
         sharedPreferences=mActivity.getPreferences(mActivity.MODE_PRIVATE);
        String backlighTime= sharedPreferences.getString("backlighTextView","52");
        String  timeSetting= sharedPreferences.getString("timeSettingTextView","12:58");
        String  dateSetting= sharedPreferences.getString("dateSettingTextView","2018/05/20");
         OKButton.setOnClickListener(this);
         backwardMainButton.setOnClickListener(this);

      //   dateSettingTextView.setText(editTextString);

         setTextView();
       //timeSettingTextView.setText(editTextString);
        return view;
    }
    private void setTextView() {
        String backlighTime= sharedPreferences.getString("backlighTextView","52");
        String  timeSetting= sharedPreferences.getString("timeSettingTextView","12:58");
        String  dateSetting= sharedPreferences.getString("dateSettingTextView","2018/05/20");
        backlighTextView.setText(backlighTime+"s");
//            dd-ee-ff-gggg-hh-ii
            timeSettingTextView.setText(timeSetting);
            dateSettingTextView.setText(dateSetting);

//        try {
//           String[] text= editTextString.split("-");
//           backlighTextView.setText(text[0]+"s");
////            dd-ee-ff-gggg-hh-ii
//            timeSettingTextView.setText(text[1]+":"+text[2]);
//            dateSettingTextView.setText(text[3]+"/"+text[4]+"/"+text[5]);
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.i(TAG, "setTextView: "+editTextString);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.datesetting:editDate_Time("输入日期",false);

                break;
            case  R.id.timesetting:editDate_Time("输入时间",true);

                break;
            case R.id.OKButton:
             //  printMytips("test");
               mActivity.printMytips("test");
             mActivity.timeView.setText(editTextString);
             //mActivity.getTimeTest();
                break;
            case R.id.backmain:
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.detach(this);
                //mActivity.getValue("T1",1);
                Log.i(TAG, "onClick: >>backmain");
                transaction.commit();
                break;
            case R.id.backlight:editDate_Time("调节背光",true);

            break;

        }
    }
    public void editDate_Time(final String setting, final boolean timeValue) {
        final EditText inputedit=new EditText(getActivity());
        inputedit.setWidth(300);
        if(timeValue){
          if(setting.equals("输入时间"))
              inputedit.setHint("格式如：05:20");else {
                  inputedit.setHint("格式如(时间秒):60");
          }
        }else{
            inputedit.setHint("格式如：2018/05/20");
        }
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(getActivity());
        alertdialog.setTitle(setting);
        alertdialog.setView(inputedit);
        alertdialog.setNegativeButton("取消",null);
        alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editTextString = inputedit.getText().toString();
                if (!editTextString.equals("")) {
              //    printMytips("test");
                    mActivity.printMytips("test");
                  if(timeValue){
                      if(setting.equals("输入时间")){
                      timeSettingTextView.setText(editTextString);
                          editor=sharedPreferences.edit();
                          editor.putString("timeSettingTextView",editTextString);
                          editor.commit();
                      }
                      else{
                          backlighTextView.setText(editTextString);
                          editor=sharedPreferences.edit();
                          editor.putString("backlighTextView",editTextString);
                          editor.commit();
                      }
                  }else{
                       dateSettingTextView.setText(editTextString);
                      editor=sharedPreferences.edit();
                      editor.putString("dateSettingTextView",editTextString);
                      editor.commit();
                  }
                }
            }
        });
        alertdialog.show();
    }
//    public void printMytips(String str) {
//        try {
//            if(getActivity().getApplicationContext()!=null){
//                Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//    }
    @Override
    public void onAttach(Context context) {
        mActivity=(Myversion_2_Activity) context;

        super.onAttach(context);
    }
    public TextView getView(){
        return dateSettingTextView;
    }
    public void  setValue(String s1){
      // mActivity.printMytips(s1);
       // timeSettingTextView.setText(s1);
        Log.i(TAG, "setValue: gettimetest"+s1);

        if(!s1.equals(null))
        {editTextString=s1;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        mActivity=(Myversion_2_Activity) getActivity();
        super.onAttach(activity);
    }
}
