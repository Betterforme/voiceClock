package sj.com.voiceclock.model.IModel;

import rx.Subscriber;
import sj.com.voiceclock.zretrofitService.excuter.JobExecutor;
import sj.com.voiceclock.zretrofitService.excuter.RxJavaExecuter;
import sj.com.voiceclock.zretrofitService.excuter.UIThread;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class BaseModel {
    protected RxJavaExecuter rxJavaExecuter;
    public BaseModel(){
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();

    public class CommonSubscriber<T> extends Subscriber<T> {
        @Override
        public void onCompleted() {
            iOnCompleted();
        }

        @Override
        public void onError(Throwable e) {
            iOnError(e);
        }

        @Override
        public void onNext(T t) {
            iOnNext(t);
        }
    }

    public abstract void iOnCompleted();
    public abstract void iOnNext(Object t);
    public abstract void iOnError(Throwable e);
}
