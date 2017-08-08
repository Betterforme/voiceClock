package sj.com.voiceclock.mvp.main;

import android.content.Context;

import sj.com.voiceclock.mvp.BasePresenter;
import sj.com.voiceclock.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        
    }
}
