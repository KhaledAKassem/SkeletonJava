package com.beetleware.skeletonjava.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;

public interface BaseView {

    /**
     * use this method to provide the activity layout to be used in dataBinding
     *
     * @return the layout id
     */
    @LayoutRes
    int getLayoutRes();

    /**
     * This method is to define the life cycleOwner to the dataBinding.
     * that's because using LiveData in dataBinding requires to be aware of LifeCycle
     */
    void initLifeCycleOwner();

    /**
     * Use this method to handle all views in your activities and fragments
     */
     void init(Bundle savedInstanceState);

    /**
     * Use this method to observe for all LiveData in viewModel
     */
    void  observeLiveDatas();

    /**
     * use this method to  hide the keyboard if it's shown
     */
    void hideKeyboard();

}
