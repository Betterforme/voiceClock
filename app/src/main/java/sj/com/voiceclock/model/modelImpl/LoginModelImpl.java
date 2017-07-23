package sj.com.voiceclock.model.modelImpl;


import com.iflytek.cloud.thirdparty.T;

import sj.com.voiceclock.VCInterface;
import sj.com.voiceclock.model.IModel.BaseModel;
import sj.com.voiceclock.model.IModel.ILogin;
import sj.com.voiceclock.model.api.UserApiRespository;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginModelImpl extends BaseModel implements ILogin {

    public VCInterface vc;
    public CommonSubscriber subscriber;
    public LoginModelImpl(VCInterface vc){
        this.vc = vc;
    }

    public UserApiRespository userApiRespository  = UserApiRespository.getInstance();

    @Override
    public void login(String username,String password) {
        rxJavaExecuter.execute(
                userApiRespository.userLogin(username,password)
                , subscriber = new CommonSubscriber<T>()
        );
    }

    @Override
    public void destroy() {
        if(subscriber!=null)
            subscriber.unsubscribe();
    }

    @Override
    public void iOnCompleted() {

    }

    @Override
    public void iOnNext(Object t) {
        vc.success(t);
    }

    @Override
    public void iOnError(Throwable e) {
        vc.error(e.getMessage()+"");
    }
}
