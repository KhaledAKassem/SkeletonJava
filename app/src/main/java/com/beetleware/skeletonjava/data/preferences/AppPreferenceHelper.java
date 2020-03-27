package com.beetleware.skeletonjava.data.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppPreferenceHelper implements PreferenceHelper {

    public SharedPreferences  sharedPreferences;

    @Inject
    public AppPreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences=sharedPreferences;
    }

}
