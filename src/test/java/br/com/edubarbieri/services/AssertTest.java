package br.com.edubarbieri.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AssertTest {

    @Test
    public void test() {
        assertTrue(true);
        assertFalse(false);
        assertEquals(1, 1);
        assertEquals(0.51, 0.51, 0.01);
        assertEquals(Math.PI, 3.14, 0.01);
        
        int i = 5;
        Integer i2 = 5;
        assertEquals(i,  i2.intValue());
        
        assertEquals("teste", "teste");
    }
}
