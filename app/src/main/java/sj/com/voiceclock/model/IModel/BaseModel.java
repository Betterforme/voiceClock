package sj.com.voiceclock.model.IModel;

import rx.Subscriber;
import sj.com.voiceclock.model.Bean.User;
import sj.com.voiceclock.retrofitService.excuter.JobExecutor;
import sj.com.voiceclock.retrofitService.excuter.RxJavaExecuter;
import sj.com.voiceclock.retrofitService.excuter.UIThread;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public abstract class BaseModel {
    protected RxJavaExecuter rxJavaExecuter;
    protected UserLoginSubscriber subscriber;
    public BaseModel(){
        this.rxJavaExecuter = new RxJavaExecuter(JobExecutor.instance(), UIThread.instance());
    }

    public abstract void destroy();

    private class UserLoginSubscriber extends Subscriber<User> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(User user) {

        }
    }
}
