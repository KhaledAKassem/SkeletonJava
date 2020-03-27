package com.beetleware.skeletonjava.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;


public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity implements BaseView {

    protected DB mBinding;
    protected VM viewModel;
    private Class<VM> mViewModelClass;

    public BaseActivity(Class<VM> mViewModelClass) {
        this.mViewModelClass = mViewModelClass;
    }

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(mViewModelClass);
        mBinding = DataBindingUtil.setContentView(this, getLayoutRes());

        initBindingViewModel(viewModel);
        initLifeCycleOwner();
        init(savedInstanceState);
    }


    /**
     * You need to override this method. And you need to set viewModel to mBinding:
     * mBinding.viewModel = viewModel
     *
     * @param viewModel the instance of ViewModel that is related to the  activity
     */
    protected abstract void initBindingViewModel(VM viewModel);

    @Override
    @CallSuper
    public void initLifeCycleOwner() {
        mBinding.setLifecycleOwner(this);
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
