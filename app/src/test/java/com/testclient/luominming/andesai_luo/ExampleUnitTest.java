package com.testclient.luominming.andesai_luo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "testapk";
    private BatteryReceiver batteryReceiver;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        batteryReceiver = new BatteryReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
       System.out.print("test");
      //  Log.i(TAG, "addition_isCorrect: ");
       // registerReceiver(batteryReceiver, intentFilter);
    }
    private class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (true) {
                long beigintime = System.currentTimeMillis();
                Log.i(TAG, "onReceive: begintime" + beigintime);
                int current = intent.getExtras().getInt("level");//获得当前电量
                int total = intent.getExtras().getInt("scale");//获得总电量
                int  percentFisrt = current * 100 / total;
                Log.i(TAG, "onReceive:current "+current);
                Log.i(TAG, "onReceive:total "+total);
                Log.i(TAG, "onReceive:percentFisrt "+percentFisrt);
            }
        }
}}