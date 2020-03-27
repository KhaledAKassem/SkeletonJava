package com.beetleware.skeletonjava.di.component;

import android.content.Context;
import android.content.SharedPreferences;
import com.beetleware.skeletonjava.SkeletonJavaApp;
import com.beetleware.skeletonjava.di.module.AppModule;
import com.beetleware.skeletonjava.di.module.NetworkModule;
import com.beetleware.skeletonjava.ui.base.BaseViewModel;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules ={ AppModule.class, NetworkModule.class})
public interface AppComponent {
    Context getContext();
    void inject(BaseViewModel baseViewModel);
    SharedPreferences getSharedPreferences();
}
