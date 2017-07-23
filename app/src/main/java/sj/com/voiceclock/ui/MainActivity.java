package sj.com.voiceclock.ui;

import android.content.Context;
import android.os.Bundle;

import sj.com.voiceclock.IView.ILoginView;
import sj.com.voiceclock.R;
import sj.com.voiceclock.presenter.BasePresenter;
import sj.com.voiceclock.presenter.UserLoginPresent;

public class MainActivity extends BaseActivity implements ILoginView {


    UserLoginPresent present;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.destroy();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter getPresenter() {
        return present;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        present = new UserLoginPresent(this);
        present.login("1233","133");
    }
}
