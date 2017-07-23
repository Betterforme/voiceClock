package sj.com.voiceclock.model.api;

import retrofit2.http.Query;
import rx.Observable;
import sj.com.voiceclock.model.Bean.Data;
import sj.com.voiceclock.retrofitService.RetrofitService;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class UserApiRespository implements UserApi{

    private static UserApiRespository Instance = null;
    UserApi userApi = RetrofitService.getInstance().createApi(UserApi.class);
    public static UserApiRespository getInstance(){
        if(Instance == null){
            synchronized(UserApiRespository.class){
                Instance = new UserApiRespository();
            }
        }
        return Instance;
    }

    @Override
    public Observable<Data> userLogin(String username,String password) {
        return userApi.userLogin(username,password);
    }

    @Override
    public Observable<Data> userRegist(@Query("username") String username, @Query("password") String password, @Query("city") String city, @Query("age") String age, @Query("sex") String sex, @Query("dob") String dob, @Query("email") String email, @Query("userIcon") String userIcon, @Query("status") String status, @Query("lastLoginTime") String lastLoginTime) {
        return userApi.userRegist(username, password,city,age,sex,dob,email,userIcon,status,lastLoginTime);
    }
}
