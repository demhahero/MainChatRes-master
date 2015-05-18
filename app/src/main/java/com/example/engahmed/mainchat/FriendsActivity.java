package com.example.engahmed.mainchat;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.engahmed.models.UserModel;

import java.util.ArrayList;


public class FriendsActivity extends Activity {

    //Service Bind
    DataTaker dataTaker;
    boolean mBound = false;

    LinearLayout verticalLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        verticalLayer = (LinearLayout) findViewById(R.id.verticalLayer);

    }

    public void afterBind(){
        UserModel um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");
        UserModel[] friends = dataTaker.getFriendsOf(um);
        for(final UserModel friend : friends)
        {
            TextView tvf = new TextView(getApplicationContext());
            tvf.setText(" > " + friend.getUsername());
            tvf.setTextColor(Color.BLACK);
            tvf.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(FriendsActivity.this, ChatActivity.class);
                    myIntent.putExtra("friendUsername" , friend.getUsername());
                    FriendsActivity.this.startActivity(myIntent);
                    //Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
                }
            });

            tvf.setTextSize(40);
            tvf.setBackgroundColor(Color.rgb(144,147,255));
            verticalLayer.addView(tvf);
            Space sp = new Space(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(20,30);
            sp.setLayoutParams(lp);
            verticalLayer.addView(sp);
        }
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
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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
