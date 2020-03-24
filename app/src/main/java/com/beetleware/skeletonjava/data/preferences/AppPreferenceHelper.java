package com.beetleware.skeletonjava.data.preferences;

import android.content.SharedPreferences;

public class AppPreferenceHelper implements PreferenceHelper {

    public SharedPreferences  sharedPreferences;

    public AppPreferenceHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences=sharedPreferences;
    }

}
