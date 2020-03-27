package com.beetleware.skeletonjava.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.beetleware.skeletonjava.SkeletonJavaApp;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides App instance of the application
 *
 * @return the instance of application
 */

@Module
public class AppModule {

    SkeletonJavaApp app ;
    public AppModule(SkeletonJavaApp app) {
        this.app = app;
    }

    /**
     * Provides App instance of the application
     *
     * @return the instance of application
     */
    @Provides
    @Singleton
    Application provideApplication() {
        return new Application();
    }

    /**
     * provide context to be used in data repository
     *
     * @return applicationContext
     */
    @Provides
    @Singleton
    Context provideContext(){
        return app.getApplicationContext();
    }

    /**
     * provide shared preference to store data
     *
     * @return shared preference instance
     */
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences (){
        return app.getSharedPreferences("SHARED_PREFERENCE_NAME",Context.MODE_PRIVATE);
    }


}
