package sj.com.voiceclock.net;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sj.com.voiceclock.BuildConfig;
import sj.com.voiceclock.constants.Constants;

/**
 * Created by Administrator on 2017/2/17.
 */

public class RetrofitService {

    private static RetrofitService Instance = null;

    public static RetrofitService getInstance(){
        if(Instance == null)
            Instance = new RetrofitService();

        return Instance;
    }

    private String Tag = RetrofitService.class.getSimpleName();
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private RetrofitService(){
        if (BuildConfig.DEBUG) {

            okHttpClient = new OkHttpClient();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        else{
            okHttpClient = new OkHttpClient();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public <T> T createApi(Class<T> clazz) {
        return retrofit.create(clazz);
    }

}