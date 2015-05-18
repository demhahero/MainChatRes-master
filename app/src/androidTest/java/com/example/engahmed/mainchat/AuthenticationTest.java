package com.example.engahmed.mainchat;

import android.test.AndroidTestCase;

import com.exmaple.engahmed.models.UserModel;


/**
 * Created by EngAhmed on 03/05/2015.
 */
public class AuthenticationTest extends AndroidTestCase {
    DataTaker dt;
    UserModel um;
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();

      //  dt.authentication.login(um);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test Login
     */
    public final void testLogin() {
        dt = new DataTaker();
        um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");

        boolean res = dt.authentication.login(um);
        assertEquals(true,res);
    }

    /**
     * Test Logout
     */
    public final void testLogout() {
        dt = new DataTaker();
        um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");

        dt.authentication.login(um);
       // boolean res = dt.authentication.logout();
       // assertEquals(true,res);

        //assertNull(dt.authentication.getUserInfo());
    }


    /**
     * Test getUserInfo
     */
    public final void testgetUserInfo() {
        dt = new DataTaker();
        um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");

        boolean res = dt.authentication.login(um);
        assertNotNull(dt.authentication.getUserInfo());
    }

    /**
     * Test Logout
     */
    public final void testgetFriends() {
        dt = new DataTaker();
        um = new UserModel();
        um.setUsername("Ahmed");
        um.setPassword("pass");
        assertNotNull(dt.authentication.getFirendList());
    }

    public final void testRegisterNewUser(){
        dt = new DataTaker();
        um = new UserModel();
        UserModel newUser = new UserModel();
        newUser.setUsername("Mohamed");
        newUser.setPassword("pass2");
        boolean res = dt.authentication.addNewUser(newUser);
        assertEquals(true, res);

        res = dt.authentication.login(newUser);
        assertEquals(true, res);
        assertEquals("Mohamed", dt.authentication.getUserInfo().getUsername());
    }
}
