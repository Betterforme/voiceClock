package sj.com.voiceclock.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iflytek.sunflower.FlowerCollector;

import sj.com.voiceclock.R;
import sj.com.voiceclock.presenter.UserLoginPresent;

public class MainActivity extends AppCompatActivity implements ILoginView{


    UserLoginPresent present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        present = new UserLoginPresent(this);
        present.login("1233","133");
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlowerCollector.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FlowerCollector.onPause(this);
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.destroy();
    }
}
