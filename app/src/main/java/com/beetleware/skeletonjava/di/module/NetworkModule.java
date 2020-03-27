package com.beetleware.skeletonjava.di.module;

import android.content.Context;
import android.util.Log;
import com.beetleware.skeletonjava.common.Constants;
import com.beetleware.skeletonjava.common.utils.NetworkUtils;
import com.beetleware.skeletonjava.data.network.ApiService;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module which provides all required dependencies about network
 */

@Module
public class NetworkModule {

    /**
     * provide Http Interceptor to be used in logging networking
     *
     * @return an instance of Http Interceptor with custom logging
     */
    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor.Logger logger = message -> Log.d("network", Constants.NETWORKING_LOG);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(logger);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * Provides okHttp cache to be used in okHttp client
     *
     * @return the okHttp cache
     */
    @Provides
    @Singleton
    Cache provideHttpCash(Context context) {
        return new Cache(context.getFilesDir(), Constants.CACHE_SIZE);
    }

    /**
     * Provide Http Interceptor to update cache if network connected and add Cache-Control to responseBody
     * header if Cache not supported in the server
     *
     * @param context the application context to e used in check the network state
     * @return Http Interceptor instance
     */
    @Provides
    @Singleton
    @Named("online_interceptor")
    Interceptor provideHttpOnlineInterceptor(Context context) {
        return chain -> {
            Response response = chain.proceed(chain.request());
            String headers = response.header("Cache-Control");
            if (NetworkUtils.isNetworkAvailable(context) && (headers == null ||
                    headers.contains("no-store") || headers.contains("must-revalidate") ||
                    headers.contains("no-cache") || headers.contains("max-age=0"))
            ){
                response.newBuilder()
                        .header("Cache-Control", "public, max-age="+Constants.CACHE_MAX_AGE)
                        .build();
            } else {
                return response;
            }
            return response;
        };
        }

        @Provides
        @Singleton
        @Named("offline_interceptor")
        Interceptor provideHttpOfflineInterceptor(Context context) {
        return chain -> {
            Request request=chain.request();
            if (!NetworkUtils.isNetworkAvailable(context)) {
                request = request.newBuilder()
                        .header(
                                "Cache-Control",
                                "public, only-if-cached, max-stale="+Constants.CACHE_MAX_STALE
                        )
                        .build();
            }
           return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    @Named("headers_interceptor")
    Interceptor provideHeadersInterceptor() {
        return chain -> {
            Request request = chain.request();
            request = request.newBuilder()
                    .header("Accept", "application/json")
                    .build();
            return chain.proceed(request);
        };
    }

    /**
     * Provides the okHttp client for networking
     *
     * @param cache the okHttp cache
     * @param loggingInterceptor the okHttp logging interceptor
     * @param onlineInterceptor the interceptor to be used in case of online network
     * @param offlineInterceptor the interceptor to be used in case of offline network
     * @return okHttp client instance
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(
            Cache cache, HttpLoggingInterceptor loggingInterceptor,
            @Named("online_interceptor") Interceptor onlineInterceptor,
            @Named("offline_interceptor")Interceptor offlineInterceptor,
            @Named("headers_interceptor") Interceptor headersInterceptor
    ){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache)
                .addNetworkInterceptor(onlineInterceptor)
                .addInterceptor(offlineInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headersInterceptor)
                .addNetworkInterceptor(headersInterceptor)
                .connectTimeout(300, TimeUnit.SECONDS) // connect timeout
                .readTimeout(300, TimeUnit.SECONDS); // socket timeout

        return client.build();
    }

    /**
     * Provides the Retrofit object.
     *
     * @param httpClient the okHttp client
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    Retrofit provideRetrofitInterface(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl("https//:bettleware.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    /**
     * Provides the service implementation.
     *
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the ApiService implementation.
     */
    @Provides
    @Singleton
     public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


}
