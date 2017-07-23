package sj.com.voiceclock.presenter;

import sj.com.voiceclock.VCInterface;
import sj.com.voiceclock.model.modelImpl.LoginModelImpl;
import sj.com.voiceclock.ui.ILoginView;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class UserLoginPresent extends BasePresenter implements VCInterface{


    public LoginModelImpl loginModel;
    public ILoginView view;
    public UserLoginPresent(ILoginView view){
        super();
        loginModel = new LoginModelImpl();
        this.view = view;
    }

    public void login(String u,String p){
        loginModel.login(u,p);
    }

    @Override
    public void success(Object o) {
        view.initView();
    }

    @Override
    public void error(Object e) {

    }

    @Override
    public void destroy() {
        loginModel.destroy();
    }
}
