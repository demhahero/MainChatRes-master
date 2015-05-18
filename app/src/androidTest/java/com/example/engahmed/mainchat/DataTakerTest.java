package com.example.engahmed.mainchat;

import android.test.AndroidTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EngAhmed on 03/05/2015.
 */
public class DataTakerTest  extends AndroidTestCase {

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test something
     */
    public final void testGetUser() {
        DataTaker dt = new DataTaker();
        assertTrue(dt!=null);
        //HashMap<String, String> user = dt.getUser("Ahmed" , "pass");
        //assertEquals(3,user.size());
    }
}
