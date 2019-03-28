package com.example.clienttest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.servicetest02.IPerson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button button_bind_service, button_unbind_service;
    private IPerson person;
    private boolean mBound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            person = IPerson.Stub.asInterface(service);
            Log.d(TAG, "person对象的内存地址是：" + person);
            try {
                person.setName("smyh");
                person.setAge(26);
                person.setSex("nan");
                String s = person.getPerson();
                Log.d(TAG, "person的信息是:" + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_bind_service = (Button) findViewById(R.id.button_bind_service);
        button_unbind_service = (Button) findViewById(R.id.button_unbind_service);
        button_bind_service.setOnClickListener(this);
        button_unbind_service.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.example.servicetest02", ".MyService"));
//        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bind_service:
                Intent bindIntent = new Intent();
                bindIntent.setAction("com.example.servicetest02.MyService");
                //
                Intent eintent = new Intent(createExplicitFromImplicitIntent(this, bindIntent));
                //
                bindService(eintent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind_service:
                if (mBound){
                    unbindService(connection);
                    mBound = false;
                }
                break;
            default:
                break;
        }
    }
    //Android5.0中service的intent一定要显性声明
    //将隐性调用变成显性调用。先定义一个函数：
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;}

}
