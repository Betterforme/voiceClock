package sj.com.voiceclock.model.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import sj.com.voiceclock.constants.Constants;
import sj.com.voiceclock.model.Bean.Data;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public interface UserApi {
    @GET(Constants.User_Login)
    Observable<Data> userLogin(@Query("username") String username, @Query("password") String password);
    @GET(Constants.User_Regist)
    Observable<Data> userRegist(@Query("username") String username,
                                @Query("password") String password,
                                @Query("city") String city,
                                @Query("age") String age,
                                @Query("sex") String sex,
                                @Query("dob") String dob,
                                @Query("email") String email,
                                @Query("userIcon") String userIcon,
                                @Query("status") String status,
                                @Query("lastLoginTime") String lastLoginTime);

}
