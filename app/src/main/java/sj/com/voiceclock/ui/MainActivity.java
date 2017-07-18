package sj.com.voiceclock.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iflytek.sunflower.FlowerCollector;

import sj.com.voiceclock.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
