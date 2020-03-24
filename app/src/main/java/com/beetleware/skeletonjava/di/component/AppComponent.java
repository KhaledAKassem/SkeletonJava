package com.beetleware.skeletonjava.di.component;

import android.content.Context;
import android.content.SharedPreferences;
import com.beetleware.skeletonjava.SkeletonJavaApp;
import com.beetleware.skeletonjava.di.module.AppModule;
import com.beetleware.skeletonjava.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules ={ AppModule.class, NetworkModule.class})

public interface AppComponent {
    SkeletonJavaApp app();
    Context getContext();
    SharedPreferences getSharedPreferences();
}
