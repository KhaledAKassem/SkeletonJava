package com.beetleware.skeletonjava.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import java.util.Objects;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;

public abstract class BaseFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment implements BaseView {

    private DB mBinding;
    private VM viewModel;
    private Class<VM> mViewModelClass;

    public BaseFragment(Class<VM> mViewModelClass) {
        this.mViewModelClass = mViewModelClass;
    }

    private void initDataBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(mViewModelClass);
    }

    /**
     * You need to override this method. And you need to set viewModel to mBinding:
     * mBinding.setViewModel(viewModel);
     *
     * @param viewModel the instance of ViewModel that is related to the  activity
     */

    abstract void initBindingViewModel(VM viewModel);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Nullable
    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_RESIZE);

        initDataBinding(inflater, container) ;
        initBindingViewModel(viewModel);
        initLifeCycleOwner();
        init(savedInstanceState);
        observeErrorResponse();
        observeBackBtnClick();

        return mBinding.getRoot();
    }

    private void observeBackBtnClick() {
//        viewModel.getIsBackBtnClicked().observe(getViewLifecycleOwner(), aBoolean -> {
//            if (aBoolean) {
//                viewModel.setIsBackBtnClicked(false);
//                NavHostFragment.findNavController(this).navigateUp();
//            }
//        });
    }

    private void observeErrorResponse() {
//        viewModel.errorMsg.observe(getViewLifecycleOwner(), errorMsg -> {
//
//            if (errorMsg != null) {
//                hideKeyboard();
//                SnackBarUtils.errorMsg(getView(), errorMsg);
//            }
//        });
//
//    viewModel.errorMsgRes.observe(getViewLifecycleOwner(), errorMsgRes -> {
//
//            if (errorMsgRes != null) {
//                hideKeyboard();
//                SnackBarUtils.errorMsg(getView(), getString(errorMsgRes));
//            }
//        });
    }

    @Override
    @CallSuper
    public void initLifeCycleOwner() {
        mBinding.setLifecycleOwner(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideKeyboard();

//        viewModel.errorMsgRes.setValue(null);
//        viewModel.errorMsg.setValue(null);
//
//        viewModel.errorMsg.removeObservers(this);
//        viewModel.errorMsgRes.removeObservers(this);
    }


    @Override
    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
