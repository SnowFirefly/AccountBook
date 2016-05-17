package com.guangzhou.weiwong.accountbook.mvp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

/**
 * Created by alan on 2016/5/13.
 */
public class MyApplication extends Application {
    BugtagsOptions options = new BugtagsOptions.Builder().
            trackingLocation(true).//是否获取位置，默认 true
            trackingCrashLog(true).//是否收集crash，默认 true
            trackingConsoleLog(true).//是否收集console log，默认 true
            trackingUserSteps(true).//是否收集用户操作步骤，默认 true
            crashWithScreenshot(true).    //收集闪退是否附带截图
            versionName("1.0.1").         //自定义版本名称
            versionCode(10).              //自定义版本号
            trackingNetworkURLFilter("http://www.book4account.com/*").//自定义网络请求跟踪的 url 规则，默认 null
            build();

    @Override
    public void onCreate() {
        super.onCreate();
        Bugtags.start("ae096999d2c3b5bdae9befcaa3e4e4fd", this, Bugtags.BTGInvocationEventBubble);
//        LeakCanary.install(this);
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();
        Log.i("MyApplication", "heapSize: " + heapSize + "MB");
    }
}
