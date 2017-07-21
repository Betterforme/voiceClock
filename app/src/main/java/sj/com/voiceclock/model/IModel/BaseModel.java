package sj.com.voiceclock.model.IModel;

import sj.com.voiceclock.retrofitService.excuter.JobExecutor;
import sj.com.voiceclock.retrofitService.excuter.RxJavaExecuter;
import sj.com.voiceclock.retrofitService.excuter.UIThread;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class BaseModel {
    protected RxJavaExecuter rxJavaExecuter;
    public BaseModel(){
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();
}
