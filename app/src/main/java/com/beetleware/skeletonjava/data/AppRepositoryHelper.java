package com.beetleware.skeletonjava.data;

import com.beetleware.skeletonjava.data.network.AppNetworkHelper;
import com.beetleware.skeletonjava.data.preferences.AppPreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepositoryHelper implements RepositoryHelper {

    public AppNetworkHelper appNetworkHelper;
    public AppPreferenceHelper appPreferenceHelper;

    @Inject
    public AppRepositoryHelper(AppNetworkHelper appNetworkHelper,AppPreferenceHelper appPreferenceHelper) {
        this.appNetworkHelper=appNetworkHelper;
        this.appPreferenceHelper=appPreferenceHelper;
    }
}
