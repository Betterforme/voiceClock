package sj.com.voiceclock.model.IModel;


import com.iflytek.cloud.thirdparty.T;

import rx.Subscriber;
import sj.com.voiceclock.VCInterface;
import sj.com.voiceclock.retrofitService.excuter.JobExecutor;
import sj.com.voiceclock.retrofitService.excuter.RxJavaExecuter;
import sj.com.voiceclock.retrofitService.excuter.UIThread;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class BaseModel {
    protected RxJavaExecuter rxJavaExecuter;
    VCInterface vcInterface;
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
            iOnNext((com.iflytek.cloud.thirdparty.T)t);
        }
    }

    public abstract void iOnCompleted();
    public abstract void iOnNext(T t);
    public abstract void iOnError(Throwable e);
}
