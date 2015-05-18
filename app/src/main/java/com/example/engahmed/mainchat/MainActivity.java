package com.example.engahmed.mainchat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.exmaple.engahmed.models.UserModel;


public class MainActivity extends Activity {
    EditText username;
    EditText password;


    //Service Bind
    DataTaker dataTaker;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.ETusername);
        password = (EditText) findViewById(R.id.ETpassword);

        Intent intent = new Intent(this, DataTaker.class);
        startService(intent);
    }

    public void BTloginOnClick(View v) {
        UserModel user = new UserModel();
        //user.setUsername(username.getText().toString());
        //user.setPassword(password.getText().toString());
        user.setUsername("Ahmed");
        user.setPassword("pass");

        if (mBound) {
            if (dataTaker.authentication.login(user)) {
               // Toast.makeText(getApplicationContext(), "Login passed", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MainActivity.this, FriendsActivity.class);
                MainActivity.this.startActivity(myIntent);
                finish();
            } else
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "not Bound", Toast.LENGTH_LONG).show();
        }
    }

    public void afterBind(){
        if(dataTaker.authentication.getUserInfo() != null){
            Intent myIntent = new Intent(MainActivity.this, FriendsActivity.class);
            MainActivity.this.startActivity(myIntent);
            finish();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
