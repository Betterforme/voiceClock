package sj.com.voiceclock.presenter;

import android.content.Context;

import sj.com.voiceclock.excuter.JobExecutor;
import sj.com.voiceclock.excuter.RxJavaExecuter;
import sj.com.voiceclock.excuter.UIThread;


/**
 * Created by Administrator on 2017/2/17.
 */

public abstract class BasePresenter {

    protected Context context;
    protected RxJavaExecuter rxJavaExecuter;

    public BasePresenter(Context context){
        this.context = context;
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
