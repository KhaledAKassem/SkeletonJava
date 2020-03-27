package com.beetleware.skeletonjava.ui.base;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.beetleware.skeletonjava.SkeletonJavaApp;
import com.beetleware.skeletonjava.data.AppRepositoryHelper;
import javax.inject.Inject;

public class BaseViewModel extends AndroidViewModel {

    public BaseViewModel( Application app) {
        super(app);
        ((SkeletonJavaApp) app).component.inject(this);
    }

    @Inject
    AppRepositoryHelper appRepositoryHelper;



}
