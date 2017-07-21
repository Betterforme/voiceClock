package sj.com.voiceclock.model.modelImpl;

import sj.com.voiceclock.model.IModel.BaseModel;
import sj.com.voiceclock.model.IModel.ILogin;
import sj.com.voiceclock.model.api.UserApiRespository;

import static android.R.attr.name;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class LoginModelImpl extends BaseModel implements ILogin {


    public UserApiRespository userApiRespository  = UserApiRespository.getInstance();

    @Override
    public void login(String username,String password) {
        rxJavaExecuter.execute(
                userApiRespository.userLogin(username,password)
                , loadMoreCookMenuByIDSubscriber = new LoadMoreCookMenuByIDSubscriber()
        );
    }

    @Override
    public void destroy() {

    }
}
