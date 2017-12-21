package com.tlcn.mvpapplication;

import com.tlcn.mvpapplication.utils.DateUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.print(DateUtils.getDateFormat(DateUtils.getTodayEnd()));
        System.out.print(DateUtils.getDateFormat(DateUtils.getDay()));
        System.out.print(DateUtils.getDateFormat(DateUtils.getFirstDateOfWeek(DateUtils.getDay())));
        assertEquals(4, 2 + 2);
    }
}