package com.tlcn.mvpapplication;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.tlcn.mvpapplication.mvp.chooselocation.view.ChooseLocationView;
import com.tlcn.mvpapplication.mvp.main.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), mainActivityActivityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testButtonKhac() throws Exception {
        solo.unlockScreen();
        solo.sleep(2000);
        solo.clickOnView(solo.getView(me.riddhimanadib.library.R.id.linearLayoutBar2));
        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.tv_add));
        solo.sleep(2000);
        LinearLayout linearLayout = (LinearLayout) solo.getCurrentActivity().findViewById(R.id.lnl_other);
        assertEquals("Button [Khac] is not visible", linearLayout.getVisibility(), View.VISIBLE);
    }

    @Test
    public void uploadNoImage() throws Exception{
        solo.sleep(2000);
        solo.clickOnView(solo.getView(me.riddhimanadib.library.R.id.linearLayoutBar4));
        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.btn_send));
        TextView toast = (TextView) solo.getView(android.R.id.message);
        solo.sleep(2000);
        assertEquals("toast is not showing", toast.getText().toString(), "Bạn cần đăng kèm hình ảnh!");
    }

    @Test
    public void openActivityChooseLocation() throws Exception{
        solo.clickOnView(solo.getView(me.riddhimanadib.library.R.id.linearLayoutBar4));
        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.rdb_other));
        solo.sleep(2000);
        solo.waitForActivity(ChooseLocationView.class);
        solo.sleep(2000);
        solo.assertCurrentActivity("Launch choose location activity", ChooseLocationView.class);
    }
}
