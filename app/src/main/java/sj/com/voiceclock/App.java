package sj.com.voiceclock;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/7/18 0018.
 */

public class App extends Application {

    public static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getContext(){
        return app;
    }
}
