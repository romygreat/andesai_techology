package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by luominming on 2018/2/3.
 */

public class Myversion_3_Activity extends Activity {

 private ImageButton sleepmode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutversion_1);
        initView();

    }


    private void initView() {

        //sleepmode=findViewById(R.id.sleepmode);
    }
    public void test(View view){
        Uri uri = Uri.parse("https://www.riskstorm.com/company/91441300MA4WK7QB7X#event");//密码qjfd
        //Uri uri = Uri.parse("https://pan.baidu.com/disk/home?errno=0&errmsg=Auth%20Login%20Sucess&&bduss=&ssnerror=0#list/vmode=list&path=%2F%E6%88%91%E7%9A%84%E8%AE%BA%E6%96%87");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void dayin(String str){
        Toast.makeText(Myversion_3_Activity.this,str,Toast.LENGTH_SHORT).show();
    }
}