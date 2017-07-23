package sj.com.voiceclock.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.iflytek.sunflower.FlowerCollector;

import butterknife.ButterKnife;
import sj.com.voiceclock.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public abstract  class BaseActivity extends AppCompatActivity {
    protected Context context;
    protected BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        context = this;
        ButterKnife.bind(this);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlowerCollector.onResume(this);
        if(presenter == null && getPresenter() != null){
            presenter = getPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FlowerCollector.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(presenter != null){
            presenter.destroy();
        }
    }

    public Context getContext(){
        return context;
    }

    protected void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    protected void toAcitivty(Class t){
        startActivity(new Intent(this,t));
    }

    protected void toActivityForResult(Bundle bundle,Class t,int requst_code){
        Intent intent = new Intent(this,t);
        intent.putExtras(bundle);
        startActivityForResult(intent,requst_code);
    }

    protected abstract int getLayoutId();
    protected abstract BasePresenter getPresenter();
    protected abstract void init(Bundle savedInstanceState);

}
