package sj.com.voiceclock.zretrofitService.excuter;

import rx.Scheduler;

/**
 * Created by PeOS on 2016/9/6 0006.
 */
public interface PostExecutionThread {
    public Scheduler getScheduler();
}
