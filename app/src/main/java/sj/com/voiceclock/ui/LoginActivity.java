package sj.com.voiceclock.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sj.com.voiceclock.IView.ILoginView;
import sj.com.voiceclock.R;
import sj.com.voiceclock.presenter.BasePresenter;
import sj.com.voiceclock.presenter.UserLoginPresent;
import top.wefor.circularanim.CircularAnim;

public class LoginActivity extends BaseActivity implements ILoginView {

    UserLoginPresent present;
    @Bind(R.id.tv_lable)
    TextView tvLable;
    @Bind(R.id.imgWxLogin)
    ImageView imgWxLogin;
    @Bind(R.id.rl_weixing)
    RelativeLayout rlWeixing;
    @Bind(R.id.imgQQLogin)
    ImageView imgQQLogin;
    @Bind(R.id.rl_qq)
    RelativeLayout rlQq;
    @Bind(R.id.imgSinaLogin)
    ImageView imgSinaLogin;
    @Bind(R.id.rl_weibo)
    RelativeLayout rlWeibo;
    @Bind(R.id.ll_share)
    LinearLayout llShare;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.iv_white)
    ImageView ivWhite;
    @Bind(R.id.iv_right)
    ImageView ivRight;
    @Bind(R.id.sl_loading)
    sj.com.voiceclock.view.SpinnerLoader slLoading;
    @Bind(R.id.rl_login)
    RelativeLayout rlLogin;
    @Bind(R.id.tv_learn_more)
    TextView tvLearnMore;
    @Bind(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @Bind(R.id.tv_regist)
    TextView tvRegist;
    @Bind(R.id.rl_regist)
    RelativeLayout rlRegist;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initView() {
        toMainActivity(rlLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.destroy();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected BasePresenter getPresenter() {
        return present;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        present = new UserLoginPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_weixing, R.id.rl_qq, R.id.rl_weibo, R.id.rl_login, R.id.rl_about_us, R.id.rl_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_weixing:
                break;
            case R.id.rl_qq:
                break;
            case R.id.rl_weibo:
                break;
            case R.id.rl_login:
                toAcitivty(Main2Activity.class);
                present.login("liuyu", "497045289@qq.com");
                slLoading.setVisibility(View.VISIBLE);
                ivRight.setVisibility(View.INVISIBLE);
                ivWhite.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_about_us:
                break;
            case R.id.rl_regist:
                break;
        }
    }

    private void toMainActivity(View v) {
        CircularAnim.fullActivity(LoginActivity.this, v)
                .colorOrImageRes(R.color.colorPrimary)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        toAcitivty(LoginActivity.class);
                    }
                });
    }

    @Override
    public void netError(String s) {
        toast(s+"");
    }
}
