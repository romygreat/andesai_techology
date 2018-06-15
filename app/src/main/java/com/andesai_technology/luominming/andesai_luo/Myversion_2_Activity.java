package com.testclient.luominming.andesai_luo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Myversion_2_Activity extends Activity implements View.OnClickListener,View.OnLongClickListener,Myversion2_Callback {
    String hostName = "172.16.106.188";
    int port = 8300;
    final String  TAG = "method";
    TextView result, show;
    private WifiAdmin mWifiAdmin;
    public String content;
    public TextView PM2_5_TextView,CO_2_TextView, HCHO_TextView;
    Timer timer = new Timer();
    boolean IsreadFirst = true;
    Socket socket = new Socket();
    OutputStream ou = null;
    boolean toast = true;
    InetSocketAddress inetSocketAddress;
    int count = 0;
    boolean xunhuanG = true;
    TextView timeView;
    private int showFailTip = 0;
    public  String anDeSaiMessage="test";
    private ImageButton WIFIButton;
    private ImageButton forwardButton,dealwith_repaire;
    final int handleTimeInt =555;
    String pageNum="1";
    String current_page="1";
    String timePageValue="52-12-58-2018-05-20";
    String costMatirierString="98-99-95";
    /**
     * x=0：点击开始页面任意位置，进入主页面
     x=1：新风按钮
     x=2：净化按钮
     x=3：混风按钮
     x=4：故障处理按钮
     x=5：睡眠按钮
     x=6：常规风按钮
     x=7：高速风按钮
     x=8：自动按钮
     x=9：开关机按钮
     x=a：付热按钮
     x=b：右箭头按钮
     */
    final int handleFirst=0;
    final int handleXinfeng=1;
    final int handleJinghua=2;
    final int handleHunfeng=3;
    final int handleDealWith=4;
    final int handleSleep=5;
    final int handleFormal=6;
    final int handleHigh=7;
    final int handleAuto=8;
    final int handleFure=10;//10还是A需要确认一下
    final int handlePower=9;
    final int hanldleMainpage=35;
    String ZValueString="no value";//OGx：Z  ZValueString代表Z的值
    final String mainPage="1";
    final String timePage="3";
    final String accidentPage="4";
    final String costMatirier="5";

    public Handler myHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case handleXinfeng: {
                    if(result!=null){
                    result.append("server:" + msg.obj + "\n");
                    if (!msg.obj.equals(null))
                        result.setText("" + msg.obj);
                    Log.i("readlog", "handleMessage: msg" + msg.obj);
                    }
                }
                break;
                case 3:
                    if (showFailTip < 5) {
                        printMytips("请确认网络是否有问题");
                        showFailTip++;
                    }
                    break;
                case 2:
                    printMytips("已连接");
                    break;
                case 6://P1值显示
                {

                    break;
                }
                case 7://M值显示
                {
                    break;
                }
                case 8://L值显示
                {

                    break;
                }
                case handleTimeInt:
                    timeView.setText((String)msg.obj);
                    break;
                case hanldleMainpage:
                    String nongDu_3=(String)msg.obj;
                    String[] nongDuArray=nongDu_3.split("-");
                    try {
                    String PM2_5=nongDuArray[0];
                    String CO_2=nongDuArray[1];
                    String HCHO=nongDuArray[2];
                    PM2_5_TextView.setText(getResources().getString(R.string.PM2_5_title)+PM2_5+getResources().getString(R.string.PM2_5_Unit));
                    CO_2_TextView.setText(getResources().getString(R.string.CO_2_title)+CO_2+getResources().getString(R.string.CO_2_Unit));
                    HCHO_TextView.setText(getResources().getString(R.string.HCHO)+HCHO+getResources().getString(R.string.HCHO_Unit));
                    }catch (ArrayIndexOutOfBoundsException e){
                        Log.i(TAG, "handleMessage:nongDuArray ");
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private ImageButton sleepmodeButton,formalmodeButton,automodeButton,highmodeButton,furemodeButton,powermodeButton;
    private ImageButton hunfengButton,jinghuaButton,xinfengButton;
    private boolean sleepmodeon=true;
    private boolean formalmodeon=true;
    private boolean hunfengon=true;
    private boolean jinghuaon=true;
    private boolean xinfengfengon=true;
    private Fragment fragment=null;
    private boolean fureon=true;
    private boolean poweron=true;
    private boolean highmodeon=true;
    private FragmentManager fragmentManager;
    private  FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.myversionzhengshi_2);
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
             }
        mWifiAdmin = new WifiAdmin(this);
        initView();
        InitImageView();

    }
    private void InitImageView() {
        WIFIButton=findViewById(R.id.wifi_2);
        forwardButton=findViewById(R.id.forward1);
        dealwith_repaire=findViewById(R.id.dealwith_repair);

        result=findViewById(R.id.result);
        timeView=findViewById(R.id.timeView);

        hunfengButton=findViewById(R.id.feng);
        jinghuaButton=findViewById(R.id.jinghua);
        xinfengButton=findViewById(R.id.xinfeng);

        sleepmodeButton =findViewById(R.id.sleepmode);
        automodeButton=findViewById(R.id.automode);
        formalmodeButton=findViewById(R.id.formalmode);
        highmodeButton=findViewById(R.id.highmode);
        furemodeButton=findViewById(R.id.furemode);
        powermodeButton=findViewById(R.id.powermode);

        WIFIButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);

        dealwith_repaire.setOnClickListener(this);

        hunfengButton.setOnClickListener(this);
        jinghuaButton.setOnClickListener(this);
        xinfengButton.setOnClickListener(this);

        sleepmodeButton.setOnClickListener(this);
        formalmodeButton.setOnClickListener(this);
        automodeButton.setOnClickListener(this);
        highmodeButton.setOnClickListener(this);
        furemodeButton.setOnClickListener(this);
        powermodeButton.setOnClickListener(this);
        WIFIButton.setOnLongClickListener(this);
    }
//
    private void initView() {
       PM2_5_TextView = findViewById(R.id.PM2_5);
       CO_2_TextView= findViewById(R.id.CO_2);
       HCHO_TextView = findViewById(R.id.HCHO);
     //  setListener();
    }

    @Override
    public String getValue(String sendMessage, int fragmentId) {
        String returnFragment=null;
        MyThread thread=null;
        switch (fragmentId){
            case 0:
                Log.i(TAG, "getValue: case 0 has ran");
                thread=new MyThread(sendMessage);
                Log.i(TAG, "getValue: "+sendMessage);
                thread.start();break;
            case 1:
                Log.i(TAG, "getValue: case 0 has ran");
                thread=new MyThread(sendMessage);
                Log.i(TAG, "getValue: "+sendMessage);
                thread.start();break;
            default:
                thread=new MyThread(sendMessage);
                Log.i(TAG, "getValue: "+sendMessage);
                thread.start();
                break;
        }
      while (thread.isAlive()){
          //  if(anDeSaiMessage.equals("test"))
                Log.i(TAG, "getValue: is Alive");
                //return anDeSaiMessage;
      }
      return ZValueString;
    }

    @Override
    public String getTimeTest() {
        Timer timertime=new Timer();
        timertime.schedule(new TimerTask() {
            @Override
            public void run() {
               getTime();//添加事件,G1  G2
                //52-12-58-2019-5-20
//                current_page="3";
//                String[] test_array={"OG2:51-12-58-2019-5-20","OG2:52-12-58-2019-5-20","OG2:53-12-58-2019-5-20",
//                        "OG2:56-12-58-2019-5-20","OG2:55-12-58-2019-5-20"};
//                int a=(int)( Math.random()*test_array.length-1);
//                dealWithReturnString(test_array[a]);
          //  gotoTimepage();
                Log.i(TAG, "run: getTimetest");
            }
        },1000,1000);
        return "time";
    }

    class MyThread extends Thread {
        public MyThread(String str) {
            content = str;
            Log.i(TAG, "MyThread: mythead()");
        }
        @Override
        public void run() {
            //定义消息
            // dd-ee-ff-gggg-hh-iidd-ee-ff-gggg-hh-ii dd：背光时间；ee：时；ff：分；gggg：年；hh：月；ii：日
           // dealWithReturnString("OG2:2-52-12-58-2019-5-20");

            dealWithReturnString("OG1:3-231-25-325");
            boolean b = true;
            int end;
            Log.i(TAG, "run: in socketName\n" + socket);
            Log.i(TAG, "run: contentInittial::" + content);
            // while (true)
            {
                try {
                    Log.i(TAG, "run: content" + content);
                    Log.i(TAG, "run: InitconnectOrNot1" + socket.isConnected());
                    if (!socket.isConnected()) {
                        socket = new Socket();
                        inetSocketAddress = new InetSocketAddress(hostName, port);
                        IsreadFirst = true;
                        Log.i(TAG, "run: connect is not connect,begin connect");
                        socket.connect(inetSocketAddress, 5000);
                        toast = true;
                        ou = socket.getOutputStream();
                        Log.i(TAG, "run: connectedOrnot2" + socket.isConnected());
                        //  socket.setSoTimeout(100);
                        Log.i(TAG, "run: bff" + content);
                        // getGvalue();

                        if (socket.isConnected() && toast) {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = "connected";
                            //发送消息 修改UI线程中的组件
                            myHandler.sendMessage(msg);
                            toast = false;
                        }
                    }
                    Log.i(TAG, "run: InitconnectOrNot" + socket.isConnected());
                    Log.i(TAG, "run: connected mythread");
                    Log.i(TAG, "run: outpustream");
                    Log.i(TAG, "run: out.write() starting");
                    ou.write(content.getBytes("utf-8"));
                    Log.i(TAG, "run: sendcontent1" + content);
                    Log.i(TAG, "run: ou.write() have excuted");
                    ou.flush();
                    {
                        IsreadFirst = false;
                        Log.i(TAG, "run: Isreadfirst:new ReadThread" + IsreadFirst);
                    }
                } catch (SocketTimeoutException aa) {
                    //连接超时 在UI界面显示消息
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = "服务器连接失败或网络不畅通";
                    anDeSaiMessage=(String) msg.obj;
                    //发送消息 修改UI线程中的组件
                    myHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "run: MythreadIOExeption");
                    anDeSaiMessage="run: MythreadIOExeption";
                } finally {

                    Log.i(TAG, "run: socketstate:" + socket.isConnected());
                }
                Log.i(TAG, "run: finally" + socket);
                Log.i(TAG, "run: socketstate:" + socket.isConnected());

                String buffer = "";
                String result = " no message";
                InputStream socketInputStream = null;
                Log.i(TAG, "run:socket.getInputStream()");
                try {
                    socketInputStream = socket.getInputStream();
                    byte[] bytes = new byte[256];
                    // while ((end= socketInputStream.read(bytes)) !=-1)
                    socketInputStream.read(bytes);
                    Log.i(TAG + "bytes", "run: " + bytes[0] + "the second " + bytes[1]);
                    String test = new String(bytes, "UTF-8");
                    Log.i(TAG + "socketinputstream", test);
                    buffer = buffer + test;
                    {
                        result = buffer;
//                        result=buffer;
                        Log.i("readlog", "run: buffer begin" + buffer);
                    }
                    Log.i("readlog", "run: send content:" + content);

                    Log.i("readlog", "run: result received:" + result);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("readlog", "run:ReadThread IOException");

                }
                Log.i("test", "run:buffer end");
                if (!result.equals(""))
                {
                    String returnString = result.trim();
                   // result=dealWithReturnString(returnString);
                    Message msg = new Message();
                    msg.what = handleXinfeng;
                 //   msg.obj = result.toString();
                    msg.obj = returnString;
                    myHandler.sendMessage(msg);
                    Log.i("readlog", "run: resulttoString:" + result);
                }
                String received = result.toString();
                // before = result.toString();
            }
        }
    }
    public void connect() {
        mWifiAdmin.connect();
        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
    }

    public void disconnect() {
        mWifiAdmin.disconnectWifi();
    }

    @Override
    public void onClick(View v) {
        final String sendXinfeng="M1";
        final String sendJinghua="M2";
        final String sendHunfeng="M3";
        final String sendAccident="M4";
        final String sendSleep="M5";
        final String sendformal="M6";
        final String sendHigh="M7";
        final String sendAuto="M8";
        final String sendPower="M9";
        final String sendFure="Ma";
        final String sendForward="Mb";
        switch (v.getId()) {
            case R.id.wifi_2:
                connect();
                printMytips("wifi");break;
            case R.id.forward1:
                //右键Mb
//             forwrard();
                fragment_date_time fragmentDateTime=new fragment_date_time();
                openFragment(fragmentDateTime);
                fragmentDateTime.setValue(timePageValue);
                new MyThread(sendForward).start();
                break;
            case R.id.dealwith_repair://M4
//                Intent intent1=new Intent(Myversion_2_Activity.this,DealWithRepaireActivity.class);
                //ealWithRepaireActivity进入故障处理
//                startActivity(intent1);
                Fragment_dealWithRepair fragment_dealWithRepair =new Fragment_dealWithRepair();
               // fragment_dealWithRepair.setCallback(Myversion_2_Activity.this);
                openFragment(fragment_dealWithRepair);
                new MyThread(sendAccident).start();
                break;
            case R.id.sleepmode:
                    printMytips("M5");//睡眠M5
                    if(sleepmodeon){
                    sleepmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.sleepmodeon));}
                    else {
                        sleepmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.sleepmode));
                    }
                    sleepmodeon=!sleepmodeon;
                    new MyThread(sendSleep).start();break;
            case R.id.formalmode:
                printMytips("M6");//常规6
                if(formalmodeon){
                    formalmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.formalmodeon));
                }
                else {
                    formalmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.formalmode));
                }
                formalmodeon=!formalmodeon;
                new MyThread(sendformal).start();
                break;
            case R.id.highmode:  //高速M7
                printMytips("M7");//
                if(highmodeon){
                    highmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.highmodeon));
                }
                else {
                    highmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.highmode));
                }
                highmodeon=!highmodeon;
                new MyThread(sendHigh).start();
                printMytips("M7");break;
            case R.id.automode:
                printMytips("M8");
                new MyThread(sendAuto).start();break;
            case R.id.furemode:  //附热Ma
                printMytips("Ma");//发送指令M1
                if(fureon){
                   furemodeButton.setImageDrawable(getResources().getDrawable(R.drawable.furemodeon));
                }
                else {
                    furemodeButton.setImageDrawable(getResources().getDrawable(R.drawable.furemode));
                }
                fureon=!fureon;
                new MyThread(sendFure).start();
                break;
            case R.id.powermode://开关M9
                if(poweron){
                    powermodeButton.setImageDrawable(getResources().getDrawable(R.drawable.poermodeon));
                }
                else {
                    powermodeButton.setImageDrawable(getResources().getDrawable(R.drawable.powermode));
                }
                poweron=!poweron;
                new MyThread(sendPower).start();
                deleteFeng();
            deleteModeOn();
            break;
            case R.id.xinfeng:
                getTimeTest();
                //deleteFeng();
                printMytips("M1");//发送指令M1
                if(xinfengfengon){
                   // dealWithReturnString("OG2:52-12-58-2019-5-20");
                    xinfengButton.setImageDrawable(getResources().getDrawable(R.drawable.xiinfengon));
                }
                else {
                    xinfengButton.setImageDrawable(getResources().getDrawable(R.drawable.xinfeng));
                }
                xinfengfengon=!xinfengfengon;
                new MyThread(sendXinfeng).start();
                break;
            case R.id.feng:
                //deleteFeng();
                printMytips("M2");
                if(hunfengon){
                    hunfengButton.setImageDrawable(getResources().getDrawable(R.drawable.hunfengon));
                }
                else {
                    hunfengButton.setImageDrawable(getResources().getDrawable(R.drawable.hunfeng));
                }
                hunfengon=!hunfengon;
                new MyThread(sendHunfeng).start();
                break;
            case R.id.jinghua:
                //deleteFeng();
                if(jinghuaon){
                    jinghuaButton.setImageDrawable(getResources().getDrawable(R.drawable.jinghuaon));
                }
                else {
                    jinghuaButton.setImageDrawable(getResources().getDrawable(R.drawable.jinghua));
                }
                jinghuaon=!jinghuaon;
                new MyThread(sendJinghua).start();
                printMytips("M3");
                break;
            default:
                Log.i(TAG, "onClick: default");
                break;
        }
    }
    private void openFragment(Fragment fragmentName){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.fragment, fragmentName);
        transaction.commit();
    }
    @Override
    public boolean onLongClick(View v) {
        {
            switch (v.getId()) {
                case R.id.wifi_2: {
                    printMytips("long click");
                        editIP();
                    count++;
                    break;
                }
            }
            return true;
        }
    }
    public void printMytips(String str) {
        try {
            if(getApplicationContext()!=null){
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();}
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.i(TAG, "printMytips: kongzhizhen");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            try {
                socket.close();
                xunhuanG = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
    public void editIP() {
        final EditText inputedit=new EditText(Myversion_2_Activity.this);
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Myversion_2_Activity.this);
        alertdialog.setTitle("请输入IP地址与端口");
        alertdialog.setView(inputedit);
        alertdialog.setIcon(R.drawable.wifi);
        alertdialog.setNegativeButton("取消",null);
        alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                if (!editTextString.equals("")) {
                    try{
                    String[] IPandPort;
                    IPandPort = editTextString.split(":");
                    hostName = IPandPort[0];
                    port = Integer.parseInt(IPandPort[1]);
                    printMytips("IP" + hostName + "port" + port);
                        getTimeTest();
                    }
                    catch (Exception e){
                        Log.i(TAG, "onClick: 数组长度超出");
                        printMytips("请输入类似192.168.108.188:8300");
                    }
                }
          }
        });
        alertdialog.show();
    }
        public void deleteModeOn(){
        sleepmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.sleepmode));
        formalmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.formalmode));
        automodeButton.setImageDrawable(getResources().getDrawable(R.drawable.automode));
        highmodeButton.setImageDrawable(getResources().getDrawable(R.drawable.highmode));
       // furemodeButton.setImageDrawable(getResources().getDrawable(R.drawable.furemode));
        printMytips("test");
    }
        public void deleteFeng()
        {
        xinfengfengon=true;
        hunfengon=true;
        jinghuaon=true;
        fureon=true;
        xinfengButton.setImageDrawable(getResources().getDrawable(R.drawable.xinfeng));
        hunfengButton.setImageDrawable(getResources().getDrawable(R.drawable.hunfeng));
        jinghuaButton.setImageDrawable(getResources().getDrawable(R.drawable.jinghua));
        furemodeButton.setImageDrawable(getResources().getDrawable(R.drawable.furemode));
        }
    String getTime(){
        long time=System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date d1=new Date(time);
        String t1=format.format(d1);
       Message message=myHandler.obtainMessage();
       message.what= handleTimeInt;
       message.obj=t1;
       myHandler.sendMessage(message);
//       new MyThread("G1").start();
//       new MyThread("G2").start();
//        dealWithReturnString("OG2:1-234-356-519");
//        dealWithReturnString("OG1:3-234-25-325");
       return t1;
    }
    String getAndesaiMesasge(){
        return anDeSaiMessage;
    }
    public String dealWithReturnString(String returnString) {
        String dealString = "";
        Log.i(TAG, "dealWithReturnString: begin");
        char fiirLetter = '1';
        char secondLetter = '2';
        char thirdLetter = '3';
        char fourLetter = '4';
        String[] state_4_spit={"0"};
        Log.i(TAG, "dealWithReturnString: " + returnString);
        if (returnString.length() >= 4) {
            fiirLetter = returnString.charAt(0);
            secondLetter = returnString.charAt(1);
            thirdLetter = returnString.charAt(2);
            fourLetter = returnString.charAt(3);
        }
        if (fiirLetter == 'O') //判断接收成功
        {
            switch (secondLetter) { //判断按键选择
                case 'G':
                {
                    try{
                        String[] fenhao1String=returnString.split(":");
                        ZValueString=fenhao1String[1];
                    }
                    catch (ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                        Log.i(TAG, "dealWithReturnString: secondArray is not exitst");
                    }
                    //得到Z只后，对Z值进行处理
                    switch (thirdLetter){
                        case '1'://获取当前页面和按钮状态OG1
                            /**
                             * a：a=0（开始界面），a=1（主页面），a=3（时间页面），a=4（故障报警页面），a=5（耗材更换界面）
                             * xyz：x（十六进制），第0位为加热开关状态（1：加热开，0：加热关），第1位为故障处理按钮状态
                             * （0：无故障处理，1：有故障处理）；y（十六进制），从高位到地位分别代表：新风模式、混风、净化模式和开关机按钮
                             * （值为1代表按下，为0代表弹起）；z（十六进制）：睡眠、常规风、自动和高速风; ij（十六进制）：
                             * （每位值为0代表无故障，1代表有故障）；
                             * w（十六进制）：0-2位依次代表HEPA更换、活性炭滤网更换、初效滤网更换按钮状态。
                             */
                            try{
                                state_4_spit=ZValueString.split("-");//a-xyz-ij-w
                                current_page=state_4_spit[0];//a
                                Log.i(TAG, "dealWithReturnString:state_4_spit[0]12 "+pageNum+"thirdletter"+thirdLetter);
                                //goPageNum(pageNum);//自动跳转相应页面，目前少了pageNum=2页面
                                Log.i(TAG, "dealWithReturnString:state_4_spit[0] "+pageNum);
                                String mode_state=state_4_spit[1];//xyz
                                Log.i(TAG, "dealWithReturnString:state_4_spit[1] "+ mode_state);
                                String accident_state=state_4_spit[2];//ij
                                Log.i(TAG, "dealWithReturnString:state_4_spit[2] "+accident_state);
                                String matirialchange_state=state_4_spit[3];
                                //w （十六进制）：0-2位依次代表HEPA更换、活性炭滤网更换、初效滤网更换按钮状态。
                                Log.i(TAG, "dealWithReturnString:state_4_spit[3] "+matirialchange_state);
                                matirialchange(matirialchange_state);
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                Log.i(TAG, "dealWithReturnString: state_4_spit=ZValueString.split(\"-\");");
                            }
                            break;
                        case  '2'://x=2：获取更新值OG2
                            /**
                             * 主页面Z2:aaa-bbbb-cccc;
                             时间页面Z2：dd-ee-ff-gggg-hh-ii;耗材页面Z2：kk-ll-mm
                             故障报警页面Z2：0
                             * Z2是当前页面实时更新的信息
                             aaa:PM2.5值；bbbb：二氧化碳值；cccc：甲醛值；dd：背光时间；ee：时；ff：分；gggg：年；hh：月；
                             ii：日；kk：HEPA更换剩余值；ll：活性炭滤网更换剩余值；mm：初效滤网更换剩余值
                             */
                            switch (current_page){
                                case mainPage://主页面
                                    // Log.i(TAG, "dealWithReturnString: pageMain");
                               Message messageMainPage=myHandler.obtainMessage();
                               messageMainPage.what=hanldleMainpage;
                               messageMainPage.obj=ZValueString;
                                    Log.i(TAG, "dealWithReturnString: "+ZValueString);
                               myHandler.sendMessage(messageMainPage);
                                    goPageNum(mainPage);
                                    break;
                                case timePage://时间页面
                                   goPageNum(timePage);
                                    break;
                                case accidentPage://故障页面
                                    goPageNum(accidentPage);
                                    break;
                                case costMatirier:goPageNum(costMatirier);
                                break;
                            }
                            break;
                    }
                }
                break;
            }
          //  Log.i(TAG, "dealWithReturnString: secondLetter:" + secondLetter);
        }
        else {
            Log.i(TAG, "dealWithReturnString: else zhixing");
//                return "暂未开放响应的指令或网络异常";
            return returnString;
        }
        return returnString;
    }
    private void matirialchange(String matirialchange_state) {
        //w （十六进制）：0-2位依次代表HEPA更换、活性炭滤网更换、初效滤网更换按钮状态。
        switch (matirialchange_state){
            case "0":
                printMytips("0");break;
            case "1":
                printMytips("1");
                break;
            case "2":
                printMytips("2");
                break;
        }
    }
    public void goPageNum(String pageNum) {
        switch (pageNum){
            case "0":
                break;
            case mainPage:
                gotoMainPage();
                printMytips("now you are  in mainPage");
                break;
            case "2":
                break;
            case timePage:
                gotoTimepage();
                break;
            case accidentPage:
                fragment=new Fragment_dealWithRepair();
                break;
            case costMatirier:
                gotocostMatirier();
                break;
        }
        //openFragment(fragment);
    }
    private void gotoMainPage() {
        String aaa_bbb_ccc=ZValueString;
        String[] PM_CO_HCHO=ZValueString.split("-");
        try{
        String PM2_5_Value=PM_CO_HCHO[0];
        String CO2_Value=PM_CO_HCHO[1];
        String HCHO_Value=PM_CO_HCHO[2];
        Log.i(TAG, "gotoMainPage: PM2_5_Value"+PM2_5_Value+"CO2_Value"+CO2_Value+"HCHO_Value"+HCHO_Value);}
        catch (Exception e){
            printMytips("MainPage  error"+ZValueString.length());
        }
    }
    public void gotoTimepage() {
        fragment=new fragment_date_time();
        String s=ZValueString+count;
       // count++;
        timePageValue=ZValueString;
        try{
        fragment_date_time fragment_date_time= (fragment_date_time )fragment;
        fragment_date_time.setValue(s);
        fragmentManager = getFragmentManager();
        transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.fragment, fragment_date_time);
        transaction.commit();}catch (Exception e){
            e.printStackTrace();
        }
    }

    public void gotocostMatirier() {
        fragment=new Fragment_haocai();
        Fragment_haocai fragment_haocai= ( Fragment_haocai )fragment;
        costMatirierString=ZValueString;
        fragment_haocai.setValue(costMatirierString);
        fragmentManager = getFragmentManager();
        transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.fragment, fragment_haocai);
        transaction.commit();
    }
    public interface  Value_State{
        void  setVaueOrState();
    }
}
