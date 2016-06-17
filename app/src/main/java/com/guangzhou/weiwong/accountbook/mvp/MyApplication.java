package com.guangzhou.weiwong.accountbook.mvp;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.guangzhou.weiwong.accountbook.dagger2.component.AppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerApiServiceComponent;
import com.guangzhou.weiwong.accountbook.dagger2.component.DaggerAppComponent;
import com.guangzhou.weiwong.accountbook.dagger2.module.ApiServiceModule;
import com.guangzhou.weiwong.accountbook.dagger2.module.AppModule;
import com.guangzhou.weiwong.accountbook.mvp.model.ApiService;

import javax.inject.Inject;

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

    private static MyApplication instance;
    private static AppComponent appComponent;
    @Inject
    static ApiService apiService;

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public static MyApplication getInstance(){
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Bugtags.start("ae096999d2c3b5bdae9befcaa3e4e4fd", this, Bugtags.BTGInvocationEventShake);
        
//        LeakCanary.install(this);
//        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//        int heapSize = manager.getMemoryClass();
//        Log.i("MyApplication", "heapSize: " + heapSize + "MB");

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule())
                .build();
        DaggerApiServiceComponent.builder().build().inject(this);
    }
}
