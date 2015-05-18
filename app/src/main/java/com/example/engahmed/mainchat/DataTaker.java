package com.example.engahmed.mainchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.exmaple.engahmed.models.InstantMessageModel;
import com.exmaple.engahmed.models.UserModel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by EngAhmed on 03/05/2015.
 */
public class DataTaker  extends Service {

    private static Timer timer = new Timer();

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    public Authentication authentication;

    public InstantMessaging instantMessaging;
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */

    private Handler handler;

    public class LocalBinder extends Binder {
        DataTaker getService() {
            // Return this instance of LocalService so clients can call public methods
            return DataTaker.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        handler = new Handler();
        timer.scheduleAtFixedRate(new mainTask(), 0, 5000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.post(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(DataTaker.this, "Service Got Destroyed!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private OnInstantMessgaeEventListener mOnInstantMessgaeEventListener;
    String username = "Ahmed";
    String password = "pass";
    String ID = "1";
    UserModel [] friends = new UserModel[2];
    UserModel currentUser;

    public DataTaker(){

        authentication = new Authentication(this);
        instantMessaging = new InstantMessaging(this);

        friends[0] = new UserModel();
        friends[0].setUsername("Ali");
        friends[1] = new UserModel();
        friends[1].setUsername("Yahya");


    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            handler.post(new Runnable() {

                @Override
                public void run() {
                    InstantMessageModel imm = new InstantMessageModel();
                    Calendar c = Calendar.getInstance();
                    int seconds = c.get(Calendar.SECOND);
                    int minuts = c.get(Calendar.MINUTE);
                    int hours = c.get(Calendar.HOUR);
                    imm.setBody("Periodic message, " + hours + ":" + minuts + ":" + seconds);
                    imm.setSenderID("Ali");
                    imm.setRecipientID("Ahmed");
                    doInstantMessageEvent(imm);
                }
            });
        }
    }


    public void setOnInstantMessgaeEventListener(OnInstantMessgaeEventListener listener) {
        mOnInstantMessgaeEventListener = listener;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }

    public boolean doLogin(UserModel um){
        if(um.getUsername().equals(this.username) && um.getPassword().equals(this.password)) {
            currentUser = new UserModel();
            currentUser = um;
            currentUser.setID(1);
            return true;
        }
        return false;
    }

    public boolean doLogout() {
        // Destory the session with the server here

        currentUser = null;
        return true;
    }

    public UserModel[] getFriendsOf(UserModel um) {
        return this.friends;
    }

    public boolean addNewUser(UserModel um) {
        this.username = um.getUsername();
        this.password = um.getPassword();
        return true;
    }

    public boolean sendInstantMessage(InstantMessageModel imm) {
        //Simulate Replay from the recipient
        imm.setBody("Replay:" + imm.getBody());
        imm.setSenderID(imm.getRecipientID());
        doInstantMessageEvent(imm);
        return true;
    }

    public void doInstantMessageEvent(InstantMessageModel imm) {

        ChatActivity ca = (ChatActivity) instantMessaging.getlistener();

        if(instantMessaging.getlistener() != null && ca.onOFF && imm.getSenderID().equals(ca.friend.getUsername())) {
            if (mOnInstantMessgaeEventListener != null)
                mOnInstantMessgaeEventListener.onInstantMessageEvent(imm); // event object :)
        }
        else
            {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

                int icon = R.mipmap.ic_launcher;
                CharSequence tickerText = imm.getSenderID(); // ticker-text
                long when = System.currentTimeMillis();
                Context context = getApplicationContext();
                CharSequence contentTitle = imm.getRecipientID();
                CharSequence contentText = imm.getBody();
                Intent notificationIntent = new Intent(this, ChatActivity.class);
                notificationIntent.putExtra("friendUsername",imm.getSenderID());
                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                Notification notification = new Notification(icon, tickerText, when);
                notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

                mNotificationManager.notify(122, notification);
            }
    }

    public boolean sendInstantMessageACK(InstantMessageModel imm) {
        return true;
    }
}
