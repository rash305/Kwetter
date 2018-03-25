package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class UserROleTest {

    Group role;
    @Before
    public void setUp() throws Exception {
        role = new Group("Admin");
    }

        @Test
    public void setId() throws Exception {
  //      role.setId(10);
 //       assertEquals(role.getId(), 10);
    }

    @Test
    public void setName() throws Exception {
        role.setName("Standard");
        assertEquals(role.getName(), "Standard");
    }

}