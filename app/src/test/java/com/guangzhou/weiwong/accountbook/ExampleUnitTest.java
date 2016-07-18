package com.guangzhou.weiwong.accountbook;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println("---------- @Test method test1 called ----------");
    }

    @Test
    public void str_isEqual() {
        assertEquals("str", "st" + "r");
        System.out.println("---------- @Test method test2 called ----------");
    }
}