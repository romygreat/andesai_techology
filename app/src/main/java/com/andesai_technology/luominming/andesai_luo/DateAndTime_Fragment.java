package com.testclient.luominming.andesai_luo;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * Created by luominming on 2018/3/1.
 */
public class DateAndTime_Fragment extends Fragment implements View.OnClickListener{
    ImageButton dateBackforwardButton;
    private String TAG="DateAndTime_Fragment";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_1, container, false);
     dateBackforwardButton=root.findViewById(R.id.datebackward);
     dateBackforwardButton.setOnClickListener(this);
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.datebackward){
            dayin("回退");
            fragmentManager = getActivity().getFragmentManager();
            transaction = fragmentManager. beginTransaction();
            transaction.detach(this);
            transaction.commit();

        }
    }
    public void dayin(String str) {
        try {
            if(getActivity().getApplicationContext()!=null){
                Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i(TAG, "printMytips: kongzhizhen");
        }
    }
}
