package com.testclient.luominming.andesai_luo;

import android.app.Activity;

//<!--android:screenOrientation="portrait"-->
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivitytest extends Activity implements View.OnClickListener,View.OnLongClickListener  {
    String hostName="172.16.106.188";
    int port=8300;
    //String hostName = "192.168.43.178";
//    int port = 30000;
    int localport = 40696;
    String TAG = "test";
    TextView result, show;
    Button send;
    EditText input, portedit;
    int sercretNum = 1, sercretNum2 = 1;
    private ImageButton connect;
    private ImageButton aButton, bButton, cButton;
    private Button disconnect;
    private WifiAdmin mWifiAdmin;
    public String content;
    public  TextView L1_on_off,L2_on_off,L3_on_off;
    String inputContent;
    MyThread clientTHread;
    String before;
    Timer timer = new Timer();
    boolean IsreadFirst = true;
    Socket socket = new Socket();
    boolean IsSendOK = true;
    OutputStream ou=null;
    boolean toast=true;
    int secret=1;
    boolean FirstConnect=true;
    InetSocketAddress inetSocketAddress;
    TextView IPconfirm;
    int count=0;
    boolean xunhuanG=true;
    private int showFailTip=0;

    public Handler myHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10: {
                    result.append("server:" + msg.obj + "\n");
                    if (!msg.obj.equals(null))
                        result.setText("" + msg.obj);
                    Log.i("readlog", "handleMessage: msg" + msg.obj);
                }
                break;

                case 3:
                    if(showFailTip<5)
                    {
                        dayin("连接SSID网络失败");
                    showFailTip++;
                    }
                    else {
                        try {
                            showFailTip=1;
                            socket.close();
                            xunhuanG=true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        timer.cancel();
                    }
                    break;
                case 2:
                    dayin("connected");break;
                case 6://P1值显示
                {
                    L1_on_off.setText(msg.obj.toString());
                    break;
                }

                case 7://M值显示
                { L2_on_off.setText(msg.obj.toString());
                    break;
                }
                case 8://L值显示
                {
                    L3_on_off.setText(msg.obj.toString());
                    break;
                }

                default:
                    break;
            }
//
//            if (msg.what == 1) {
////
//            } else if(msg.what==2){
//
//            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutversion_1);
        initView();
       // result.setMovementMethod(ScrollingMovementMethod.getInstance());
        mWifiAdmin = new WifiAdmin(MainActivitytest.this);
    }
    private void setListener() {
        connect.setOnClickListener(MainActivitytest.this);
        disconnect.setOnClickListener(MainActivitytest.this);
        aButton.setOnClickListener(this);
        bButton.setOnClickListener(this);
        cButton.setOnClickListener(this);
//        show.setOnClickListener(this);
       // send.setOnClickListener(this);
        //设置长按功能
        aButton.setOnLongClickListener(this);
        bButton.setOnLongClickListener(this);
        cButton.setOnLongClickListener(this);
        connect.setOnLongClickListener(this);
    }
    private void initView() {
        connect = findViewById(R.id.connect);
        disconnect = (Button) findViewById(R.id.disconnect);
        portedit = findViewById(R.id.port);
        aButton = findViewById(R.id.aa);
        result = (TextView) findViewById(R.id.result);
       // send = (Button) findViewById(R.id.send);
        input = (EditText) findViewById(R.id.input);
        bButton = findViewById(R.id.bb);
        cButton = findViewById(R.id.cc);
        //show = findViewById(R.id.show);
        L1_on_off=findViewById(R.id.L1_on_off);
        L2_on_off=findViewById(R.id.L2_on_off);
        L3_on_off=findViewById(R.id.L3_on_off);
        IPconfirm=findViewById(R.id.IPconfirm);
        setListener();
    }

    class MyThread extends Thread {
        String received = "";
        int i = 1;
        String before = "before";
        String P_STAETE="";
        String L_STATE="";
        String M_STATE="";
        String P_STATE="";
        String G_STATE="暂未执行获取PM2.5值操作";

        BufferedReader bff = null;
        Message msg;
        public MyThread(String str) {
            content = str;
            Log.i(TAG, "MyThread: mythead()");
        }
        @Override
        public void run() {
            //定义消息
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
                        toast=true;
                        ou = socket.getOutputStream();
                        Log.i(TAG, "run: connectedOrnot2" + socket.isConnected());
                        //  socket.setSoTimeout(100);
                        Log.i(TAG, "run: bff" + content);
                        // getGvalue();
                        if(socket.isConnected()&&toast)
                        {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = "connected";
                            //发送消息 修改UI线程中的组件
                            myHandler.sendMessage(msg);
                            toast=false;
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
                    //  if (IsreadFirst)
                    {IsreadFirst = false;
                        Log.i(TAG, "run: Isreadfirst:new ReadThread" + IsreadFirst);
                    }
                    // Log.i(TAG, "run: begin in socket in");
                    // Log.i(TAG, "run: EOF");
                    // }
                    //读取发来服务器信息

                    //关闭各种输入输出流
//                    ou.close();
//                  //  socket.close();
////                  IsSendOK=false;
                } catch (SocketTimeoutException aa) {
                    //连接超时 在UI界面显示消息
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = "安德赛提醒您,服务器连接失败！请检查网络是否打开";
                    //发送消息 修改UI线程中的组件
                    myHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "run: MythreadIOExeption");
                } finally {
                    Log.i(TAG, "run: finally"+socket);
                    Log.i(TAG, "run: socketstate:"+socket.isConnected());
//                    try {
//                        socket.shutdownOutput();
//                        Log.i(TAG, "run: tupoutdown");
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
                Log.i(TAG, "run: finally"+socket);
                Log.i(TAG, "run: socketstate:"+socket.isConnected());

                String buffer="";
                String result="";
                InputStream socketInputStream = null;
                Log.i(TAG, "run:socket.getInputStream()");
                try {
                      socketInputStream = socket.getInputStream();
                    byte[] bytes=new byte[256];
                    // while ((end= socketInputStream.read(bytes)) !=-1)
                    socketInputStream.read(bytes);
                    Log.i(TAG+"bytes", "run: "+bytes[0]+"the second "+bytes[1]);
                    String test=new String(bytes,"UTF-8");
                    Log.i(TAG+"socketinputstream", test);
                    buffer=buffer+test;
                    {
                        result = buffer;
//                        result=buffer;
                        Log.i("readlog", "run: buffer begin" + buffer);
                    }
                    Log.i("readlog", "run: send content:" + content);

                    Log.i("readlog", "run: result received:" +result);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("readlog", "run:ReadThread IOException");
                }
                Log.i("test", "run:buffer end");
                //if (!result.equals(""))
                {   String returnString=result.trim();
                    result=dealWithReturnString(returnString);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result.toString();
                    myHandler.sendMessage(msg);
                    Log.i("readlog", "run: resulttoString:"+result);
                }
                String received = result.toString();
                // before = result.toString();
            }
//            try {          //不能关闭
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        private String  dealWithReturnString(String returnString) {
            String dealString ="";
            Log.i(TAG, "dealWithReturnString: begin");
            char fiirLetter='1';
            char secondLetter='2';
            char thirdLetter='3';
            char fourLetter='4';
            Log.i(TAG, "dealWithReturnString: "+returnString);
            if(returnString.length()>=4){
                fiirLetter=returnString.charAt(0);
                secondLetter=returnString.charAt(1);
                thirdLetter=returnString.charAt(2);
                fourLetter=returnString.charAt(3);}
            if(fiirLetter=='O') //判断接收成功
            {
                switch (secondLetter) {   //判断按键选择
                    case 'P'://按下Power键的
                    {
                        Log.i(TAG, "dealWithReturnString: secondLetter" + secondLetter);
                        switch (fourLetter) {
                            case '1': {
                                P_STATE = "关机";break;
//                                return "关机";
                            }
                            case '2': {
                                P_STATE = "一档";break;
//                                return "一档";
                            }
                            case '4': {
                                P_STATE = "二档";break;
//                                return "二档";
                            }
                            case '8': {
                                P_STATE = "三档";break;
//                                return "三档";
                            }
                            case '0': {
                                P_STATE = "四档";break;
//                                return "四档";
                            }
                        }
                        Message msg6 = new Message();
                        msg6.what = 6;
                        msg6.obj = P_STATE;
                        myHandler.sendMessage(msg6);
                    }
                    break;
                    case 'L': {
                        switch (fourLetter) {
                            case '1': {
                                L_STATE = "加热开";break;
//                                return "加热开";
                            }
                            case '2': {
                                L_STATE = "加热关";break;
//                                return "加热关";
                            }
                        }
                        Message msg7 = new Message();
                        msg7.what = 7;  //L返回的数值
                        msg7.obj = L_STATE;
                        myHandler.sendMessage(msg7);
                        break;
                    }
                    case 'M': {
                        switch (fourLetter) {
                            case '1':
                                M_STATE = "手动模式";break;
//                                return "手动模式";
                            case '2':
                                M_STATE = "自动模式";break;
//                                return "自动模式";
                            case '4':
                                M_STATE = "睡眠模式";break;
//                                return "睡眠模式";

                        }
                        Message msg8 = new Message();
                        msg8.what = 8;  //L返回的数值
                        msg8.obj = M_STATE;
                        myHandler.sendMessage(msg8);
                    }
                    break;

                    case 'G':
                    {String Gvalue = returnString.substring(4);
                        Log.i(TAG, "dealWithReturnString: Gvalue" + Gvalue);
                        switch (thirdLetter)
                        {
                            case '1':
                                G_STATE = "PM2.5:"+Gvalue;break;
//                                return G_STATE;
                            case '2':
                                G_STATE = "G_STATE_2";break;
//                                return G_STATE;
                            case '3':
                                G_STATE = "other";break;
//                                return "other";
                            default:

                                break;
                        }
                        Message msg9 = new Message();
                        msg9.what = 10;  //G返回的数值
                        msg9.obj = G_STATE;
                      //  msg9.obj=returnString;
                        myHandler.sendMessage(msg9);
                        return Gvalue;
                    }
                }
                Log.i(TAG, "dealWithReturnString: secondLetter:"+secondLetter);
            }
            else
            {
                Log.i(TAG, "dealWithReturnString: else zhixing");
//                return "暂未开放响应的指令或网络异常";
                return returnString;
            }

            return  returnString;
        }
    }

    @Nullable
    private String getlocalip() {
        Log.i(TAG, "getlocalip: ");
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        //  Log.d(Tag, "int ip "+ipAddress);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        Log.i(TAG, "getlocalip: " + Formatter.formatIpAddress(dhcpInfo.gateway));

        Log.i(TAG, "getlocalipserver: " + Formatter.formatIpAddress(dhcpInfo.serverAddress));
        Log.i(TAG, "wifiInfoID: " + wifiInfo.getNetworkId());

        Log.i(TAG, "getlocalip: " + wifiInfo.getSSID());//路由器名称
        if (ipAddress == 0) return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }


    public void IPButton(View view) {
        secret++;
        String IP=input.getText().toString();
        String port1=portedit.getText().toString();
        { if(!port1.equals("")){
            port=Integer.parseInt(port1);}
            if(!IP.equals("")){
                hostName=IP;}
            dayin("IP："+hostName+" "+"端口："+port);
            if(secret>10){
                show.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
            }
        }
       // connect.setText("WIFI连接");
        portedit.setVisibility(View.INVISIBLE);
        input.setVisibility(View.INVISIBLE);
        IPconfirm.setVisibility(View.INVISIBLE);
//        new MyThread("G1").start();
        if(xunhuanG){
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    new MyThread("G1").start();
                }
            }, 1000, 5000);
            xunhuanG=false;
        }
        count++;

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
        switch (v.getId()) {
            case R.id.connect:
                connect();
                Log.i("test", "onClick: "+getlocalip());
                break;
            case R.id.disconnect:

                //disconnect();
            {
                try {
                    socket.close();
                   timer.cancel();
                   xunhuanG=true;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                socket=new Socket();
            }
            break;
            case R.id.aa:
//                content="a";break;
//               Log.i(TAG, "onClick: aaa");
//                sendMessageButton("ABC");break;
            {   new MyThread("P1").start();
                //new MyThread("G1").start();
//                String test="ABC123";
//                printMytips(test.substring(3)); //打印123

            dayin("正在切换中，请稍后");
            Log.i(TAG, "onClick: test"+socket);
            break;
            }
            case R.id.bb:
                new MyThread("L1").start();
                dayin("正在切换中，请稍后");
                break;
            case R.id.cc:
                new MyThread("M1").start();
                //input.setText(getLocalIpAddress());
                dayin("正在切换中，请稍后");
                break;
            case R.id.show:
                showguanwang();break;
            case R.id.send:
                String inputContent = input.getText().toString();
                // result.setText("安德塞APP客户端:" + inputContent + "\n");
                //启动线程 向服务器发送和接收信息
                // localNet("ip");
                Log.i("Isreable", "onClick: ");
                // if(clientTHread==null){
                clientTHread= new MyThread(inputContent);
                clientTHread.start();break;
            default:
                Log.i(TAG, "onClick: default");
                break;
        }
    }
    @Override
    public boolean onLongClick(View v) {
        {
            switch (v.getId()) {
                case R.id.aa:
                {
                    new MyThread("P2").start();
                    dayin("正在开关机切换，请稍后");
                    Log.i(TAG, "onClick: test"+socket);
//                    if(socket!=null || socket.isConnected())
//                    {
//                        try {
//                            socket.close();
//                            xunhuanG=true;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        timer.cancel();
//                    }
                    break;
                }
                case R.id.bb:
                {
                    new MyThread("L2").start();
                    dayin("正在切换中,请稍后");
                    break;
                }
                case R.id.cc:
                {
                    new MyThread("M2").start();
                    dayin("正在切换中,请稍后");
                break;}
                case R.id.connect:
                {
                    editIP();
//                    if(count%2==0){
//                       // connect.setText("点击确认");
//                        portedit.setVisibility(View.VISIBLE);
//                        input.setVisibility(View.VISIBLE);
//                        IPconfirm.setVisibility(View.VISIBLE);
//                    }else {
//                       // connect.setText("WIFI连接");
//                        portedit.setVisibility(View.INVISIBLE);
//                        input.setVisibility(View.INVISIBLE);
//                        IPconfirm.setVisibility(View.INVISIBLE);
//                    }
//                    count++;
                    break;
                }
            }
            return true;
        }
    }
    private void showguanwang() {
//        Uri uri = Uri.parse("https://www.riskstorm.com/company/91441300MA4WK7QB7X#event");//密码qjfd
//        //Uri uri = Uri.parse("https://pan.baidu.com/disk/home?errno=0&errmsg=Auth%20Login%20Sucess&&bduss=&ssnerror=0#list/vmode=list&path=%2F%E6%88%91%E7%9A%84%E8%AE%BA%E6%96%87");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
        // if(sercretNum>=3)
        {try {
            socket.close();
            xunhuanG=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
            socket=new Socket();
        }
        dayin("IP："+hostName+" "+"port："+port);
    }
    private void sendMessageButton(String a) {
        {     content=a;
            if(clientTHread==null)
            {
                clientTHread=new MyThread(a);
                clientTHread.start();
                IsSendOK=true;
            }
            // new MyThread(a).start();
        }
    }
    public void dayin(String str){
        Toast.makeText(MainActivitytest.this,str,Toast.LENGTH_SHORT).show();

    }
    public String getLocalIpAddress()
    {try
    {for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
    {
        NetworkInterface intf = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
        {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress()&!inetAddress.isLinkLocalAddress())
            {  Log.i(TAG, "getLocalIpAddress: "+inetAddress.getHostAddress().toString());
                return inetAddress.getHostAddress().toString();
            }
        }
    }
    }
    catch (SocketException ex)
    {
        Log.e("IpAddress", ex.toString());
    }
        return null;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(socket!=null){
            try {
                socket.close();
                xunhuanG=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            timer.cancel();
        }
    }
    public void editIP() {
        final EditText inputedit=new EditText(MainActivitytest.this);
        inputedit.setHint("格式如 172.16.108.188:8300");
//       final SharedPreferences  sharedPreferences=getPreferences(getApplicationContext().MODE_PRIVATE);
//       String sharePrefhostName= sharedPreferences.getString("IPandPort","176.16.108.188:8300");
//        inputedit.setText(sharePrefhostName);
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(MainActivitytest.this);
        alertdialog.setTitle("请输入IP地址与端口");
        alertdialog.setView(inputedit);
        alertdialog.setIcon(R.drawable.wifi);
        alertdialog.setNeutralButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(socket!=null || socket.isConnected()){
                    try {
                        socket.close();
                        xunhuanG=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    timer.cancel();
                }
            }
        });
        //alertdialog.setNegativeButton("取消",null);
        alertdialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String editTextString = inputedit.getText().toString();
                try {
                    if (!editTextString.equals("")) {
                        String[] IPandPort;
                        IPandPort = editTextString.split(":");
                        hostName = IPandPort[0];
                        port = Integer.parseInt(IPandPort[1]);
                        dayin("IP" + hostName + "  port" + port);
//                        SharedPreferences.Editor editor=sharedPreferences.edit();
//                        editor.putString("IPandPort",editTextString);
//                        editor.commit();
                    }
                }
                catch (Exception e){
                    dayin("请输入正确的IP地址与端口");
                }

                if(xunhuanG){
                    timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new MyThread("G1").start();
                        }
                    }, 1000, 5000);
                    xunhuanG=false;
                }
            }
        });
        alertdialog.show();

    }
}
