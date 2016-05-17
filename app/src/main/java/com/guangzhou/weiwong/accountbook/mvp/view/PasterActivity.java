package com.guangzhou.weiwong.accountbook.mvp.view;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.guangzhou.weiwong.accountbook.R;
import com.guangzhou.weiwong.accountbook.mvp.presenter.IPresenter;
import com.guangzhou.weiwong.accountbook.ui.PasterEditView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasterActivity extends BaseMvpActivity {
    private final String TAG = getClass().getName();
    private boolean sEditable;

    @Bind(R.id.pev_note) PasterEditView mPevNote;
    @Bind(R.id.btn_edit) Button mBtnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paster);
        ButterKnife.bind(this);
        mPevNote.setTypeface(Typeface.createFromAsset(this.getAssets(), "fangzheng_jinglei.ttf"));
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.btn_edit)
    public void onEdit(View view){
        Log.d(TAG, "onEdit()");
        mPevNote.setEnabled(sEditable);
        sEditable = !sEditable;
    }
}
