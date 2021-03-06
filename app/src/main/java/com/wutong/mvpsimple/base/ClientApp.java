package com.wutong.mvpsimple.base;

import android.app.Application;

import com.wutong.mvpsimple.common.di.component.AppComponent;
import com.wutong.mvpsimple.common.di.component.DaggerAppComponent;
import com.wutong.mvpsimple.common.di.module.AppModule;

/**
 * Created by 吴同 on 2016/7/25 0025.
 */
public class ClientApp extends Application {
    private static final String TAG = ClientApp.class.getSimpleName() + "_TAG";
    private AppComponent mAppComponent;
    private static ClientApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDagger2();


    }


    private void initDagger2() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static ClientApp getInstance() {
        return instance;
    }

    public static AppComponent getAppComponent() {
        return ClientApp.getInstance().mAppComponent;
    }





}
