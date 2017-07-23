package sj.com.voiceclock.model.modelImpl;


import sj.com.voiceclock.VCInterface;
import sj.com.voiceclock.model.Bean.User;
import sj.com.voiceclock.model.IModel.BaseModel;
import sj.com.voiceclock.model.IModel.ILogin;
import sj.com.voiceclock.model.api.UserApiRespository;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginModelImpl extends BaseModel implements ILogin {

    public VCInterface vc;
    public LoginModelImpl(VCInterface vc){
        this.vc = vc;
    }

    public UserApiRespository userApiRespository  = UserApiRespository.getInstance();

    @Override
    public void login(String username,String password) {
        rxJavaExecuter.execute(
                userApiRespository.userLogin(username,password)
                , new CommonSubscriber<User>()
        );
    }

    @Override
    public void destroy() {

    }

    @Override
    public void iOnCompleted() {

    }

    @Override
    public void iOnNext(Object t) {

    }

    @Override
    public void iOnError(Throwable e) {

    }
}
