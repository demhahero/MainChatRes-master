package com.example.engahmed.mainchat;

import android.test.AndroidTestCase;
import android.util.Log;

import com.exmaple.engahmed.models.InstantMessageModel;
import com.exmaple.engahmed.models.UserModel;

/**
 * Created by EngAhmed on 04/05/2015.
 */
public class InstantMessagingTest extends AndroidTestCase {
    Authentication auth;
    InstantMessaging ch;
    DataTaker dt;
    String y= "";
    protected void setUp() throws Exception {
        super.setUp();

        dt = new DataTaker();
        auth = new Authentication(dt);
        ch = new InstantMessaging(dt);

        UserModel um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");
        auth.login(um);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public final void testSendInstantMessage(){
        InstantMessageModel imm = new InstantMessageModel();
        imm.setID(2);
        imm.setSenderID("Ali");
        imm.setBody("Hello how are u?");
        assertTrue(ch.sendInstantMessage(imm));
    }

    public final void testMessageListner(){

        ch.registerMessagesListener(this);

        InstantMessageModel imm = new InstantMessageModel();
        imm.setID(2);
        imm.setSenderID("Ahmed");
        imm.setRecipientID("Ali");
        imm.setBody("Hello how are u?");
        ch.sendInstantMessage(imm);

        assertEquals("Ali" , y);
    }

    public void onInstantMessageComeEvent(InstantMessageModel imm){
        y=imm.getSenderID();
        ch.sendInstantMessageACK(imm);
    }


}
