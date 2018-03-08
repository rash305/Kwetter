package Collectionrepository;

import model.USER_ROLE;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Sjoerd on 7-3-2018.
 */
public class USER_ROLETest {

    USER_ROLE role;
    @Before
    public void setUp() throws Exception {
        role = new USER_ROLE("Admin");
    }

        @Test
    public void setId() throws Exception {
        role.setId(10);
        assertEquals(role.getId(), 10);
    }

    @Test
    public void setName() throws Exception {
        role.setName("Standard");
        assertEquals(role.getName(), "Standard");
    }

}