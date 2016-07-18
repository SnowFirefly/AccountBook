package com.guangzhou.weiwong.accountbook;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Tower on 2016/7/2.
 */
public class JUnitLifeCycle {

    // 该方法在所有测试方法之前调用，只会被调用一次（静态方法）
    @BeforeClass
    public static void prepare() {
        System.out.println("---------- @BeforeClass method prepare called ----------");
    }

    // 该方法在每次测试方法调用前都会调用
    @Before
    public void initTest() {
        System.out.println("---------- @Before method initTest called ----------");
    }

    // 说明了该方法需要测试
    @Test
    public void test1() {
//        assertEquals("test1", "test");
        System.out.println("---------- @Test method test1 called ----------");
    }

    @Test
    public void test2() {
        System.out.println("---------- @Test method test2 called ----------");
    }

    // 该方法在每次测试方法调用后都会调用
    @After
    public void doneTest() {
        System.out.println("---------- @After method doneTest called ----------");
    }

    // 该方法在所有测试方法之后调用，只会被调用一次（静态方法）
    @AfterClass
    public static void finish() {
        System.out.println("---------- @AfterClass method finish called ----------");
    }

    // 忽略该方法
    @Ignore
    public void ignore() {
        System.out.println("---------- @Ignore method ignore called ----------");
    }
}
