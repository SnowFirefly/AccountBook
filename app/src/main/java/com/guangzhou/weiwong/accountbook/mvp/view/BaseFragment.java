package com.guangzhou.weiwong.accountbook.mvp.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.guangzhou.weiwong.accountbook.utils.MyLog;

/**
 * Created by Tower on 2016/5/11.
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MyLog.d(this, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        MyLog.d(this, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        MyLog.d(this, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        MyLog.d(this, "onDetach");
        super.onDetach();
    }

    @Override
    public void onStart() {
        MyLog.d(this, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        MyLog.d(this, "onStop");
        super.onStop();
    }

    @Override
    public void onResume() {
        MyLog.d(this, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        MyLog.d(this, "onPause");
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        MyLog.d(this, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        MyLog.d(this, "onDestroy");
        super.onDestroy();
    }
}
