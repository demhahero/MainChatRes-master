package com.example.engahmed.mainchat;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.engahmed.models.InstantMessageModel;
import com.exmaple.engahmed.models.UserModel;


public class ChatActivity extends Activity {

    public UserModel friend;
    //Service Bind
    DataTaker dataTaker;
    boolean mBound = false;

    public boolean onOFF;

    EditText ETmessage;
    LinearLayout verticalLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Cancel all notes
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(122);

        onOFF = true;

        verticalLayer = (LinearLayout) findViewById(R.id.verticalLayer2);
        ETmessage = (EditText) findViewById(R.id.ETmessage);

        String username = getIntent().getStringExtra("friendUsername");
        friend = new UserModel();
        friend.setUsername(username);
        //Toast.makeText(getApplicationContext(), "clicked" + friend.getUsername(), Toast.LENGTH_SHORT).show();
    }

    public void onSendClick(View v){
        InstantMessageModel imm = new InstantMessageModel();
        imm.setSenderID(dataTaker.authentication.getUserInfo().getUsername());
        imm.setRecipientID(friend.getUsername());
        imm.setBody(ETmessage.getText().toString());

        TextView tv = new TextView(this);
        tv.setText(dataTaker.authentication.getUserInfo().getUsername()+": "+ETmessage.getText().toString());
        tv.setTextSize(20);
        tv.setBackgroundColor(Color.LTGRAY);
        verticalLayer.addView(tv);
        focusOnButtons();
        dataTaker.instantMessaging.sendInstantMessage(imm);
        ETmessage.setText("");
    }

    public void afterBind() {
        dataTaker.instantMessaging.registerMessagesListener(this);
        dataTaker.instantMessaging.messageListener();

    }

    private final void focusOnButtons(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScrollView sv = (ScrollView)findViewById(R.id.scroll);
                sv.fullScroll(sv.FOCUS_DOWN);
            }
        },1000);
    }

    public void onInstantMessageComeEvent(InstantMessageModel imm){
        if(friend.getUsername().equals(imm.getSenderID())) {
            TextView tv = new TextView(this);
            tv.setText(imm.getSenderID() + " : " + imm.getBody());
            tv.setTextSize(20);
            tv.setBackgroundColor(Color.CYAN);
            verticalLayer.addView(tv);
            focusOnButtons();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            onOFF = false;
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        onOFF = false;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        onOFF = true;
    }

    protected void onDestroy() {
        onOFF = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, DataTaker.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DataTaker.LocalBinder binder = (DataTaker.LocalBinder) service;
            dataTaker = binder.getService();

            mBound = true;

            afterBind();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
