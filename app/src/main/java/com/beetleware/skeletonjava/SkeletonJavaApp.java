package com.beetleware.skeletonjava;

import android.app.Application;

import com.beetleware.skeletonjava.di.component.AppComponent;
import com.beetleware.skeletonjava.di.component.DaggerAppComponent;
import com.beetleware.skeletonjava.di.module.AppModule;


public class SkeletonJavaApp extends Application {
       public AppComponent component =  DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

}
