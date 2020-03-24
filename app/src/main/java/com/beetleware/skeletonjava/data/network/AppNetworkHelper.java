package com.beetleware.skeletonjava.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppNetworkHelper implements NetworkHelper {

    ApiService apiService;

    @Inject
    public AppNetworkHelper(ApiService apiService) {
        this.apiService=apiService;
    }

}