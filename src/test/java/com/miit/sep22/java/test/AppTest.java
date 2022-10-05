package com.miit.sep22.java.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 *
 * @Author : Vijay
 */
public class AppTest {


    App app = new App();

    @Test
    public void isPositiveNumber_true() {
        assertTrue(app.isPositiveNumber(2));
    }

    @Test
    public void isPositiveNumber_false() {
        assertFalse(app.isPositiveNumber(-6));
    }


}
