package sj.com.voiceclock.model.modelImpl;

import com.iflytek.cloud.thirdparty.T;

import sj.com.voiceclock.model.Bean.User;
import sj.com.voiceclock.model.IModel.BaseModel;
import sj.com.voiceclock.model.IModel.ILogin;
import sj.com.voiceclock.model.api.UserApiRespository;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginModelImpl extends BaseModel implements ILogin {


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
    public void iOnNext(T t) {

    }

    @Override
    public void iOnError(Throwable e) {

    }
}
