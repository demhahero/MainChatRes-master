package com.example.engahmed.mainchat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.exmaple.engahmed.models.InstantMessageModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by EngAhmed on 04/05/2015.
 */
public class InstantMessaging{
    DataTaker dt;
    String[] msg = null;
    Object listener ;
    Method method;

    public InstantMessaging(DataTaker dt){
        this.dt = dt;
    }

    public boolean sendInstantMessage(InstantMessageModel imm) {
        return dt.sendInstantMessage(imm);
    }

    public void messageListener(){

        dt.setOnInstantMessgaeEventListener(new OnInstantMessgaeEventListener() {
            public void onInstantMessageEvent(InstantMessageModel imm) {
                onMessageComeEvent(imm);
            }
        });

    }

    public void registerMessagesListener(Object o){
        listener = o;
        messageListener();
    }

    public Object getlistener(){
        return  listener;
    }

    public void onMessageComeEvent(InstantMessageModel imm)
    {

        try {
            method = listener.getClass().getMethod("onInstantMessageComeEvent" ,InstantMessageModel.class );
            try {
                method.invoke(listener, imm);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public boolean sendInstantMessageACK(InstantMessageModel imm) {
        return dt.sendInstantMessageACK(imm);
    }
}
